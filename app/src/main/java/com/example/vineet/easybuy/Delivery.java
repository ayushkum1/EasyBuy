package com.example.vineet.easybuy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.vineet.easybuy.Common.ItemClickSupport;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.android.gms.location.places.Places;

public class Delivery extends AppCompatActivity {
    public static final int TEXT_REQUEST = 1;
    public static final String EXTRA_MESSAGE = "com.example.vineet.easybuy.extra.MESSAGE";
    private EditText pickup;
    private EditText drop;
    private EditText content;
    private EditText val;
    private EditText instruction;
    SessionManager sessionManager;

    CardView card1,card2;
    RecyclerView recyclerView1,recyclerView2;
    boolean itemclick1 = true;
    boolean itemclick2 = true;

    List primaryTextList1,secondaryTextList1;
    List<AutocompletePrediction> predictionList1;
    private AutoAdapter1 autoAdapter1;

    List primaryTextList2,secondaryTextList2;
    List<AutocompletePrediction> predictionList2;
    private AutoAdapter2 autoAdapter2;

    private  final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    protected GeoDataClient mGeoDataClient;

    String lat1="",lat2="";
    String lng1="",lng2="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        pickup=findViewById(R.id.et1);
        drop=findViewById(R.id.et2);
        content= findViewById(R.id.et3);
        val = findViewById(R.id.et4);
        instruction = findViewById(R.id.et5);

        sessionManager  = new SessionManager(this);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView2 = findViewById(R.id.recyclerView2);

        card1.setVisibility(View.GONE);
        card2.setVisibility(View.GONE);

        primaryTextList1 = new ArrayList();
        secondaryTextList1 = new ArrayList();
        predictionList1 = new ArrayList<>();


