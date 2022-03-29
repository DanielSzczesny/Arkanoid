package pl.ds.shared;

import static pl.ds.shared.Constants.DEFAULT_TIME_FOR_GAME;

public class Timer {

    private int minutes;
    private int seconds;

    public Timer() {
        this.seconds = 59;
    }

    public Timer(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setMinutes(long timeInMilisec) {
        this.minutes = (int) (timeInMilisec / 60000);
    }

    public void setSeconds(long timeInMilisec) {
        this.seconds = (int) ((timeInMilisec % 60000) / 1000);
    }

    public static int getMinutes(long timeInMilisec) {
        return (int) (timeInMilisec / 60000);
    }

    public static int getSeconds(long timeInMilisec) {
        return (int) ((timeInMilisec % 60000) / 1000);
    }

    public void timeElapse() {
        if (seconds > 0 || minutes > 0) {
            if (seconds > 0) seconds -= 1;
            else {
                if (minutes > 0) minutes -= 1;
                seconds = 59;
            }
        }
    }
}
