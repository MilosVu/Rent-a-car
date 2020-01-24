package com.example.signin;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.Car;
import domain.Search;

public class DiscoverActivity extends ListActivity {

    String keyValue;
    public static boolean search = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        //INICIJALIZUJEMO NIZ U KOJI CEMO UCITATI AUTOMOBILE OD SERVERA
        Car car;
        ArrayList<Car> cars = new ArrayList<>();


        //UCITAVAMO KLJUC
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            keyValue = extras.getString("key");
        }

        if( keyValue!= null && keyValue.equals("searchedCars")){
            MainActivity.streamToServer.println("search");

            try {
                System.out.println(MainActivity.streamFromServer.readLine());
                MainActivity.objectOutputStream.writeObject(SearchActivity.search);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("searched cars");
        }
        else{
            System.out.println("advertised cars");
            MainActivity.streamToServer.println("advertisedCars");
        }

        //NE ZNAM OTKUD OVO
        Context context = getApplicationContext();
        Resources resources = context.getResources();

        //U SVAKOM SLUCAJU UCITAVAMO AUTOMOBILE U LISTU
//        Car car1 = new Car(1,"b1","m1",1,"t1","tip1",111,"f1",4,"https://www.rentacaratos.com/assets/img/automobili/rent-car-beograd-fiat-grande-punto.jpg");
//        Car car2 = new Car(1,"b2","m2",2,"t2","tip2",222,"f2",5,null);
//        cars.add(car1);
//        cars.add(car2);

        try {
            do{
                car = (Car) MainActivity.objectInputStream.readObject();
                if(car != null)
                    cars.add(car);
            }while(car != null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (cars.isEmpty()) {
            TextView errorDiscoverTV = findViewById(R.id.errorDiscoverTV);
            errorDiscoverTV.setText("No such cars available.");
        }
        else {
            CarsAdapter adapter = new CarsAdapter(this,cars);
            final ListView listView = findViewById(android.R.id.list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Car listItem = (Car)listView.getItemAtPosition(position);
                    Intent i = new Intent(DiscoverActivity.this, ReservationActivity.class);
                    i.putExtra("key",listItem);
                    startActivity(i);
                }
            });
        }

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
