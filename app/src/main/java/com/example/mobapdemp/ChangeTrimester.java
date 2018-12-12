package com.example.mobapdemp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mobapdemp.Database.Database;

import java.util.ArrayList;

public class ChangeTrimester extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private ArrayList<String> terms = new ArrayList<>();
    private static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_trimester);

        db = Database.getInstance(this);

        Cursor cursor = db.queryEntity("SELECT DISTINCT term FROM courses");

        while(cursor.moveToNext()){
            terms.add(cursor.getString(0));
        }

        spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChangeTrimester.this,
                android.R.layout.simple_spinner_item,terms);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}



