package pl.ds.model;

import pl.ds.shared.Timer;

import static pl.ds.shared.Constants.DEFAULT_TIME_FOR_GAME;

public class Game {

    private int lives;
    private int points;
    private Difficulty difficulty;
    
    private Timer timer;

    private long time;

    private Game(GameBuilder builder) {
        this.difficulty = builder.difficulty;
        this.lives = builder.lives;
        this.points = builder.points;
        this.time = (long) (DEFAULT_TIME_FOR_GAME / difficulty.ballSpeedMultiplicant());
        init();
    }

    private void init() {
        timer = new Timer();
        timer.setMinutes(time);
        timer.setSeconds(time);
    }

    public Timer getTimer() {
        return timer;
    }

    public int getLives() {
        return lives;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void lostLive() {
        lives--;
    }

    public static class GameBuilder {

        private int lives;
        private int points;
        private Difficulty difficulty;

        public GameBuilder(Difficulty difficulty) {
            this.difficulty = difficulty;
        }

        public GameBuilder lives(int lives) {
            this.lives = lives;
            return this;
        }

        public GameBuilder points(int points) {
            this.points = points;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
