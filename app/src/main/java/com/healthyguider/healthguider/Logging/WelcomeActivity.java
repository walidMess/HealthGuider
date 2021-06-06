package com.healthyguider.healthguider.Logging;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthyguider.healthguider.ApiConfig.FetchData;
import com.healthyguider.healthguider.R;

public class WelcomeActivity extends AppCompatActivity {
    LinearLayout l1,l2;
    Button btnsub;
    public static TextView userNumber;
    Animation uptodown,downtoup;
    FetchData fetchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnsub = (Button)findViewById(R.id.buttonsub);
        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

        fetchData= new FetchData(this);
        userNumber= (TextView)findViewById(R.id.userNumber);

        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this.getApplicationContext(),LogingActivity.class);
                startActivity(intent);
            }
        });

        fetchData.getUsers();

    }
}
