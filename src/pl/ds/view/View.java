package pl.ds.view;

import com.google.gwt.canvas.client.Canvas;
import pl.ds.model.Brick;

import java.util.List;

public interface View {
    Canvas createCanvas();
    void nextFrame();

    void refreshCanvas();

    void showBricks(List<Brick> bricks);
    void showBall();
}
