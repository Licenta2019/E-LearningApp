package learningapp.entities;

public enum TestQuestionStatus {

    PENDING("Pending"),
    REQUESTED_CHANGES("Requested Changes"),
    VALIDATED("Validated"),
    INVALIDATED("Invalidated");

    private String label;

    TestQuestionStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
