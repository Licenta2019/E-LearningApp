package learningapp.entities;

public enum TestQuestionStatus {

    PENDING("Pending", 1),
    REQUESTED_CHANGES("Requested Changes", 2),
    VALIDATED("Validated", 3),
    INVALIDATED("Invalidated", 3);

    private String label;
    private int priority;

    TestQuestionStatus(String label, int priority) {
        this.label = label;
        this.priority = priority;
    }

    public String getLabel() {
        return this.label;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isFinal() {
        return priority == 3;
    }
}
