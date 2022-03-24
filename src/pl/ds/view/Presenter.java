package pl.ds.view;

import com.google.gwt.canvas.client.Canvas;
import pl.ds.model.Game;

public interface Presenter {

    void listenToTheGame();
    void onKeyHit(Canvas canvas);
    void rocketMove();
    void launchBall();
    double getBallXPos();
    double getBallYPos();
    void putBricksToMemory();
    double getRocketXPos();
    Game getGame();
}
