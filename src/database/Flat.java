package database;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Flat implements Serializable{

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private  String name; //Поле не может быть null, Строка не может быть пустой
    private  Coordinates coordinates; //Поле не может быть null
    private  java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private  Double area; //Максимальное значение поля: 997, Значение поля должно быть больше 0
    private  long numberOfRooms; //Максимальное значение поля: 13, Значение поля должно быть больше 0
    private  Furnish furnish; //Поле может быть null
    private  View view; //Поле не может быть null
    private  Transport transport; //Поле не может быть null
    private  House house; //Поле может быть null
    public static final Double MaxArea = 997.0;
    public static final Double MinArea = (double)0;
    public static final long MinNumberOfRooms = 0;
    public static final long MaxNumberOfRooms = 13;
    private long x;
    private double yfs;
    private String houseName;
    private int year;
    private long numberOfLifts;


    private String owner;
    private String key;



    public Flat(String name, Coordinates coordinates, Double area,
                long numberOfRooms, Furnish furnish, View view, Transport transport, House house, java.time.LocalDateTime creationDate, String owner, long id){
        this.name=name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.furnish=furnish;
        this.area=area;
        this.numberOfRooms=numberOfRooms;
        this.view=view;
        this.transport=transport;
        this.house=house;
        this.x =coordinates.getX();
        this.yfs=coordinates.getY();
        this.houseName=house.getHouseName();
        this.year=house.getYear();
        this.numberOfLifts=house.getNumberOfLifts();
        this.owner =owner;
        this.id=id;
    }
    public Flat(String name, Coordinates coordinates,  Double area,
                long numberOfRooms, Furnish furnish, View view, Transport transport, House house, java.time.LocalDateTime creationDate, long id){
        this.name=name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.furnish=furnish;
        this.area=area;
        this.numberOfRooms=numberOfRooms;
        this.view=view;
        this.transport=transport;
        this.house=house;
        this.x =coordinates.getX();
        this.yfs=coordinates.getY();
        this.houseName=house.getHouseName();
        this.year=house.getYear();
        this.numberOfLifts=house.getNumberOfLifts();
        this.id=id;
    }


    public long getNumberOfRooms() {
        return numberOfRooms;
    }


    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Furnish getFurnish() {
        return furnish;
    }

    public double getYfs() {
        return yfs;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setYfs(double yfs) {
        this.yfs = yfs;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Double getArea() {
        return area;
    }

    public Transport getTransport() {
        return transport;
    }

    public House getHouse() {
        return house;
    }

    public long getX() {
        return x;
    }

    public String getHouseName() {
        return houseName;
    }

    public int getYear() {
        return year;
    }

    public long getNumberOfLifts() {
        return numberOfLifts;
    }

    @Override
    public String toString() {
        return "" + name +
                "," + x +
                "," + yfs +
                "," + area +
                "," + numberOfRooms +
                "," + furnish +
                "," + view +
                "," + transport +
                "," + houseName +
                "," + year +
                "," + numberOfLifts +
                "," + creationDate +
                "," + owner +
                "," + id
                ;
    }




}
