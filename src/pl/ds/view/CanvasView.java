package pl.ds.view;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.Composite;
import pl.ds.controller.GameController;
import pl.ds.model.Brick;
import pl.ds.model.Difficulty;

import java.util.List;

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
        return null;
    }

    @Override
    public void nextFrame() {

    }

    @Override
    public void showBricks(List<Brick> bricks) {

    }

    @Override
    public void showBall() {

    }
}
