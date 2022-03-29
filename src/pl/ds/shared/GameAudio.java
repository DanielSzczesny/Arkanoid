package pl.ds.shared;

import com.google.gwt.media.client.Audio;

import static pl.ds.shared.Constants.*;

public class GameAudio {

    private static void playSound(String sound) {
        Audio audio;
        audio = Audio.createIfSupported();
        audio.setSrc("sound/" + sound);
        audio.play();
    }

    public static void ballBounceSound() {
        playSound(BALL_BOUNCE);
    }

    public static void gameOverSound() {
        playSound(GAME_OVER);
    }

    public static void gameDoneSound() {
        playSound(GAME_DONE);
    }
}
