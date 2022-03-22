package pl.ds.model;

import pl.ds.shared.Timer;

import static pl.ds.shared.Constants.DEFAULT_TIME_FOR_GAME;

public class Game {

    private int lives;
    private int points;
    private Dificulty dificulty;
    
    private Timer timer;

    private long time;

    private Game(GameBuilder builder) {
        this.dificulty = builder.dificulty;
        this.lives = builder.lives;
        this.points = builder.points;
        this.time = (long) (DEFAULT_TIME_FOR_GAME / dificulty.ballSpeedMultiplicant());
        init();
    }

    private void init() {
        timer = new Timer(Timer.getMinutes(time), Timer.getSeconds(time));
    }

    private class GameBuilder {

        private int lives;
        private int points;
        private Dificulty dificulty;

        public GameBuilder(Dificulty dificulty) {
            this.dificulty = dificulty;
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
