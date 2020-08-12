package com.example.vineet.easybuy;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.vineet.easybuy.Model.product_modal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Products extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static TextView total_amt;
    public static List<Drawable> imagelist1 = new ArrayList<>();
    public static List<Drawable> imagelist2 = new ArrayList<>();
    public static List<Drawable> imagelist3 = new ArrayList<>();
    public static ArrayList<Integer> totalamount = new ArrayList<Integer>();

    public static ArrayList<product_modal> finalList = new ArrayList<>();
    product_modal modal;
    private DatabaseHandler db;
    List<String> namelist1 = new ArrayList<>();
    List<String> pricelist1 = new ArrayList<>();


    List<String> namelist2 = new ArrayList<>();
    List<String> pricelist2 = new ArrayList<>();

    List<String> namelist3 = new ArrayList<>();
    List<String> pricelist3 = new ArrayList<>();

    Spinner sp_select_product;
    private Boolean mAllowSelectionFiring = false;
    String[] product = { "Laptop", "Mobile", "TV"};
    RecyclerView recyclerView1,recyclerView2,recyclerView3;
    LinearLayout layout1,layout2,layout3,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        totalamount.clear();
        db = new DatabaseHandler(getApplicationContext());
        db.clearDatabase();


        total_amt = findViewById(R.id.total_amt);
        recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView3 = findViewById(R.id.recyclerView3);
        sp_select_product = findViewById(R.id.sp_select_product);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        next = findViewById(R.id.next);

        sp_select_product.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,product);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp_select_product.setAdapter(aa);




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Products.this,CartActivity.class));
            }
        });


        namelist1.add("Laptop 1");
        namelist1.add("Laptop 2");
        namelist1.add("Laptop 3");
        namelist1.add("Laptop 4");
        namelist1.add("Laptop 5");



        namelist2.add("Mobile 1");
        namelist2.add("Mobile 2");
        namelist2.add("Mobile 3");
        namelist2.add("Mobile 4");
        namelist2.add("Mobile 5");

        namelist3.add("TV 1");
        namelist3.add("TV 2");
        namelist3.add("TV 3");
        namelist3.add("TV 4");
        namelist3.add("TV 5");

        pricelist1.add("40000");
        pricelist1.add("35000");
        pricelist1.add("35500");
        pricelist1.add("42000");
        pricelist1.add("27000");

        pricelist2.add("20000");
        pricelist2.add("25000");
        pricelist2.add("15000");
        pricelist2.add("17000");
        pricelist2.add("23000");

        pricelist3.add("51000");
        pricelist3.add("45000");
        pricelist3.add("70000");
        pricelist3.add("34000");
        pricelist3.add("35000");

        imagelist1.add(getResources().getDrawable(R.drawable.laptop1));
        imagelist1.add(getResources().getDrawable(R.drawable.laptop2));
        imagelist1.add(getResources().getDrawable(R.drawable.laptop3));
        imagelist1.add(getResources().getDrawable(R.drawable.laptop4));
        imagelist1.add(getResources().getDrawable(R.drawable.laptop5));

        imagelist2.add(getResources().getDrawable(R.drawable.mobile1));
        imagelist2.add(getResources().getDrawable(R.drawable.mobile2));
        imagelist2.add(getResources().getDrawable(R.drawable.mobile3));
        imagelist2.add(getResources().getDrawable(R.drawable.mobile4));
        imagelist2.add(getResources().getDrawable(R.drawable.mobile5));

        imagelist3.add(getResources().getDrawable(R.drawable.tv1));
        imagelist3.add(getResources().getDrawable(R.drawable.tv2));
        imagelist3.add(getResources().getDrawable(R.drawable.tv3));
        imagelist3.add(getResources().getDrawable(R.drawable.tv4));
        imagelist3.add(getResources().getDrawable(R.drawable.tv5));

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(mLayoutManager1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        AdapterProducts1 adapterProducts1 = new AdapterProducts1(this,namelist1,pricelist1,imagelist1);
        recyclerView1.setAdapter(adapterProducts1);



        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        AdapterProducts2 adapterProducts2 = new AdapterProducts2(this,namelist2,pricelist2,imagelist2);
        recyclerView2.setAdapter(adapterProducts2);

        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(mLayoutManager3);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
        AdapterProducts3 adapterProducts3 = new AdapterProducts3(this,namelist3,pricelist3,imagelist3);
        recyclerView3.setAdapter(adapterProducts3);
        getList();


    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {


        if(product[position].equals("Laptop")){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);




        }
        else if(product[position].equals("Mobile")){
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.GONE);

        }
        else if(product[position].equals("TV")){
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.VISIBLE);

        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    public void getList() {

        AndroidNetworking.get("http://avenirtechs.com/testing/demo_data.php")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("getpostsdata", String.valueOf(response));
                        try {

                            JSONArray jsonArray = response.getJSONArray("data");
                            //Toast.makeText(CreativeThinkingListActivity.this, String.valueOf(jsonArray.length()), Toast.LENGTH_SHORT).show();
                            if (jsonArray!=null){
                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject object=jsonArray.getJSONObject(i);

                                    String name=object.getString("name");
                                    String price=object.getString("price");
                                    String count=object.getString("count");
                                    String fdf = db.addNewForm(name,price,count);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {

                        Log.d("response3", String.valueOf(error));
                        // handle error
                    }
                });
    }






}
