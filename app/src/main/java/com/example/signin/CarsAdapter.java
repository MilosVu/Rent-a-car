package com.example.signin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import domain.Car;
import domain.User;

public class CarsAdapter extends ArrayAdapter<Car> {

    public CarsAdapter(Context context, ArrayList<Car> cars) {
        super(context, 0, cars);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Car car = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.car_layout, parent, false);
        }
        // Lookup view for data population
        ImageView carImageView = convertView.findViewById(R.id.carImageView);
        TextView brandTextView = convertView.findViewById(R.id.brandTextView);
        TextView modelTextView = convertView.findViewById(R.id.modelTextView);
        TextView yearTextView = convertView.findViewById(R.id.yearTextView);
        TextView transmissionTextView = convertView.findViewById(R.id.transmissionTextView);
        TextView typeTextView = convertView.findViewById(R.id.typeTextView);
        TextView priceTextView = convertView.findViewById(R.id.priceTextView);
        TextView fuelTextView = convertView.findViewById(R.id.fuelTextView);
        TextView seatsTextView = convertView.findViewById(R.id.seatsTextView);

        // Populate the data into the template view using the data object
        try {
            URL url = new URL(car.getImageURL());
            Bitmap myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            carImageView.setImageBitmap(myBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        brandTextView.setText("BRAND: "+ car.getBrand());
        modelTextView.setText("MODEL: "+ car.getModel());
        yearTextView.setText("YEAR: "+car.getYear());
        transmissionTextView.setText("TRANSMISSION: "+car.getTransmission());
        typeTextView.setText("TYPE: "+car.getType());
        priceTextView.setText("PRICE: "+car.getPrice());
        fuelTextView.setText("FUEL: "+car.getFuel());
        seatsTextView.setText("SEATS: "+car.getSeats());
        // Return the completed view to render on screen
        return convertView;
    }

}
