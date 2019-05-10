package learningapp.handlers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import learningapp.entities.TestQuestionStatus;

import static learningapp.entities.TestQuestionStatus.INVALIDATED;
import static learningapp.entities.TestQuestionStatus.PENDING;
import static learningapp.entities.TestQuestionStatus.REQUESTED_CHANGES;
import static learningapp.entities.TestQuestionStatus.VALIDATED;

@Component
public class StatusTransitionComputation {
    private static Map<TestQuestionStatus, List<TestQuestionStatus>> transitionMap;

//    static {
//        transitionMap.put(PENDING, Arrays.asList(REQUESTED_CHANGES, VALIDATED, INVALIDATED));
//        transitionMap.put(REQUESTED_CHANGES, Arrays.asList(PENDING, VALIDATED, INVALIDATED));
//    }

    public boolean isValidTransition(TestQuestionStatus previousStatus, TestQuestionStatus currentStatus) {
        return transitionMap.get(previousStatus).contains(currentStatus);
    }

}
