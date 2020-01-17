package domain;

import java.io.Serializable;

public class Car implements Serializable {
    private int carID;
    private String brand;
    private String model;
    private int year;
    private String transmission;
    private String type;
    private int price;
    private String fuel;
    private int seats;
//    public static final long serialVersionUID =1L;

    public Car(int carID, String brand, String model, int year, String transmission, String type, int price, String fuel, int seats) {
        this.carID = carID;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.transmission = transmission;
        this.type = type;
        this.price = price;
        this.fuel = fuel;
        this.seats = seats;
    }

    public Car() {
    }
}
