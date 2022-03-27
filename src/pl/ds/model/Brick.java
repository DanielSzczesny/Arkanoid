package pl.ds.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static pl.ds.shared.Constants.*;

/**
 * Klasa pojedynczej cegły, w zależności od poziomu 0/1/2 (wybieranego za pomocą Random)
 * wystawiana ilość potrzebnych uderzeń aby zbić cegłę
 */

public class Brick {

    private BrickType brickType;
    private int brickLives;
    private Cordinate cordinate;

    public Brick(int brickType, Cordinate cordinate){
        this.brickType = BrickType.valueOf(brickType);
        this.brickLives = getLives(this.brickType);
        this.cordinate = cordinate;
    }

    private int getLives(BrickType brickType) {
        if (brickType == BrickType.YELLOW) return 1;
        else if (brickType == BrickType.BLUE) return 2;
        else return 3;
    }

    public int getBrickLives() {
        return brickLives;
    }

    public void setBrickLives(int brickLives) {
        this.brickLives = brickLives;
    }

    public Cordinate getCordinate() {
        return cordinate;
    }

    public BrickType getBrickType() {
        return brickType;
    }

    public static List<Brick> createBricks(int amountOfBricks) {
        List<Brick> bricks = new LinkedList<>();
        Random random = new Random();
        double tempYPos = BRICK_Y_START_POS;

        for (int i = 0; i < amountOfBricks; i++) {
            bricks.add(new Brick(random.nextInt(BrickType.values().length),new Cordinate(tempYPos ,BRICK_X_START_POS)));
            tempYPos += BRICK_WIDTH;
        }
        return bricks;
    }
}
