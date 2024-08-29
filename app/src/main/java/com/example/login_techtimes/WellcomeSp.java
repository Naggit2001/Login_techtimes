package com.example.login_techtimes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class WellcomeSp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wellcome_sp);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                Boolean is_present = sharedPreferences.getBoolean("flag",false);

                if(is_present){
                    Intent intent = new Intent(WellcomeSp.this , MainActivity.class);
                    startActivity(intent);
                    finish();


                  }else {
                    Intent intent = new Intent(WellcomeSp.this , Loginactivity.class);
                    startActivity(intent);
                    finish();

                 }


            }
        }, 4000);
        

    }
}