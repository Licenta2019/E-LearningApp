package learningapp.services;

import java.util.List;
import java.util.UUID;

import learningapp.dtos.test.BaseTestDto;
import learningapp.dtos.test.CreationTestDto;

public interface TestService {

    /**
     * add a new test to db.
     *
     * @param CreationTestDto
     * @return
     */
    UUID addTest(CreationTestDto CreationTestDto);

    /**
     * Retrieve all tests linked with a given subject.
     *
     * @param subjectId
     * @return
     */
    List<BaseTestDto> getTests(UUID subjectId);
}
