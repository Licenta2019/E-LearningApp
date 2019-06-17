package learningapp.services;

import java.util.List;
import java.util.UUID;

import learningapp.dtos.test.BaseTestDto;
import learningapp.dtos.test.CreationTestDto;
import learningapp.dtos.test.TestDto;
import learningapp.entities.TestDifficulty;

public interface TestService {

    /**
     * add a new test to db.
     *
     * @param creationTestDto
     * @return
     */
    UUID addTest(CreationTestDto creationTestDto);

    /**
     * Retrieve all tests linked with a given subject.
     *
     * @param subjectId
     * @return
     */
    List<BaseTestDto> getTests(UUID subjectId);

    /**
     * Return list of test difficulties.
     *
     * @return
     */
    List<TestDifficulty> getTestDifficulties();

    /**
     * Retrieve a test with all his questions via a test id.
     *
     * @param id
     * @return
     */
    TestDto getTest(UUID id);
}
