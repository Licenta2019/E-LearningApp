package learningapp.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import learningapp.entities.TestQuestionStatus;
import learningapp.entities.UserRole;
import learningapp.exceptions.base.InvalidTransitionException;

import static learningapp.entities.TestQuestionStatus.INVALIDATED;
import static learningapp.entities.TestQuestionStatus.PENDING;
import static learningapp.entities.TestQuestionStatus.REQUESTED_CHANGES;
import static learningapp.entities.TestQuestionStatus.VALIDATED;
import static learningapp.entities.UserRole.PROFESSOR;
import static learningapp.exceptions.ExceptionMessages.INVALID_TRANSITION_ERROR;

public class StatusTransitionComputation {
    public static final List<TestQuestionStatus> nonFinalStatuses;
    private static final Map<TestQuestionStatus, List<TestQuestionStatus>> transitionMap;

    static {
        transitionMap = new HashMap<>();
        transitionMap.put(PENDING, Arrays.asList(REQUESTED_CHANGES, VALIDATED, INVALIDATED));
        transitionMap.put(REQUESTED_CHANGES, Arrays.asList(PENDING, VALIDATED, INVALIDATED));

        nonFinalStatuses = new ArrayList<>();
        nonFinalStatuses.add(PENDING);
        nonFinalStatuses.add(REQUESTED_CHANGES);
    }

    public static TestQuestionStatus getNextStatus(TestQuestionStatus currentStatus, UserRole userRole) {
        TestQuestionStatus nextStatus = userRole.equals(PROFESSOR) ? REQUESTED_CHANGES : PENDING;

        if (currentStatus != nextStatus) {
            if (!transitionMap.get(currentStatus).contains(nextStatus)) {
                throw new InvalidTransitionException(INVALID_TRANSITION_ERROR + currentStatus + " to " + nextStatus);
            }
        }
        return nextStatus;
    }

    public static boolean isValidTransition(TestQuestionStatus previousStatus, TestQuestionStatus currentStatus) {
        return transitionMap.get(previousStatus).contains(currentStatus);
    }

}
