package pl.ds.view;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
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
        CssColor color = CssColor.make(255, 255, 255);

        context.setFillStyle(color);
        context.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        context.closePath();
        ImageElement img = ImageElement.as(new Image("images/" + BACKGROUND_IMAGE).getElement());
        context.drawImage(img, 0 , 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        getGameLiveInformations();
        gameController.putBricksToMemory();
        showBall();
        launchRocket();
        gameController.listenToTheGame();
        TimeWrapper.getInstance().nextFrame();
    }

    private void getGameLiveInformations() {
        //punkty
        context.fillRect(POINTS_POSITION_X - 30, POINTS_POSITION_Y -8, CANVAS_WIDTH - 5, 30);
        context.strokeText("POINTS: ", POINTS_POSITION_X - 28, POINTS_POSITION_Y);
        context.strokeText(String.valueOf(gameController.getGame().getPoints()), POINTS_POSITION_X + 20, POINTS_POSITION_Y);

        //czas
        context.fillRect(POINTS_POSITION_X - 30, POINTS_POSITION_Y + 2, CANVAS_WIDTH - 5, 10);
        context.strokeText(getTime(), POINTS_POSITION_X - 28, POINTS_POSITION_Y + 10);
        context.fillRect(10, POINTS_POSITION_Y - 8, 50, 10);
        context.strokeText("Lives: " + gameController.getGame().getLives(), 12, POINTS_POSITION_Y);

        //pozycja myszy
        context.strokeText("X: " + MouseListener.getInstance().getMouseX(), 12, POINTS_POSITION_Y + 10);
        context.strokeText("Y: " + MouseListener.getInstance().getMouseY(), 12, POINTS_POSITION_Y + 20);
    }

    private String getTime() {
        return "Remaining time: " + gameController.getGame().getTimer().getMinutes() + ":"
                + gameController.getGame().getTimer().getSeconds();
    }

    private void launchRocket() {
        ImageElement img = ImageElement.as(new Image("images/" + ROCKET).getElement());
        context.drawImage(img, gameController.getRocketXPos(), ROCKET_Y_POS);
        gameController.rocketMove();
    }

    @Override
    public void showBricks(List<Brick> bricks) {
        for (Brick b: bricks) {
            ImageElement img = ImageElement.as(new Image("images/" + getBrickColor(b)).getElement());
            context.drawImage(img, b.getCordinate().getX(),
                    b.getCordinate().getY(),
                    BRICK_WIDTH, BRICK_HEIGHT);

        }

    }

    private String getBrickColor(Brick b) {
        switch (b.getBrickType()) {
            case YELLOW:
                return YELLOW_BRICK;
            case BLUE:
                if (b.getBrickLives() == 1) return BLUE_TWO_BRICK;
                else return BLUE_ONE_BRICK;
            case RED:
                if (b.getBrickLives() == 1) return RED_THREE_BRICK;
                else if (b.getBrickLives() == 2) return RED_TWO_BRICK;
                else return RED_ONE_BRICK;
        }
        return YELLOW_BRICK;
    }

    @Override
    public void showBall() {
        ImageElement img = ImageElement.as(new Image("images/" + BALL).getElement());
        context.drawImage(img, gameController.getBallXPos(), gameController.getBallYPos());
        gameController.launchBall();
    }

    @Override
    public void gameOver() {

    }

    @Override
    public void levelWon() {

    }
}
