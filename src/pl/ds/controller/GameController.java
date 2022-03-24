package pl.ds.controller;

import com.google.gwt.user.client.Timer;
import pl.ds.model.Brick;
import pl.ds.model.Cordinate;
import pl.ds.model.Difficulty;
import pl.ds.model.Game;
import pl.ds.shared.TimeWrapper;
import pl.ds.view.CanvasView;
import pl.ds.view.Presenter;

import java.util.LinkedList;
import java.util.List;

import static pl.ds.shared.Constants.*;

public class GameController implements Presenter {

    private Game game;
    private Difficulty difficulty;

    private double ballXSpeed;
    private double ballYSpeed;
    private double ballXPos;
    private double ballYPos;

    private Timer countdownTimer;

    private static final double rocketYPos = ROCKET_Y_POS;
    protected double rocketXPos;
    protected double rocketCurrentSpeed;
    protected double slowdownSpeed;

    private CanvasView view;
    private List<Cordinate> ballCoordinates;
    private List<Brick> bricks;
    private Cordinate brickHitCoordinate;

    private boolean isStarted;
    private boolean ballMove;

    public GameController(Difficulty difficulty, CanvasView canvasView) {
        this.difficulty = difficulty;
        this.view = canvasView;
        init();
    }

    private void init() {
        bricks = Brick.createBricks(CANVAS_WIDTH / BRICK_WIDTH);
        isStarted = false;
        ballXPos = BALL_START_X_POS;
        ballYPos = BALL_START_Y_POS;
        rocketXPos = ROCKET_X_POS;
        rocketCurrentSpeed = 0.0;
        buildGame();
        ballCoordinates = new LinkedList<>();
        brickHitCoordinate = new Cordinate();
    }

    private void buildGame() {
        game = new Game.GameBuilder(difficulty).lives(difficulty.getLives()).points(0).build();
        ballMove = false;
    }
}
