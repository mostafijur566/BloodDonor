package com.example.blooddonor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Donor> {

    private Activity context;
    private List<Donor> donorList;

    public CustomAdapter (Activity context, List<Donor> donorList) {
        super(context, R.layout.sample_view, donorList);
        this.context = context;
        this.donorList = donorList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sample_view, null, true);

        Donor donor = donorList.get(position);

        TextView sampleName = view.findViewById(R.id.sampleName);
        TextView sampleNumber = view.findViewById(R.id.sampleNumber);
        TextView sampleAddress = view.findViewById(R.id.sampleAddress);
        TextView sampleBlood = view.findViewById(R.id.sampleBlood);

        sampleName.setText(donor.getName());
        sampleNumber.setText("Phone Number: "+donor.getNumber());
        sampleAddress.setText("Address: "+donor.getAddress());
        sampleBlood.setText("Blood Group: "+donor.getBlood());

        return view;
    }
}
