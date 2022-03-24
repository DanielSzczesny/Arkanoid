package pl.ds.controller;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.Timer;
import pl.ds.model.Brick;
import pl.ds.model.Cordinate;
import pl.ds.model.Difficulty;
import pl.ds.model.Game;
import pl.ds.shared.MouseListener;
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

    @Override
    public void listenToTheGame() {
        lifeLost();
        levelDone();
        gameOver();
    }

    private void gameOver() {
        if (game.getLives() == 0 || (game.getTimer().getMinutes() == 0 && game.getTimer().getSeconds() == 0)) {
            stopBall();
            view.gameOver();
            countdownTimer.cancel();
        }
    }

    private void levelDone() {
        if (bricks.size() == 0) {
            stopBall();
            view.levelWon();
            countdownTimer.cancel();
        }
    }

    private void stopBall() {
        ballYSpeed = 0;
        ballXSpeed = 0;
    }

    private void lifeLost() {
        if (ballYPos > CANVAS_HEIGHT) {
            ballMove = false;
            ballXSpeed = 0;
            ballYSpeed = 0;
            ballXPos = rocketXPos + (ROCKET_WIDTH / 2.0);
            ballYPos = BALL_START_Y_POS;
            isStarted = false;
            game.lostLive();
        }
    }

    @Override
    public void onKeyHit(Canvas canvas) {
        canvas.addMouseMoveHandler(mouseMoveEvent -> {
            MouseListener.getInstance().setMouseX(mouseMoveEvent.getRelativeX(canvas.getElement()));
            MouseListener.getInstance().setMouseY(mouseMoveEvent.getRelativeY(canvas.getElement()));
            mouseMovementCore();
        });
        canvas.addClickHandler(clickEvent -> clickEvents());
        canvas.addKeyDownHandler(keyDownEvent -> arrowSteering());
        canvas.addKeyUpHandler(keyUpEvent -> holdRocketWhenKeyUp());
    }

    private void holdRocketWhenKeyUp() {
    }

    private void arrowSteering() {
    }

    private void clickEvents() {
    }

    private void mouseMovementCore() {
    }

    @Override
    public void rocketMove() {

    }

    @Override
    public void launchBall() {

    }

    @Override
    public double getBallXPos() {
        return 0;
    }

    @Override
    public double getBallYPos() {
        return 0;
    }

    @Override
    public void putBricksToMemory() {

    }

    @Override
    public double getRacketXPos() {
        return 0;
    }

    @Override
    public Game getGame() {
        return null;
    }
}
