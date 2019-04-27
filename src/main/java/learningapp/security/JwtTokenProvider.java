package learningapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import learningapp.entities.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.util.stream.Collectors.toList;

/**
 * Token provider.
 */
@Component
public class JwtTokenProvider {

    private final LearningappUserDetails customUserDetails;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:14400000}")
    private long validityInMilliseconds;

    public JwtTokenProvider(final LearningappUserDetails customUserDetails) {
        this.customUserDetails = customUserDetails;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(Charset.defaultCharset()));
    }

    public String createToken(final String username, final List<UserRole> roles) {
        final Claims claims = Jwts.claims().setSubject(username);
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);
        final List<SimpleGrantedAuthority> roleList = roles.stream()
                .map(s -> new SimpleGrantedAuthority(s.getAuthority()))
                .collect(toList());

        claims.put("auth", roleList);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(final String token) {
        final UserDetails userDetails = customUserDetails.loadUserByUsername(getUsername(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(final String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(final HttpServletRequest req) {
        final String bearerToken = req.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (final JwtException | IllegalArgumentException e) {
            //throw new ForbiddenException(INVALID_TOKEN, AUTHORIZATION_NOT_VALID);
            return false;
        }
    }

}
