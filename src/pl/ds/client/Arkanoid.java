package pl.ds.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import pl.ds.model.Difficulty;
import pl.ds.shared.TimeWrapper;
import pl.ds.view.CanvasView;

import static pl.ds.shared.Constants.START;
import static pl.ds.shared.Constants.TIME_BETWEEN_FRAMES;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Arkanoid implements EntryPoint {
  private static final String canvasDivTag = "gameCanvas";
  private static final String menuDivTag = "gameMenu";
  private ListBox difficulties;
  private CanvasView canvasView;

  public void onModuleLoad() {


    final Timer timer;
    Button button = new Button(START);
    difficulties = new ListBox();
    for (Difficulty d : Difficulty.values()) {
      difficulties.addItem(d.name());
    }
    difficulties.setVisibleItemCount(1);

    RootPanel.get(menuDivTag).add(button);
    RootPanel.get(menuDivTag).add(difficulties);
    timer = new Timer() {
      @Override
      public void run() {
        canvasView.refreshCanvas();
      }
    };
    TimeWrapper.getInstance().setTimer(timer);
    button.addClickHandler(clickEvent -> {
      if (canvasView != null)
        RootPanel.get(canvasDivTag).remove(canvasView);
      timer.scheduleRepeating(TIME_BETWEEN_FRAMES);
      canvasView = new CanvasView(Difficulty.valueOf(difficulties.getSelectedItemText()));
      RootPanel.get(canvasDivTag).add(canvasView);
    });
  }
}


