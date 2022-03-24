package pl.ds.view;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.*;
import pl.ds.controller.GameController;
import pl.ds.model.Brick;
import pl.ds.model.Difficulty;
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
        canvas.setHeight(CANVAS_WIDTH + "px");
        canvas.setCoordinateSpaceHeight(CANVAS_HEIGHT);

        addKeyListeners();

        context = canvas.getContext2d();
        return canvas;
    }

    @Override
    public void nextFrame() {

    }

    private void addKeyListeners() {
    }

    @Override
    public void refreshCanvas() {
        CssColor color = CssColor.make(255, 255, 255);

        context.setFillStyle(color);
        context.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        context.closePath();
        ImageElement img = ImageElement.as(new Image("images/" + BACKGROUND_IMAGE).getElement());
        context.drawImage(img, 0 , 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        TimeWrapper.getInstance().nextFrame();
    }

    @Override
    public void showBricks(List<Brick> bricks) {

    }

    @Override
    public void showBall() {

    }
}
