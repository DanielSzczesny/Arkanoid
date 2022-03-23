package pl.ds.model;

public enum Difficulty {
    PLAYGROUND, EASY, REGULAR, HARD, HELL;

    public double ballSpeedMultiplicant() {
        switch (this) {
            case PLAYGROUND:
                return 0.5;
            case EASY:
                return 0.75;
            case HARD:
                return 2.5;
            case HELL:
                return 5;
            default:
                return 1;
        }
    }

    public int getLives() {
        switch (this) {
            case PLAYGROUND:
                return 10;
            case EASY:
                return 7;
            case HARD:
                return 3;
            case HELL:
                return 1;
            default:
                return 5;
        }
    }
}
