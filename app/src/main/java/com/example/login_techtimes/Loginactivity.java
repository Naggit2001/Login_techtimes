package com.example.login_techtimes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Loginactivity extends AppCompatActivity {

    EditText editTextUserName , editTextPassWord ;
    Button buttonLogin ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        editTextUserName =findViewById(R.id.editTextUserName);
        editTextPassWord =findViewById(R.id.editTextPassword);
        buttonLogin =findViewById(R.id.buttonLogin);



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUserName.getText().toString();
                String  password = editTextPassWord.getText().toString();

                if(username.equals("user") && password.equals("123")){

                    Toast.makeText(Loginactivity.this ,"login success" ,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Loginactivity.this ,MainActivity.class);
                    startActivity(intent);
                    finish();

//                if the  user are  login first time
                    SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("flag", true);
                    editor.apply();



                }else {
                    Toast.makeText(Loginactivity.this ,"username & password  not  match ",Toast.LENGTH_LONG).show();
                }


                

            }
        });






    }
}