        primaryTextList2 = new ArrayList();
        secondaryTextList2 = new ArrayList();
        predictionList2 = new ArrayList<>();


        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(mLayoutManager1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        autoAdapter1 = new AutoAdapter1(this, primaryTextList1,secondaryTextList1);
        recyclerView1.setAdapter(autoAdapter1);


        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        autoAdapter2 = new AutoAdapter2(this, primaryTextList2,secondaryTextList2);
        recyclerView2.setAdapter(autoAdapter2);


        mGeoDataClient = Places.getGeoDataClient(this, null);
        pickup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (itemclick1){
                    itemclick1  =false;
                }else {
                    new AsyncCallWS(String.valueOf(s)).execute();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        ItemClickSupport.addTo(recyclerView1)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        AutocompletePrediction item= predictionList1.get(position);
                        String ptext = String.valueOf(item.getPrimaryText(STYLE_BOLD));
                        itemclick1 = true;
                        String add = primaryTextList1.get(position).toString();
                        pickup.setText(add);
                        final String placeId = item.getPlaceId();

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(pickup.getWindowToken(), 0);

                        primaryTextList1.clear();
                        secondaryTextList1.clear();
                        predictionList1.clear();
                        autoAdapter1.notifyDataSetChanged();

                        Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeId);
                        placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback1);

                    }
                });


        drop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (itemclick2){
                    itemclick2  =false;
                }else {
                    new AsyncCallWS2(String.valueOf(s)).execute();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        ItemClickSupport.addTo(recyclerView2)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        AutocompletePrediction item= predictionList2.get(position);
                        String ptext = String.valueOf(item.getPrimaryText(STYLE_BOLD));
                        itemclick2 = true;
                        String add = primaryTextList2.get(position).toString();
                        drop.setText(add);

                        final String placeId = item.getPlaceId();

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(pickup.getWindowToken(), 0);

                        primaryTextList2.clear();
                        secondaryTextList2.clear();
                        predictionList2.clear();
                        autoAdapter2.notifyDataSetChanged();

                        Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeId);
                        placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback2);

                    }
                });



    }



    private OnCompleteListener<PlaceBufferResponse> mUpdatePlaceDetailsCallback1 =
            new OnCompleteListener<PlaceBufferResponse>() {
                @Override
                public void onComplete(Task<PlaceBufferResponse> task)
                {
                    try {
                        PlaceBufferResponse places = task.getResult();

                        // Get the Place object from the buffer.
                        final Place place = places.get(0);
                        Log.e("placelatlng",""+place.getLatLng());

                        LatLng latLngData = place.getLatLng();

                        lat1 = String.valueOf(latLngData.latitude);
                        lng1 = String.valueOf(latLngData.longitude);


                        Log.e("lat", String.valueOf(latLngData.latitude));
                        Log.e("lng", String.valueOf(latLngData.longitude));
                        places.release();

                    } catch (RuntimeRemoteException e) {
                        // Request did not complete successfully
                        Log.e("TAG", "Place query did not complete.", e);
                        return;
                    }
                }
            };

    private OnCompleteListener<PlaceBufferResponse> mUpdatePlaceDetailsCallback2 =
            new OnCompleteListener<PlaceBufferResponse>() {
                @Override
                public void onComplete(Task<PlaceBufferResponse> task)
                {
                    try {
                        PlaceBufferResponse places = task.getResult();

                        // Get the Place object from the buffer.
                        final Place place = places.get(0);
                        Log.e("placelatlng",""+place.getLatLng());

                        LatLng latLngData = place.getLatLng();

                        lat2 = String.valueOf(latLngData.latitude);
                        lng2 = String.valueOf(latLngData.longitude);


                        Log.e("lat", String.valueOf(latLngData.latitude));
                        Log.e("lng", String.valueOf(latLngData.longitude));
                        places.release();

                    } catch (RuntimeRemoteException e) {
                        // Request did not complete successfully
                        Log.e("TAG", "Place query did not complete.", e);
                        return;
                    }
                }
            };


    public class AsyncCallWS extends AsyncTask<Void, Void, AutocompletePredictionBufferResponse>
    {
        private  final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
        CharSequence charSequence;
        private  final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
                new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

        AsyncCallWS(CharSequence charSequence){

            this.charSequence = charSequence;
        }
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected AutocompletePredictionBufferResponse doInBackground(Void... params) {

            AutocompletePredictionBufferResponse autocompletePredictions;
            Task<AutocompletePredictionBufferResponse> results =
                    mGeoDataClient.getAutocompletePredictions(charSequence.toString(), BOUNDS_GREATER_SYDNEY,
                            null);


            // This method should have been called off the main UI thread. Block and wait for at most
            // 60s for a result from the API.
            try {
                Tasks.await(results, 60, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }


            try {
                autocompletePredictions = results.getResult();

                Log.e("MyTag", "Query completed. Received " + autocompletePredictions.getCount()
                        + " predictions.");

            } catch (RuntimeExecutionException e) {
                // If the query did not complete successfully return null

                Log.e("MyTag", "Error getting autocomplete prediction API call", e);
                return null;
            }

            return autocompletePredictions;
        }

        @Override
        protected void onPostExecute(AutocompletePredictionBufferResponse result) {

            primaryTextList1.clear();
            secondaryTextList1.clear();
            predictionList1.clear();

            if (result.getCount()==0){

                autoAdapter1.notifyDataSetChanged();
            }else {
                for (int i=0;i<result.getCount();i++){
                    AutocompletePrediction autocompletePrediction = result.get(i);
                    Log.e("textresult", String.valueOf(autocompletePrediction.getPrimaryText(STYLE_BOLD)));

                    String ptext = String.valueOf(autocompletePrediction.getPrimaryText(STYLE_BOLD));

                    String sectext = String.valueOf(autocompletePrediction.getSecondaryText(STYLE_BOLD));


                    primaryTextList1.add(ptext);
                    secondaryTextList1.add(sectext);
                    predictionList1.add(autocompletePrediction);

                    autoAdapter1.notifyDataSetChanged();

                }
            }

            if (!primaryTextList1.isEmpty()){
                card1.setVisibility(View.VISIBLE);
            }else {
                card1.setVisibility(View.GONE);
            }
        }

    }


    public class AsyncCallWS2 extends AsyncTask<Void, Void, AutocompletePredictionBufferResponse>
    {
        private  final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
        CharSequence charSequence;
        private  final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
                new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

        AsyncCallWS2(CharSequence charSequence){

            this.charSequence = charSequence;
        }
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected AutocompletePredictionBufferResponse doInBackground(Void... params) {

            AutocompletePredictionBufferResponse autocompletePredictions;
            Task<AutocompletePredictionBufferResponse> results =
                    mGeoDataClient.getAutocompletePredictions(charSequence.toString(), BOUNDS_GREATER_SYDNEY,
                            null);


            // This method should have been called off the main UI thread. Block and wait for at most
            // 60s for a result from the API.
            try {
                Tasks.await(results, 60, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }


            try {
                autocompletePredictions = results.getResult();

                Log.e("MyTag", "Query completed. Received " + autocompletePredictions.getCount()
                        + " predictions.");

            } catch (RuntimeExecutionException e) {
                // If the query did not complete successfully return null

                Log.e("MyTag", "Error getting autocomplete prediction API call", e);
                return null;
            }

            return autocompletePredictions;
        }

        @Override
        protected void onPostExecute(AutocompletePredictionBufferResponse result) {

            primaryTextList2.clear();
            secondaryTextList2.clear();
            predictionList2.clear();

            if (result.getCount()==0){

                autoAdapter2.notifyDataSetChanged();
            }else {
                for (int i=0;i<result.getCount();i++){
                    AutocompletePrediction autocompletePrediction = result.get(i);
                    Log.e("textresult", String.valueOf(autocompletePrediction.getPrimaryText(STYLE_BOLD)));

                    String ptext = String.valueOf(autocompletePrediction.getPrimaryText(STYLE_BOLD));

                    String sectext = String.valueOf(autocompletePrediction.getSecondaryText(STYLE_BOLD));


                    primaryTextList2.add(ptext);
                    secondaryTextList2.add(sectext);
                    predictionList2.add(autocompletePrediction);

                    autoAdapter2.notifyDataSetChanged();

                }
            }

            if (!primaryTextList2.isEmpty()){
                card2.setVisibility(View.VISIBLE);
            }else {
                card2.setVisibility(View.GONE);
            }
        }

    }


    public void deliverybooking(View view)
    {
        Intent intent = new Intent(this, DeliveryMapsActivity.class);
        String message = pickup.getText().toString();
        String message1 = drop.getText().toString();
        String message2= content.getText().toString();
        String message3=val.getText().toString();
        String message4=instruction.getText().toString();

        if (!message.isEmpty()){

            sessionManager.setPreferences("lat1",lat1);
            sessionManager.setPreferences("lng1",lng1);

            sessionManager.setPreferences("Pickup Location",message);

        }else {
            sessionManager.setPreferences("Pickup Location",pickup.getHint().toString().substring(3));

            sessionManager.setPreferences("lat1","");
            sessionManager.setPreferences("lng1","");
        }

        if (!message1.isEmpty()){

            sessionManager.setPreferences("Drop Location",message1);

            sessionManager.setPreferences("lat2",lat2);
            sessionManager.setPreferences("lng2",lng2);
        }else {
            sessionManager.setPreferences("Drop Location",drop.getHint().toString().substring(3));

            sessionManager.setPreferences("lat2","");
            sessionManager.setPreferences("lng2","");
        }


        if (!message2.isEmpty()){
            sessionManager.setPreferences("Content",message2);
        }else {
            sessionManager.setPreferences("Content",content.getHint().toString().substring(3));
        }


        if (!message3.isEmpty()){
            sessionManager.setPreferences("Estimated value of content",message3);
        }else {
            sessionManager.setPreferences("Estimated value of content",val.getHint().toString().substring(3));
        }

        if (!message4.isEmpty()){
            sessionManager.setPreferences("Instruction",message4);
        }else {
            sessionManager.setPreferences("Instruction",instruction.getHint().toString().substring(3));
        }


        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_MESSAGE,message1);
        intent.putExtra("message", String.valueOf(pickup));
        intent.putExtra("message1", String.valueOf(drop));
        intent.putExtra("message2", String.valueOf(content));
        intent.putExtra("message3", String.valueOf(val));
        intent.putExtra("message4", String.valueOf(instruction));
        startActivityForResult(intent, TEXT_REQUEST);
    }
}
