package database;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private long x;
    private final Double y;

    public Coordinates (long m, Double n){
        x = m;
        y = n;
    }
    public long getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}