package pl.ds.model;

public enum BrickType {
    YELLOW, BLUE, RED;

    public static BrickType valueOf(int brickType) {
        if (brickType == 0) {
            return BrickType.YELLOW;
        } else if (brickType == 1) {
            return BrickType.BLUE;
        } else {
            return BrickType.RED;
        }
    }
}
