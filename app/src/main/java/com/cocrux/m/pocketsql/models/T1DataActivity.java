package com.cocrux.m.pocketsql.models;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cocrux.m.pocketsql.R;

import java.util.ArrayList;

public class T1DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t1_data);
        ArrayList<T1Model> data = new ArrayList<>();
        if (getIntent() != null)
            data = getIntent().getParcelableArrayListExtra("data");
        Log.d("msg", "total count " + data.size());
        ((TextView)findViewById(R.id.count)).setText(data.size() + " rows found!");
        ViewGroup parent = findViewById(R.id.container);
        parent.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        for(T1Model obj: data){
            View v = inflater.inflate(R.layout.row_list_t1, null, false);
            ((TextView)v.findViewById(R.id.name)).setText(obj.getName());
            ((TextView)v.findViewById(R.id.roll)).setText(obj.getRoll() + "");
            ((TextView)v.findViewById(R.id.agg)).setText(obj.getAggregate());
            parent.addView(v);
        }
    }
}
