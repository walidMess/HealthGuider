package com.healthyguider.healthguider.Logging;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.healthyguider.healthguider.ApiConfig.FetchData;
import com.healthyguider.healthguider.MapsActivity;
import com.healthyguider.healthguider.R;
import com.healthyguider.healthguider.Register.registerActivity;

public class LogingActivity extends AppCompatActivity {

    Button Login,createaccount;
    EditText Username,Password;
    static Context ctx;
    FetchData fetchData = new FetchData(this);
    public static TextView wronginfos;
    UserLocalStorage userLocalStorage;

    static user returenedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx=LogingActivity.this.getApplicationContext();

        // set preffrences to user
        userLocalStorage=new UserLocalStorage(this);


        //init Edit texts
        wronginfos = (TextView) findViewById(R.id.wronginfos);
        Login = (Button)findViewById(R.id.LoginButton);
        Username = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.Password);
        createaccount = (Button) findViewById(R.id.createaccount);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Username.getText().toString().equals("") || Password.getText().toString().equals("") ){

                    //Toast.makeText(LogingActivity.this,"Please inter a username and a password",Toast.LENGTH_SHORT).show();

                    wronginfos.setText(" please inter a user name and a password ! ");
                    wronginfos.setVisibility(View.VISIBLE);

                }else {

                    fetchData.Login(Username.getText().toString(),Password.getText().toString());



                    }

            }
        });

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LogingActivity.this.getApplicationContext(),registerActivity.class);
                startActivity(signUpIntent);

            }
        });
    }

    public  static void setUser (user USER){

        returenedUser = USER;

        }




}
