package pl.ds.model;

public enum Dificulty {
    PLAYGROUND, EASY, REGULAR, HARD, HELL;

    public double ballSpeedMultiplicant() {
        return switch (this) {
            case PLAYGROUND -> 0.5;
            case EASY -> 0.75;
            case HARD -> 2.5;
            case HELL -> 5;
            default -> 1;
        };
    }

    public int getLives() {
        return switch (this) {
            case PLAYGROUND -> 10;
            case EASY -> 7;
            case HARD -> 3;
            case HELL -> 1;
            default -> 5;
        };
    }
}
