package pl.ds.shared;

import com.google.gwt.canvas.dom.client.CssColor;

public class Constants {

    //czas
    public static final int ONE_SECOND_IN_MSECOND = 1000; // 1s = 1000ms
    public static final long DEFAULT_TIME_FOR_GAME = 360000; //6 minut
    public static final int TIME_BETWEEN_FRAMES = 33; //ms ~30FPS

    //wielkości
    public static final int CANVAS_HEIGHT = 800;
    public static final int CANVAS_WIDTH = 1600;
    public static final int BALL_RADIUS = 10;
    public static final int BRICK_HEIGHT = 25;
    public static final int BRICK_WIDTH = 80;
    public static final int ROCKET_HEIGHT = 30;
    public static final int ROCKET_WIDTH = 120; //łatwiej do obliczeń (-60o - 60o)

    //prędkości
    public static final double BALL_SPEED = 10.0;
    public static final double ROCKET_SPEED = 20.0;

    //ramy poruszania się piłki i paletki
    public static final int ROCKET_MAX_LEFT = 0;
    public static final int ROCKET_MAX_RIGHT = CANVAS_WIDTH - ROCKET_WIDTH + BALL_RADIUS;
    public static final int BALL_MIN_Y = 0;
    //utrata życia gdy przekroczy CANVAS_HEIGHT, +50 aby piłka była niewidoczna
    public static final int BALL_MAX_Y = CANVAS_HEIGHT + 50;
    public static final int BALL_MIN_X = 0;
    public static final int BALL_MAX_X = CANVAS_WIDTH - BALL_RADIUS;

    //pozycje początkowe
    public static final int ROCKET_Y_POS = CANVAS_HEIGHT - ROCKET_HEIGHT;
    public static final int ROCKET_X_POS = CANVAS_WIDTH / 2 - ROCKET_WIDTH;
    public static final int BALL_START_X_POS = CANVAS_WIDTH / 2 - BALL_RADIUS;
    public static final int BALL_START_Y_POS = ROCKET_Y_POS - (4 * BALL_RADIUS);
    public static final double BRICK_X_START_POS = 150.0;
    public static final double BRICK_Y_START_POS = 0.0;
    public static final double POINTS_POSITION_X = CANVAS_WIDTH / 10.0 * 9;
    public static final double POINTS_POSITION_Y = 70;

    //teksty
    public static final String START = "CHOOSE YOUR DIFFICULTY AND CLICK THIS BUTTON TO START";
    public static final String CANVAS_NOT_SUPPORTED = "Sorry, your browser doesn't support the HTML5 Canvas element";

    //zdjęcia
    public static String BACKGROUND_IMAGE = "background.png";
    public static String YELLOW_BRICK = "yellow.jpg";
    public static String BLUE_ONE_BRICK = "blue_one.jpg";
    public static String BLUE_TWO_BRICK = "blue_two.jpg";
    public static String RED_ONE_BRICK = "red_one.jpg";
    public static String RED_TWO_BRICK = "red_two.jpg";
    public static String RED_THREE_BRICK = "red_three.jpg";
    public static String BALL = "ball.png";
    public static String ROCKET = "rocket.png";

    //dźwięki
    public static String BALL_BOUNCE = "ball_hit.mp3";
    public static String GAME_DONE = "game_win.mp3";
    public static String GAME_OVER = "game_over.mp3";
}
