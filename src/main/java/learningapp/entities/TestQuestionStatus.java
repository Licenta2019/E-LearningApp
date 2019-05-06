package learningapp.entities;

public enum TestQuestionStatus {

    PENDING(1),
    REQUESTED_CHANGES(2),
    VALIDATED(3),
    INVALIDATED(4);

    private int priority;

    TestQuestionStatus(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }
}
