package pl.ds.model;

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
}
