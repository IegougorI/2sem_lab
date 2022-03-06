package database;

import java.io.Serializable;

public class House implements Serializable {
    private String houseName; //Поле не может быть null
    private int year; //Максимальное значение поля: 720, Значение поля должно быть больше 0
    private long numberOfLifts; //Значение поля должно быть больше 0

    public static final int MaxYear = 720;
    public static final int MinYear = 0;
    public static final long MinNumberOfLifts = 0;


    public House(String houseName, int year, long numberOfLifts) {
        this.houseName =houseName;
        this.year=year;
        this.numberOfLifts =numberOfLifts;
    }

    public int getYear() {
        return year;
    }

    public long getNumberOfLifts() {
        return numberOfLifts;
    }

    public String getHouseName() {
        return houseName;
    }
}