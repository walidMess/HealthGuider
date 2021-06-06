package com.healthyguider.healthguider.Register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.healthyguider.healthguider.ApiConfig.FetchData;
import com.healthyguider.healthguider.R;

public class registerActivity extends AppCompatActivity {

    EditText username,password,email;
     Button signup;
     public static TextView wronginfos;
     FetchData fetchData= new FetchData(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //init Edit texts
        username = (EditText) findViewById(R.id.UsernameRegister);
        password = (EditText) findViewById(R.id.PasswordRegister);
        email = (EditText) findViewById(R.id.emailregister);
        wronginfos = (TextView) findViewById(R.id.wronginfos);


        signup= (Button)findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().equals("") || password.getText().toString().equals("") ){

                    //Toast.makeText(LogingActivity.this,"Please inter a username and a password",Toast.LENGTH_SHORT).show();

                    wronginfos.setText(" please inter a user name and a password ! ");
                    wronginfos.setVisibility(View.VISIBLE);

                }else {


                    fetchData.Register( username.getText().toString()
                            , password.getText().toString()
                            , email.getText().toString()
                            , "user");




                }            }
        });

    }
}
