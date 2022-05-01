package pl.ds.view;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.*;
import pl.ds.controller.GameController;
import pl.ds.model.Brick;
import pl.ds.model.Difficulty;
import pl.ds.shared.MouseListener;
import pl.ds.shared.TimeWrapper;

import java.util.List;

import static pl.ds.shared.Constants.*;

public class CanvasView extends Composite implements View{

    private Presenter gameController;
    private Canvas canvas;
    private Context2d context;

    ImageElement imgBackground = ImageElement.as(new Image("images/" + BACKGROUND_IMAGE).getElement());
    ImageElement imgRocket = ImageElement.as(new Image("images/" + ROCKET).getElement());
    ImageElement imgBall = ImageElement.as(new Image("images/" + BALL).getElement());
    ImageElement imgBrickYellow = ImageElement.as(new Image("images/" + YELLOW_BRICK).getElement());
    ImageElement imgBrickBlueOne = ImageElement.as(new Image("images/" + BLUE_ONE_BRICK).getElement());
    ImageElement imgBrickBlueTwo = ImageElement.as(new Image("images/" + BLUE_TWO_BRICK).getElement());
    ImageElement imgBrickRedOne = ImageElement.as(new Image("images/" + RED_ONE_BRICK).getElement());
    ImageElement imgBrickRedTwo = ImageElement.as(new Image("images/" + RED_TWO_BRICK).getElement());
    ImageElement imgBrickRedThree = ImageElement.as(new Image("images/" + RED_THREE_BRICK).getElement());

    public CanvasView(Difficulty difficulty) {
        this.gameController = new GameController(difficulty, this);
        this.canvas = createCanvas();
        initWidget(canvas);
    }


    @Override
    public Canvas createCanvas() {
        canvas = Canvas.createIfSupported();

        if (canvas == null) {
            FlowPanel flowPanel = new FlowPanel();
            flowPanel.add(new HTML(CANVAS_NOT_SUPPORTED));
            initWidget(flowPanel);
        }

        canvas.setStyleName("gameCanvas");
        canvas.setWidth(CANVAS_WIDTH + "px");
        canvas.setCoordinateSpaceWidth(CANVAS_WIDTH);
        canvas.setHeight(CANVAS_HEIGHT + "px");
        canvas.setCoordinateSpaceHeight(CANVAS_HEIGHT);

        addKeyListeners();

        context = canvas.getContext2d();
        return canvas;
    }

    private void addKeyListeners() {
        gameController.onKeyHit(canvas);
    }

    @Override
    public void refreshCanvas() {
        context.drawImage(imgBackground, 0 , 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        showGameLiveInformation();
        gameController.putBricksToMemory();
        showBall();
        launchRocket();
        gameController.listenToTheGame();
        TimeWrapper.getInstance().nextFrame();
    }

    private void showGameLiveInformation() {
        //punkty
        context.strokeText("POINTS: ", POINTS_POSITION_X - 28, POINTS_POSITION_Y);
        context.strokeText(String.valueOf(gameController.getGame().getPoints()), POINTS_POSITION_X + 20, POINTS_POSITION_Y);

        //czas
        context.strokeText(getTime(), POINTS_POSITION_X - 28, POINTS_POSITION_Y + 10);
        context.strokeText("Lives: " + gameController.getGame().getLives(), 12, POINTS_POSITION_Y);

        //pozycja myszy
        context.strokeText("X: " + MouseListener.getInstance().getMouseX(), 12, POINTS_POSITION_Y + 10);
        context.strokeText("Y: " + MouseListener.getInstance().getMouseY(), 12, POINTS_POSITION_Y + 20);
        context.strokeText("Ball speed X: " + gameController.getBallXSpeed(), 12, POINTS_POSITION_Y + 30);
        context.strokeText("Ball speed Y: " + gameController.getBallYSpeed(), 12, POINTS_POSITION_Y + 40);
    }

    private String getTime() {
        return "Remaining time: " + gameController.getGame().getTimer().getMinutes() + ":"
                + gameController.getGame().getTimer().getSeconds();
    }

    private void launchRocket() {
        context.drawImage(imgRocket, gameController.getRocketXPos(), ROCKET_Y_POS);
        gameController.rocketMove();
    }

    @Override
    public void showBricks(List<Brick> bricks) {
        for (Brick b: bricks) {
            ImageElement img = getBrickColor(b);
            context.drawImage(img, b.getCordinate().getX(),
                    b.getCordinate().getY(),
                    BRICK_WIDTH, BRICK_HEIGHT);
        }
    }

    private ImageElement getBrickColor(Brick b) {
        switch (b.getBrickType()) {
            case YELLOW:
                return imgBrickYellow;
            case BLUE:
                if (b.getBrickLives() == 1) return imgBrickBlueTwo;
                else return imgBrickBlueOne;
            case RED:
                if (b.getBrickLives() == 1) return imgBrickRedThree;
                else if (b.getBrickLives() == 2) return imgBrickRedTwo;
                else return imgBrickRedOne;
        }
        return imgBrickYellow;
    }

    @Override
    public void showBall() {
        context.drawImage(imgBall, gameController.getBallXPos(), gameController.getBallYPos());
        gameController.launchBall();
    }

    @Override
    public void gameOver() {
        context.strokeText("GAME OVER! ", CANVAS_WIDTH / 2.0, CANVAS_HEIGHT / 2.0);
    }

    @Override
    public void levelWon() {
        context.strokeText("LEVEL DONE! ", CANVAS_WIDTH / 2.0, CANVAS_HEIGHT / 2.0);
    }
}
