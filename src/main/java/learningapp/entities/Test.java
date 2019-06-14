package learningapp.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Test entity.
 */
//@Entity
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

}
