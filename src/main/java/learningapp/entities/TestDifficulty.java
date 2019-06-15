package learningapp.entities;

import lombok.Getter;

@Getter
public enum TestDifficulty {
    RANDOM(1, 10),
    EASY(1, 3),
    MEDIUM(4, 7),
    HIGH(8, 10);

    private int minDifficulty;
    private int maxDifficulty;

    TestDifficulty(int minDifficulty, int maxDifficulty) {
        this.maxDifficulty = maxDifficulty;
        this.minDifficulty = minDifficulty;
    }
}
