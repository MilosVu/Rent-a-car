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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

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


//        Car car1 = new Car(1,"b1","m1",1,"t1","tip1",111,"f1",4,null);
//        Car car2 = new Car(1,"b2","m2",2,"t2","tip2",222,"f2",5,null);
//        car1.setImageURL("https://www.rentacaratos.com/assets/img/automobili/rent-car-beograd-fiat-grande-punto.jpg");
//        car2.setImageURL("https://www.rentacaratos.com/assets/img/automobili/rent-car-beograd-peugeot-107.jpg");
//        cars.add(car1);
//        cars.add(car2);

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
