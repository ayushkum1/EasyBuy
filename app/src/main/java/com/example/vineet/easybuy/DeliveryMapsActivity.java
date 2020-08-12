package com.example.vineet.easybuy;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DeliveryMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng message,message1;
    SessionManager sessionManager;



    double lattitude1 = 13.1005,longitude1 = 77.5940;
    double lattitude2 = 13.0631,longitude2 = 77.6207;
    LatLng origin = new LatLng(13.1005, 77.5940);
    LatLng destination = new LatLng(13.0631, 77.6207);
    TextView dist_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        sessionManager = new SessionManager(this);

        dist_tv = findViewById(R.id.dist);


        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();
        String message = intent.getStringExtra(Delivery.EXTRA_MESSAGE);
        String message1 = intent.getStringExtra(Delivery.EXTRA_MESSAGE);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        String lat1 = sessionManager.getPreferences("lat1");
        String lng1 = sessionManager.getPreferences("lng1");
        String lat2 = sessionManager.getPreferences("lat2");
        String lng2 = sessionManager.getPreferences("lng2");


        if (!lat1.isEmpty()){
            lattitude1 = Double.parseDouble(lat1);
            longitude1 = Double.parseDouble(lng1);
        }

        if (!lat2.isEmpty()){
            lattitude2 = Double.parseDouble(lat2);
            longitude2 = Double.parseDouble(lng2);
        }


        // Add a marker in Sydney and move the camera
        message = new LatLng(lattitude1, longitude1);
        mMap.addMarker(new MarkerOptions().position(message).title(""));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(message));
            float zoomLevel = 12.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(message, zoomLevel));
        mMap.addMarker(new MarkerOptions()
                .title("yelhanka")
                .position(message));
        message1 = new LatLng(lattitude2, longitude2);
        mMap.addMarker(new MarkerOptions().position(message1).title(""));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(message1));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(message1, zoomLevel));



        float[] results = new float[1];
        Location.distanceBetween(message.latitude, message.longitude,
                message1.latitude, message1.longitude,
                results);
        float distance =  results[0];
        float dist = (distance/1000);
        dist_tv.setText("Distance: "+String.valueOf(dist)+" KM");
        Log.e("dist", String.valueOf(dist));
        sessionManager.setPreferences("amount", String.valueOf(Math.round(dist*10)));
        sessionManager.setPreferences("distance", String.valueOf(Math.round(dist)));

    }

    public void proceed1(View view)
    {

        Intent intent = new Intent(this,DeliveryDataDisplay.class);
        startActivity(intent);
    }
    /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
    }

