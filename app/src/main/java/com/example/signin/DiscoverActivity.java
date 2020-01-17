package com.example.signin;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class DiscoverActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_discover);

        Context context = getApplicationContext();
        Resources resources = context.getResources();
        String[] slike_naslovi = {"Slika1", "Slika2", "Slika3"};
        TypedArray slike = resources.obtainTypedArray(R.array.slike);
        setListAdapter(new ImageAndTextAdapter(context, R.layout.car_layout, slike_naslovi, slike));

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
                    }
                    return true;
                }
            };

}
