package com.healthyguider.healthguider.Demands;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.healthyguider.healthguider.ApiConfig.FetchData;
import com.healthyguider.healthguider.ApiConfig.Hospital;
import com.healthyguider.healthguider.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewDemands extends AppCompatActivity {
    public static ArrayList<Hospital> HopitalDemandes = new ArrayList<Hospital>();
   public static RecyclerView viewDemandsRecycler;
   FetchData fetchData;
   public static TextView wronginfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);

        fetchData = new FetchData(this);

        HopitalDemandes = new ArrayList<Hospital>();
        viewDemandsRecycler=(RecyclerView)findViewById(R.id.viewDemandsRecycler);
        viewDemandsRecycler.setHasFixedSize(true);
        viewDemandsRecycler.setLayoutManager(new LinearLayoutManager(this));

        wronginfos=(TextView) findViewById(R.id.wronginfos);
        wronginfos.setVisibility(View.GONE);

        fetchData.viewDemands();







    }

}
