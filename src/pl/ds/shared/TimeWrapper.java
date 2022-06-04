package pl.ds.shared;

import com.google.gwt.user.client.Timer;

import java.time.LocalTime;

public class TimeWrapper {

    private static TimeWrapper instance;
    private long frameNumber;
    private Timer timer;
    private Timer clock;


    public static TimeWrapper getInstance() {
        if (instance == null) {
            synchronized (TimeWrapper.class) {
                if (instance == null) instance = new TimeWrapper();
                instance.resetFrame();
            }
        }
        return instance;
    }

    private TimeWrapper() {
        if (instance != null)
            throw new IllegalStateException("Cannot create new instance, please use getInstance() method instead.");
    }

    public void resetFrame() {
        this.frameNumber = 0;
    }

    public long getFrameNumber() {
        return frameNumber;
    }

    /**
     * Ustawienie timera na początku gry
     * @param timer instancja Timer z biblioteki GWT
     */
    public void setTimer(Timer timer) {
        if (this.frameNumber == 0)
            this.timer = timer;
    }

    public void nextFrame() {
        frameNumber++;
    }

    public void setClock(Timer clock) {
        this.clock = clock;
    }
}
