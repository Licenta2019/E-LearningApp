package learningapp.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feature extends BaseEntity {

    @ManyToOne
    private User student;

    @ManyToOne
    private Test test;

    @NotNull
    private double grade;

    @CreationTimestamp
    private LocalDate date;

}
