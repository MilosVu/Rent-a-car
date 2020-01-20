package domain;

import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {
    private int ReservationID;
    private String username;
    private int carID;
    private Date pickUpDate;
    private Date returnDate;
    private int price;
//    public static final long serialVersionUID =4L;

    public Reservation(int reservationID, String username, int carID, Date pickUpDate, Date returnDate, int price) {
        ReservationID = reservationID;
        this.username = username;
        this.carID = carID;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
        this.price = price;
    }

    public Reservation(String username, int carID, Date pickUpDate, Date returnDate, int price) {
        this.username = username;
        this.carID = carID;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
        this.price = price;
    }

    public Reservation() {
    }

}
