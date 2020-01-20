package com.example.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import domain.Car;
import domain.Reservation;
import domain.Search;

public class ReservationActivity extends AppCompatActivity {
    Car car;
    private TextView dateFromTV;
    private TextView dateToTV;
    private TextView totalPriceTV;
    private DatePickerDialog.OnDateSetListener dateFromSetListener;
    private DatePickerDialog.OnDateSetListener dateToSetListener;
    private TextView errorReservationTV;
    private static final String TAG = "ReservationActivity";
    Date dateFrom;
    Date dateTo;
    int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        NavigationView sign_out = findViewById(R.id.sign_out);
        sign_out.setNavigationItemSelectedListener(sign_out_listener);

        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(bottom_navigation_listener);

        dateFromTV = findViewById(R.id.resDateFromTV);
        dateToTV = findViewById(R.id.resDateToTV);
        totalPriceTV = findViewById(R.id.totalPriceTV);
        errorReservationTV = findViewById(R.id.errorReservationTV);
        try {
            setDateObjects();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            car = (Car) extras.get("key");
            ArrayList<Car> cars = new ArrayList<Car>();
            cars.add(car);
            CarsAdapter adapter = new CarsAdapter(this,cars);
            ListView listView = findViewById(android.R.id.list);
            listView.setAdapter(adapter);
        }



        //SPAKOVACEMO OVAJ KOD ZA DATUME JEDNOG DANA U KRACI
        dateFromTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReservationActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateFromSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateFromSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                dateFromTV.setText(date);
                setTotalPrice();
            }
        };

        dateToTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReservationActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateToSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateToSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                dateToTV.setText(date);
                setTotalPrice();

            }
        };

    }

//    private void setTotalPrice() {
//        String dateFromString = dateFromTV.getText().toString();
//        String dateToString = dateToTV.getText().toString();
//        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("mm/dd/yyyy");
//        try {
//            Date dateFrom = simpleDateFormat.parse(dateFromString);
//            Date dateTo = simpleDateFormat.parse(dateToString);
//            if (dateTo.after(dateFrom)) {
//                int totalPrice = calculatePrice(dateFrom,dateTo);
//                totalPriceTV.setText(totalPrice+" $");
//            }
//            else {
//                errorReservationTV.setText("Date-To must be after Date-From.");
//                return;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void setTotalPrice() {
        try {
           setDateObjects();
            if (validDates(dateFrom, dateTo)) {
                totalPrice = calculatePrice(dateFrom,dateTo);
                totalPriceTV.setText(totalPrice+" $");
                errorReservationTV.setText("");
            }
            else {
                errorReservationTV.setText("Invalid dates.");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDateObjects() throws ParseException {
        dateFrom = parseStringToDate(dateFromTV.getText().toString());
        dateTo = parseStringToDate(dateToTV.getText().toString());
    }

    private Date parseStringToDate(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("mm/dd/yyyy");
        Date date = simpleDateFormat.parse(dateString);
        return date;
    }

    private boolean validDates(Date dateFrom, Date dateTo) {
        if (dateFrom == null || dateTo == null || dateFrom.before(new Date()) || dateTo.before(new Date()) || dateFrom.after(dateTo))
            return false;
        return true;
    }

    public int calculatePrice(Date dateFrom, Date dateTo) {
        long milliseconds = dateTo.getTime() - dateFrom.getTime();
        int days = (int) (milliseconds/(1000*60*60*24));
        int price = days*car.getPrice();
        return price;
    }

    public void Reserve(View view){
        if (validDates(dateFrom, dateTo)) {
            //SALJI RESERVATION OBJEKAT
//            try {
//                Reservation reservation = new Reservation(MainActivity.user.getUsername(), car.getCarID(), dateFrom, dateTo, totalPrice);
//                MainActivity.streamToServer.println("reserve");
//                MainActivity.objectOutputStream.writeObject(reservation);
//                String response = MainActivity.streamFromServer.readLine();
//                errorReservationTV.setText(response);
//                if (response.equals("Reservation accepted")) {
//                    errorReservationTV.setTextColor(Color.GREEN);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            //ispisi poruku da li je uspesno, da li je zauzet auto
            // TESTIRANJE:
//            System.out.println(MainActivity.user.getUsername());
//            System.out.println(car.getCarID());
//            System.out.println(dateFrom.toString());
//            System.out.println(dateTo.toString());
//            System.out.println(totalPrice);
//            System.out.println("super");
        }
        else
            return;
    }

    public NavigationView.OnNavigationItemSelectedListener sign_out_listener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    startActivity(new Intent(ReservationActivity.this,MainActivity.class));
                    return true;
                }
            };

    public BottomNavigationView.OnNavigationItemSelectedListener bottom_navigation_listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()) {
                        case R.id.nav_home:
                            startActivity(new Intent(ReservationActivity.this,DiscoverActivity.class));
                            break;
                        case R.id.nav_search:
                            startActivity(new Intent(ReservationActivity.this,SearchActivity.class));
                            break;
                        case R.id.nav_account:
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            };
}
