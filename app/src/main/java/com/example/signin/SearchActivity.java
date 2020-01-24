package com.example.signin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import domain.Car;
import domain.Search;
import domain.User;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private TextView dateFromTV;
    private TextView dateToTV;
    private DatePickerDialog.OnDateSetListener dateFromSetListener;
    private DatePickerDialog.OnDateSetListener dateToSetListener;
    public static Search search = null;

    Spinner brandSpinner;
    Spinner typeSpinner;
    EditText minPriceET;
    EditText maxPriceET;
    TextView errorSearchTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(bottom_navigation_listener);
        NavigationView sign_out = findViewById(R.id.sign_out);
        sign_out.setNavigationItemSelectedListener(sign_out_listener);

        dateFromTV = findViewById(R.id.dateFromTV);
        dateToTV = findViewById(R.id.dateToTV);
        brandSpinner = findViewById(R.id.brandSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        minPriceET = findViewById(R.id.minPriceET);
        maxPriceET = findViewById(R.id.maxPriceET);
        errorSearchTV = findViewById(R.id.errorSearchTV);

        String todayString = parseDateToString(Calendar.getInstance().getTime());
        dateFromTV.setText(todayString);
        dateToTV.setText(todayString);
        //UCITAVANJE U SPINNERE
        ArrayList<String> brands = new ArrayList<String>();
        ArrayList<String> types = new ArrayList<String>();

        brands.add("BMW");
        brands.add("Mercedes");
        brands.add("Skoda");
        brands.add("Bentley");
        brands.add("Rolls - Royce");
        brands.add("Volkswagen");
        brands.add("Renault");
        brands.add("Audi");
        brands.add("Nissan");
        brands.add("Opel");

        types.add("SUV");
        types.add("Saloon");
        types.add("Cabriolet");
        types.add("Coupe");
        types.add("Minivan");
        types.add("Estate");
        //       List<Car> cars = new ArrayList<>();
        //KAKO BISMO UCITALI PODATKE OD SERVERA
//        MainActivity.streamToServer.println("advertisedCars");
//            try {
//            do{
//                Car car = (Car) MainActivity.objectInputStream.readObject();
//                cars.add(car);
//                System.out.println("Dodat car");
//            }while(car != null);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//        for (Car c : cars) {
//            String brand = c.getBrand();
//            String type = c.getType();
//            brands.add(brand);
//            types.add(type);
//        }

        brandSpinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, brands));
        typeSpinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, types));
        //SPAKOVACEMO OVAJ KOD ZA DATUME JEDNOG DANA U KRACI
        dateFromTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SearchActivity.this,
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
                Log.d(TAG, "onDateSet: MM/dd/yyyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                dateFromTV.setText(date);
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
                        SearchActivity.this,
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
                Log.d(TAG, "onDateSet: MM/dd/yyyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                dateToTV.setText(date);
            }
        };
    }

    private String parseDateToString(Date time) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = dateFormat.format(time);
        return dateString;
    }


    public void Search(View view){
        String brand = brandSpinner.getSelectedItem().toString();
        String type = typeSpinner.getSelectedItem().toString();
        String dateFromString = dateFromTV.getText().toString();
        String dateToString = dateToTV.getText().toString();
        String minPriceString = minPriceET.getText().toString();
        String maxPriceString = maxPriceET.getText().toString();

        //PROVERA ZA CENU
        if (minPriceString.isEmpty() || maxPriceString.isEmpty()) {
            errorSearchTV.setText("Enter a min/max price.");
            return;
        }
        int minPrice = 0;
        int maxPrice = 0;
        try {
            minPrice = Integer.parseInt(minPriceString);
            maxPrice = Integer.parseInt(maxPriceString);
            if (minPrice>maxPrice) {
                errorSearchTV.setText("Minimum price must be less or equal to maximum price.");
                return;
            }
        } catch (NumberFormatException e) {
            errorSearchTV.setText("Price must be a number.");
            return;
        }



        //PROVERA ZA DATUM - SALJI
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("mm/dd/yyyy");
        try {
            Date dateFrom = simpleDateFormat.parse(dateFromString);
            Date dateTo = simpleDateFormat.parse(dateToString);
            System.out.println(dateFrom.toString());
            System.out.println(dateTo.toString());
            if (validDates(dateFrom,dateTo)) {
                search = new Search(brand,type,dateFrom,dateTo,minPrice,maxPrice);
                String keyValue = "searchedCars";
                Intent i = new Intent(SearchActivity.this, DiscoverActivity.class);
                i.putExtra("key",keyValue);
                startActivity(i);
            }
            else {
                errorSearchTV.setText("Invalid dates");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean validDates(Date dateFrom, Date dateTo) {
        if (dateFrom == null || dateTo == null || dateFrom.before(new Date()) || dateTo.before(new Date()) || dateFrom.after(dateTo))
            return false;
        return true;
    }

    public NavigationView.OnNavigationItemSelectedListener sign_out_listener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    startActivity(new Intent(SearchActivity.this,MainActivity.class));
                    return true;
                }
            };

    public BottomNavigationView.OnNavigationItemSelectedListener bottom_navigation_listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()) {
                        case R.id.nav_home:
                            startActivity(new Intent(SearchActivity.this,DiscoverActivity.class));
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
