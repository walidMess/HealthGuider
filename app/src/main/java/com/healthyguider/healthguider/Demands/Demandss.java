package com.healthyguider.healthguider.Demands;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.healthyguider.healthguider.ApiConfig.FetchData;
import com.healthyguider.healthguider.ApiConfig.Hospital;
import com.healthyguider.healthguider.R;

public class Demandss extends AppCompatActivity {

    EditText hopitalnom,hopitalAdresse,HeurO,hopitalgps,hopitalnum;
    Button adddemandhospital;
    public static TextView wronginfos;
    Hospital H;

    FetchData fetchData = new FetchData(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addhospital);

        // init

        hopitalAdresse=(EditText)findViewById(R.id.hopitalAdresse);
        hopitalnom=(EditText)findViewById(R.id.hopitalnom);
        HeurO=(EditText)findViewById(R.id.HeurO);
        hopitalgps=(EditText)findViewById(R.id.hopitalgps);
        hopitalnum=(EditText)findViewById(R.id.hopitalnum);
        wronginfos=(TextView)findViewById(R.id.wronginfos);


        adddemandhospital =(Button)findViewById(R.id.adddemandhospital);

        adddemandhospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hopitalAdresse.getText().toString().equals("")
                        ||hopitalnom.getText().toString().equals("")
                        ||hopitalnum.getText().toString().equals("")
                        ||hopitalgps.getText().toString().equals("")
                        ||HeurO.getText().toString().equals(""))
                {

                    wronginfos.setText("please fill all the text fields !");
                    wronginfos.setVisibility(View.VISIBLE);
                }else {

                    H=new Hospital();
                    H.setNomh(hopitalnom.getText().toString());
                    H.setHeurO(HeurO.getText().toString());
                    H.setAdresse(hopitalAdresse.getText().toString());
                    H.setGPS(hopitalgps.getText().toString());
                   H.setMobile(hopitalnum.getText().toString());

                   fetchData.addDemands(H);


                }

            }
        });






    }
}
