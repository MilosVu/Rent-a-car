package domain;

import java.io.Serializable;
import java.util.Date;

public class Search implements Serializable {
    private String brand;
    private String type;
    private Date dateFrom;
    private Date dateTo;
    private int minPrice;
    private int maxPrice;

//    public static final long serialVersionUID =3L;


    public Search(String brand, String type, Date dateFrom, Date dateTo, int minPrice, int maxPrice) {
        this.brand = brand;
        this.type = type;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
