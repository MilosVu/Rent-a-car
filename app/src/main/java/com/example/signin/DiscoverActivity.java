package com.example.signin;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.Car;

public class DiscoverActivity extends ListActivity {

    String keyValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        //INICIJALIZUJEMO NIZ U KOJI CEMO UCITATI AUTOMOBILE OD SERVERA
        //Car car;
//        List<Car> cars = new ArrayList<>();

        //UCITAVAMO KLJUC
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            keyValue = extras.getString("key");
        }

        //AKO NISMO POSLALI KLJUC DA ZELIMO SEARCHED CARS, POKAZUJU SE ADVERTISED
        if (keyValue == null) {
            System.out.println("advertised cars");
        //  MainActivity.streamToServer.println("advertisedCars");
        }

        //AKO SMO POSLALI KLJUC ZA SEARCHED CARS, ONI SE PRIKAZUJU
        //U SUSTINI OVO SE BRISE
        else if(keyValue != null && keyValue.equals("searchedCars")) {
            System.out.println("searched cars");
        }

        //NE ZNAM OTKUD OVO
       // Context context = getApplicationContext();
       // Resources resources = context.getResources();

        //U SVAKOM SLUCAJU UCITAVAMO AUTOMOBILE U LISTU
        Context context = getApplicationContext();
        Resources resources = context.getResources();


        Car car;
        ArrayList<Car> cars = new ArrayList<>();
        MainActivity.streamToServer.println("advertisedCars");
        try {
            do{
                car = (Car) MainActivity.objectInputStream.readObject();
                cars.add(car);
                System.out.println("Dodat car");
            }while(car != null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        CarsAdapter adapter = new CarsAdapter(this,cars);
        ListView listView = findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        NavigationView sign_out = findViewById(R.id.sign_out);
        sign_out.setNavigationItemSelectedListener(sign_out_listener);

        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(bottom_navigation_listener);

    }

    public NavigationView.OnNavigationItemSelectedListener sign_out_listener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    startActivity(new Intent(DiscoverActivity.this,MainActivity.class));
                    return true;
                }
            };

    public BottomNavigationView.OnNavigationItemSelectedListener bottom_navigation_listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()) {
                        case R.id.nav_home:
                            startActivity(new Intent(DiscoverActivity.this,DiscoverActivity.class));
                            break;
                        case R.id.nav_search:
                            startActivity(new Intent(DiscoverActivity.this,SearchActivity.class));
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
