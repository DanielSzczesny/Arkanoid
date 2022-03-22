package pl.ds.model;

public class Cordinate {

    private double x;
    private double y;
    private CordinateType cordinateType;

    public Cordinate(){}

    public Cordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Cordinate(double x, double y, CordinateType cordinateType) {
        this.x = x;
        this.y = y;
        this.cordinateType = cordinateType;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Cordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
