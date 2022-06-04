package pl.ds.view;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.canvas.client.Canvas;
import pl.ds.model.Brick;

import java.util.List;

public interface View {

    Canvas createCanvas();
    void refreshCanvas();
    void showBricks(List<Brick> bricks);
    void showBall();
    void gameOver();
    void levelWon();
}
