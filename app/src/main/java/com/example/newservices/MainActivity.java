package com.example.newservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Locale;

import static android.icu.text.DisplayContext.LENGTH_SHORT;import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements LocationListener {

    Button button;
    Button addButton;
    TextView textView;

    Button dateButton;
    Button timeStamp;
    TextView date;

    LocationManager locationManager;

    Geocoder geocoder;

    Double latitude ;
    Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }


        button = findViewById(R.id.button1);
        addButton = findViewById(R.id.button01);
        textView = findViewById(R.id.text1);

        geocoder = new Geocoder(this, Locale.getDefault() );


        dateButton=findViewById(R.id.button2);
        timeStamp=findViewById(R.id.button02);
        date=findViewById(R.id.text2);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                    addButton.setVisibility(View.VISIBLE);

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    textView.setText(textView.getText() + "\n"+addresses.get(0).getAddressLine(0));
                }catch(Exception e)
                {
                     e.printStackTrace();
                }
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                String currentDateandTime = sdf.format(new Date());
                date.setText(currentDateandTime);
                timeStamp.setVisibility(View.VISIBLE);
            }
        });

        timeStamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Changing String Date to Timestaamp Left.
            }
        });
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        textView.setText("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude());
        latitude = location.getLatitude();
        longitude= location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
       //Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }


}
