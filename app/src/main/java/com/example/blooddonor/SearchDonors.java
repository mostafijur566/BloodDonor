package com.example.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchDonors extends AppCompatActivity {

    private ListView searchList;
    private SearchView mSearchView;

    DatabaseReference databaseReference;

    private List<Donor> donorList;
    private CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_donors);

        databaseReference = FirebaseDatabase.getInstance().getReference("Donors");

        donorList = new ArrayList<>();

        customAdapter = new CustomAdapter(SearchDonors.this, donorList);
        mSearchView=(SearchView) findViewById(R.id.search);

        searchList = (ListView)findViewById(R.id.searchList);


    }


    @Override
    protected void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                donorList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Donor donor = dataSnapshot.getValue(Donor.class);
                    donorList.add(donor);
                }

                searchList.setAdapter(customAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        super.onStart();
    }



}