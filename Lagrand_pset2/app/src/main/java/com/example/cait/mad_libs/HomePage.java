package com.example.cait.mad_libs;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class HomePage extends AppCompatActivity {

    private Spinner dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Set textfiles from assets as dropdown list
        dropdown = (Spinner)findViewById(R.id.spinnerFile);
        AssetManager assetManager = this.getAssets();
        ArrayList<String> files = new ArrayList<String>();
        try {
            for (String file : assetManager.list("")) {
                if (file.endsWith(".txt"))
                    files.add(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, files);
        dropdown.setAdapter(adapter);
    }

    public void startGame(View view) {
        Intent goToFillIn = new Intent(this, FillInActivity.class);
        goToFillIn.putExtra("File", dropdown.getSelectedItem().toString());
        startActivity(goToFillIn);
//        finish();  decide if want to have this
    }
}
