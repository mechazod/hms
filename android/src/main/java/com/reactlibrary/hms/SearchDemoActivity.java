/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.libraries.places.api.Places;
import com.huawei.hms.libraries.places.api.model.Place;
import com.huawei.hms.libraries.places.api.model.PlaceLikelihood;
import com.huawei.hms.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.huawei.hms.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.huawei.hms.libraries.places.api.net.FindCurrentPlaceRequest;
import com.huawei.hms.libraries.places.api.net.FindCurrentPlaceResponse;
import com.huawei.hms.libraries.places.api.net.PlacesClient;
import com.reactlibrary.R;

import java.util.List;

public class SearchDemoActivity extends AppCompatActivity {

    private static final String TAG = "SearchDemoActivity";

    private PlacesClient placesClient;

    private EditText query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_demo);
        placesClient = Places.createClient(this);
        query = (EditText)findViewById(R.id.queryDetail);
    }

    public void findCurrentPlace(View view) {
        if(null == query.getText()){
            return ;
        }
        if(null == placesClient){
            return;
        }
        FindCurrentPlaceRequest.Builder builder = FindCurrentPlaceRequest.builder(null);
        builder.setQuery(query.getText().toString());
        builder.setLimit(10);
        builder.setOffet(0);
        Log.d(TAG, "addMarker: start");
        if (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "need ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT);
            return;
        }
        Task<FindCurrentPlaceResponse> currentPlaceTask = placesClient.findCurrentPlace(builder.build());
        currentPlaceTask.addOnSuccessListener(new OnSuccessListener<FindCurrentPlaceResponse>() {

            @Override
            public void onSuccess(FindCurrentPlaceResponse findCurrentPlaceResponse) {
                initListByLikeHood(findCurrentPlaceResponse.getPlaceLikelihoods());
            }
        });

        currentPlaceTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "onSuccess() called with: error = [" + e.getMessage() + "]");

            }
        });
    }

    public void searchByinput(View view) {
        if(null == placesClient){
            return;
        }
        com.huawei.hms.libraries.places.api.net.FindAutocompletePredictionsRequest.Builder builder =
            FindAutocompletePredictionsRequest.builder();
        builder.setLimit(10);
        builder.setOffet(0);
        builder.setQuery(query.getText().toString());
        Log.d(TAG, "addMarker: start");
        Task<FindAutocompletePredictionsResponse> currentPlaceTask =
            placesClient.findAutocompletePredictions(builder.build());
        currentPlaceTask.addOnSuccessListener(new OnSuccessListener<FindAutocompletePredictionsResponse>() {

            @Override
            public void onSuccess(FindAutocompletePredictionsResponse findAutocompletePredictionsResponse) {
                initList(findAutocompletePredictionsResponse.getPlaces());
            }
        });

        currentPlaceTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "onSuccess() called with: error = [" + e.getMessage() + "]");
            }
        });
    }

    private void initListByLikeHood(List<PlaceLikelihood> places) {
        String[] data = new String[places.size()];
        for (int i = 0; i < places.size(); i++) {
            data[i] = i + ":" + places.get(i).getPlace().getName() + "," + places.get(i).getPlace().getAddress();
        }
        ArrayAdapter<String> adapter =
            new ArrayAdapter<String>(SearchDemoActivity.this, android.R.layout.simple_list_item_1, data);
        ListView list = (ListView) findViewById(R.id.listView1);
        list.setAdapter(adapter);
    }

    private void initList(List<Place> places) {
        String[] data = new String[places.size()];
        for (int i = 0; i < places.size(); i++) {
            data[i] = i + ":" + places.get(i).getName() + "," + places.get(i).getAddress();
        }
        ArrayAdapter<String> adapter =
            new ArrayAdapter<String>(SearchDemoActivity.this, android.R.layout.simple_list_item_1, data);
        ListView list = (ListView) findViewById(R.id.listView1);
        list.setAdapter(adapter);
    }
}
