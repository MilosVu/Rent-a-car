package domain;

import java.io.Serializable;

public class Search implements Serializable {
    private String[][] brandAndModel;
    private int[] year;
    private String transmission;
    private String[] type;
    private int[] price;
    private String fuel;
    private int[] seats;
//    public static final long serialVersionUID =3L;

    public Search(String[][] brandAndModel, int[] year, String transmission, String[] type, int[] price, String fuel, int[] seats) {
        this.brandAndModel = brandAndModel;
        this.year = year;
        this.transmission = transmission;
        this.type = type;
        this.price = price;
        this.fuel = fuel;
        this.seats = seats;
    }

    public Search() {
    }
}
