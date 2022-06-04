package pl.ds.client;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.*;
import pl.ds.model.Difficulty;
import pl.ds.shared.TimeWrapper;
import pl.ds.view.CanvasView;

import static pl.ds.shared.Constants.START;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Arkanoid implements EntryPoint {
  private static final String canvasDivTag = "gameCanvas";
  private static final String menuDivTag = "gameMenu";
  private ListBox difficulties;
  private CanvasView canvasView;

  public void onModuleLoad() {

    Duration duration = new Duration();

    Element element = Element.as(RootPanel.getBodyElement());
    if (element != null) {
      element.appendChild(RootPanel.get(canvasDivTag).getElement());
    }
    Button button = new Button(START);
    difficulties = new ListBox();
    for (Difficulty d : Difficulty.values()) {
      difficulties.addItem(d.name());
    }
    difficulties.setVisibleItemCount(1);

    RootPanel.get(menuDivTag).add(button);
    RootPanel.get(menuDivTag).add(difficulties);

    Animation animation = new Animation() {
      @Override
      protected void onUpdate(double v) {
        canvasView.refreshCanvas();
//        AnimationScheduler.get().requestAnimationFrame(this);
      }
    };

    animation.run(duration.elapsedMillis(), duration.getStartMillis(), element);

    final AnimationScheduler.AnimationCallback callback = v ->
            animation.run(duration.elapsedMillis(), duration.getStartMillis(), element.getFirstChildElement());

    callback.execute(TimeWrapper.getInstance().getFrameNumber());

    AnimationScheduler.get().requestAnimationFrame((AnimationScheduler.AnimationCallback) this);


//    TimeWrapper.getInstance().setTimer(timer);
    button.addClickHandler(clickEvent -> {
      if (canvasView != null)
        RootPanel.get(canvasDivTag).remove(canvasView);
//      timer.scheduleRepeating(TIME_BETWEEN_FRAMES);
      canvasView = new CanvasView(Difficulty.valueOf(difficulties.getSelectedItemText()));
      RootPanel.get(canvasDivTag).add(canvasView);
    });
  }
}


