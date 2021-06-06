package com.healthyguider.healthguider.Charts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.healthyguider.healthguider.ApiConfig.FetchData;
import com.healthyguider.healthguider.MapsActivity;
import com.healthyguider.healthguider.R;

import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity {




 Button b1,b2,b3,b4,b5,b6;
 ImageView displayCharts ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diapo);

// setting image view

        displayCharts = (ImageView )findViewById(R.id.displayCharts);
        //init Buttons

        b1= (Button) findViewById(R.id.button);
        b2= (Button) findViewById(R.id.button2);
        b3= (Button) findViewById(R.id.button3);
        b4= (Button) findViewById(R.id.button4);
        b5= (Button) findViewById(R.id.button5);
        b6= (Button) findViewById(R.id.button6);

        // set onclick for buttons


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCharts.setImageResource(R.drawable.cmnspslt_im1);


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCharts.setImageResource(R.drawable.cmnspslt_im2);


            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCharts.setImageResource(R.drawable.spslcmn_im1);


            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCharts.setImageResource(R.drawable.spslcmn_im2);


            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCharts.setImageResource(R.drawable.listplusgrandhopitaux);


            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCharts.setImageResource(R.drawable.hopitaux_piramide);


            }
        });

    }



}
