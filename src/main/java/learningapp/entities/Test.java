package learningapp.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Test entity.
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Test extends BaseEntity {

    @ManyToMany
    @JoinTable(name = "test_test_questions",
            joinColumns = {@JoinColumn(name = "test_id")},
            inverseJoinColumns = {@JoinColumn(name = "test_question_id")})
    private List<TestQuestion> questions;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User author;

    @CreationTimestamp
    private LocalDate creationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TestDifficulty testDifficulty;

}
