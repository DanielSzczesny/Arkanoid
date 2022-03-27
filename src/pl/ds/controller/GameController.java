package pl.ds.controller;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.client.Timer;
import pl.ds.model.*;
import pl.ds.shared.MouseListener;
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
    private Brick brickToRemove;
    private Cordinate brickHitCoordinate;

    private boolean isStarted;
    private boolean ballMove;

    public GameController(Difficulty difficulty, CanvasView canvasView) {
        this.difficulty = difficulty;
        this.view = canvasView;
        init();
    }

    private void init() {
        bricks = Brick.createBricks((CANVAS_WIDTH / BRICK_WIDTH));
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

    /**
     * Metody sterowania
     * @param canvas
     */
    @Override
    public void onKeyHit(Canvas canvas) {
        canvas.addMouseMoveHandler(mouseMoveEvent -> {
            MouseListener.getInstance().setMouseX(mouseMoveEvent.getRelativeX(canvas.getElement()));
            MouseListener.getInstance().setMouseY(mouseMoveEvent.getRelativeY(canvas.getElement()));
            mouseMovementCore();
        });
        canvas.addClickHandler(clickEvent -> clickEvents());
        canvas.addKeyDownHandler(keyDownEvent -> arrowSteering(keyDownEvent));
        canvas.addKeyUpHandler(keyUpEvent -> holdRocketWhenKeyUp(keyUpEvent));
    }

    private void holdRocketWhenKeyUp(KeyUpEvent keyUpEvent) {
        if (keyUpEvent.isRightArrow() && rocketXPos < ROCKET_MAX_RIGHT) {
            slowdownSpeed = 5.0;
        }
        if (keyUpEvent.isLeftArrow() && rocketXPos >ROCKET_MAX_LEFT) {
            slowdownSpeed = 5.0;
        }
    }

    private void arrowSteering(KeyDownEvent keyDownEvent) {
        if (keyDownEvent.isRightArrow() && rocketXPos < ROCKET_MAX_RIGHT) {
            rocketCurrentSpeed = ROCKET_SPEED;
            slowdownSpeed = 0.0;
        }
        if (keyDownEvent.isLeftArrow() && rocketXPos > ROCKET_MAX_LEFT) {
            rocketCurrentSpeed = ROCKET_SPEED * -1;
        }
        if (keyDownEvent.isUpArrow() && !isStarted) {
            startBall();
        }
    }

    private void clickEvents() {
        if (!isStarted && !ballMove) {
            startBall();
        }
    }

    private void mouseMovementCore() {
        rocketXPos = MouseListener.getInstance().getMouseX() - ROCKET_WIDTH / 2;
        if (rocketXPos + ROCKET_WIDTH >= CANVAS_WIDTH)
            rocketXPos = CANVAS_WIDTH - ROCKET_WIDTH;
        if (rocketXPos <= 0)
            rocketXPos = 0;
    }

    private void startBall() {
        ballYSpeed = BALL_SPEED * difficulty.ballSpeedMultiplicant();
        ballXSpeed = BALL_SPEED * difficulty.ballSpeedMultiplicant();
        isStarted = true;
        ballMove = true;
        startCountDown();
    }

    private void startCountDown() {
        countdownTimer = new Timer() {
            @Override
            public void run() {
                game.getTimer().timeElapse();
            }
        };
        TimeWrapper.getInstance().setClock(countdownTimer);
        countdownTimer.scheduleRepeating(ONE_SECOND_IN_MSECOND);
    }

    @Override
    public void rocketMove() {
        if (rocketXPos < ROCKET_MAX_RIGHT & rocketCurrentSpeed > 0)
            rocketXPos += rocketCurrentSpeed;
        if (rocketXPos > ROCKET_MAX_LEFT & rocketCurrentSpeed < 0)
            rocketXPos += rocketCurrentSpeed;

        if (rocketCurrentSpeed > 0)
            rocketCurrentSpeed -= slowdownSpeed;
        if (rocketCurrentSpeed < 0)
            rocketCurrentSpeed += slowdownSpeed;
    }

    @Override
    public void launchBall() {
        ballGo();
        ballBounceOfBorders();
        ballBounceOfRocket();
        ballBounceFromBrick();
    }

    /**
     * Odbicie od cegiełki
     */
    private void ballBounceFromBrick() {
        bricks.forEach(x -> {
            if (isOnBrick(x)) {
                x.setBrickLives(x.getBrickLives() - 1);
                game.setPoints((int) (game.getPoints() + difficulty.ballSpeedMultiplicant() * 2));
                if (x.getBrickLives() <= 0)
                    brickToRemove = x;
                ballBounceOfBrick();

            }
        });
        bricks.remove(brickToRemove);
    }

    private void ballBounceOfBrick() {
        if (brickHitCoordinate.getCordinateType().equals(Cordinate.CordinateType.BOTTOM)) {
            ballYSpeed *= -1;
        } else if (brickHitCoordinate.getCordinateType().equals(Cordinate.CordinateType.LEFT)) {
            ballYSpeed *= -1;
        } else if (brickHitCoordinate.getCordinateType().equals(Cordinate.CordinateType.TOP)) {
            ballXSpeed *= -1;
        } else if (brickHitCoordinate.getCordinateType().equals(Cordinate.CordinateType.RIGHT)) {
            ballXSpeed *= -1;
        }
        //TODO dźwięk odbicia
    }

    private boolean isOnBrick(Brick brick) {
        double bStartX = brick.getCordinate().getX();
        double bStartY = brick.getCordinate().getY();
        double bEndX = brick.getCordinate().getX() + BRICK_WIDTH;
        double bEndY = brick.getCordinate().getY() + BRICK_HEIGHT;
        return bounceCheck(bStartX, bStartY, bEndX, bEndY);
    }

    /**
     * Odbicie od paletki/statku
     */
    private void ballBounceOfRocket() {
        if (isOnRocket()) {
            int equalizer = (ROCKET_WIDTH / 12);
            int compressor = (ROCKET_WIDTH / equalizer);
            int variable1 = (ROCKET_WIDTH / 17);
            int variable2 = (ROCKET_WIDTH / 20);

            for (int j = 0; j < ROCKET_WIDTH + equalizer; j = j + equalizer) {
                if (ballXPos >= rocketXPos - ROCKET_WIDTH / compressor + j && ballXPos < rocketXPos + equalizer + j) {
                    ballXSpeed = (-1 * ROCKET_WIDTH / 2 + j) / equalizer * difficulty.ballSpeedMultiplicant() * -1;
                    ballYSpeed = (variable1 - Math.abs(-variable2 + j / equalizer)) * difficulty.ballSpeedMultiplicant();
                }
            }
            ballYPos = ROCKET_Y_POS - BALL_RADIUS * 2;
            //TODO dźwięk odbicia
        }
    }

    private boolean isOnRocket() {
        return bounceCheck(rocketXPos, rocketYPos, rocketXPos + ROCKET_WIDTH, rocketYPos + ROCKET_HEIGHT);
    }

    /**
     * Ułożenie punktów do wprowadzenia
     *      12********
     *      |        |
     *      ********34
     * @param startXPos pozycja X w lewym górnym rogu
     * @param startYPos pozycja Y w lewym górnym rogu
     * @param endXPos pozycja X w prawym dolnym rogu
     * @param endYPos pozycja Y w prawym dolnym rogu
     * @return
     */
    private boolean bounceCheck(double startXPos, double startYPos, double endXPos, double endYPos) {
        return ballCoordinates().stream().anyMatch(x -> {
            double _x = x.getX();
            double _y = x.getY();
            if (_x >= startXPos && _x <= endXPos && _y >= startYPos && _y <= endYPos) {
                setCoordinate(x);
                return true;
            }
            return false;
        });
    }

    private void setCoordinate(Cordinate x) {
        brickHitCoordinate = new Cordinate(x.getX(), x.getY(), x.getCordinateType());
    }

    /**
     * cztery krawędzie piłki
     * @return Lista punktów które mogą się odbijać
     */
    private List<Cordinate> ballCoordinates() {
        ballCoordinates.clear();
        ballCoordinates.add(new Cordinate(ballXPos + BALL_RADIUS, ballYPos, Cordinate.CordinateType.LEFT));
        ballCoordinates.add(new Cordinate(ballXPos, ballYPos + BALL_RADIUS, Cordinate.CordinateType.TOP));
        ballCoordinates.add(
                new Cordinate(ballXPos + BALL_RADIUS, ballYPos + 2 * BALL_RADIUS, Cordinate.CordinateType.BOTTOM));
        ballCoordinates.add(
                new Cordinate(ballXPos + BALL_RADIUS * 2, ballYPos + BALL_RADIUS, Cordinate.CordinateType.RIGHT));
        return ballCoordinates;
    }

    private void ballBounceOfBorders() {
        if (ballXPos <= BALL_MIN_X || ballXPos >= BALL_MAX_X) {
            ballXSpeed *= -1;
            //TODO dźwięk odbicia
        }
        if (ballYPos <= BALL_MIN_Y || ballYPos >= BALL_MAX_Y) {
            ballYSpeed *= -1;
            //TODO dźwięk odbicia
        }

    }

    /**
     * Start piłki, początek po utracie życia/starcie nowej gry
     */
    private void ballGo() {
        ballYPos -= ballYSpeed;
        ballXPos -= ballXSpeed;
    }

    @Override
    public double getBallXPos() {
        return ballXPos;
    }

    @Override
    public double getBallYPos() {
        return ballYPos;
    }

    @Override
    public void putBricksToMemory() {
        view.showBricks(bricks);
    }

    @Override
    public double getRocketXPos() {
        return rocketXPos;
    }

    @Override
    public Game getGame() {
        return game;
    }


}
