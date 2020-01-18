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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import domain.Car;
import domain.User;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private TextView dateFromTV;
    private TextView dateToTV;
    private DatePickerDialog.OnDateSetListener dateFromSetListener;
    private DatePickerDialog.OnDateSetListener dateToSetListener;

    Spinner brandSpinner;
    Spinner modelSpinner;
    Spinner typeSpinner;
    EditText minPriceET;
    EditText maxPriceET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dateFromTV = findViewById(R.id.dateFromTV);
        dateToTV = findViewById(R.id.dateToTV);
        brandSpinner = findViewById(R.id.brandSpinner);
        modelSpinner = findViewById(R.id.modelSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        minPriceET = findViewById(R.id.minPriceET);
        maxPriceET = findViewById(R.id.maxPriceET);


        //UCITAVANJE U SPINNERE
        ArrayList<String> brands = new ArrayList<String>();
        ArrayList<String> models = new ArrayList<String>();
        ArrayList<String> types = new ArrayList<String>();
        brands.add("brand1");
        brands.add("brand2");
        //       List<Car> cars = new ArrayList<>();
        //OVDE ZAHTEV ZA SVE AUTOMOBILE A NE SAMO ADVERTISED
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
//            String model = c.getModel();
//            String type = c.getType();
//            brands.add(brand);
//            models.add(model);
//            types.add(type);
//        }

        brandSpinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, brands));
//        modelSpinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, models));
//        typeSpinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, types));

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
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

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
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                dateToTV.setText(date);
            }
        };
    }

    public void Search(View view){
        String brand = brandSpinner.getSelectedItem().toString();
        String model = modelSpinner.getSelectedItem().toString();
        String type = typeSpinner.getSelectedItem().toString();
        String dateFrom = dateFromTV.getText().toString();
        String dateTo = dateToTV.getText().toString();
        int minPrice = Integer.parseInt(minPriceET.getText().toString());
        int maxPrice = Integer.parseInt(maxPriceET.getText().toString());

        //DA LI DA UBACIMO TRANSMISSION I TA SRANJA
        //NAPRAVI PROVERU ZA POLJA
        //POSALJI OBJEKAT SERVERU
        //OTVORI NOVU STRANU S AUTOMOBILIMA - KAO I DISCOVER DA BUDE
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
