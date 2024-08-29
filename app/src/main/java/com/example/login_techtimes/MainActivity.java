package com.example.login_techtimes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_techtimes.Adapter.RecyclerViewAdapter;
import com.example.login_techtimes.DataBase.MyDataBaseHelper;
import com.example.login_techtimes.Model.workModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    TextView txtNoData ;
    FloatingActionButton floatingButton ;
    ArrayList<workModel> arrayWork = new ArrayList<>();
    DrawerLayout drawerLayout ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.homeRecyclerView);
        txtNoData =findViewById(R.id.txtNoData);
        floatingButton =findViewById(R.id.floatingButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        drawerLayout =findViewById(R.id.main);

//        this  is  for  open the  toolbar
        MaterialToolbar toolbar = findViewById(R.id.appBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        NavigationView navigationView =findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.menuHome) {

                    Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.menuLogout) {
                    SharedPreferences sharedPreferences = getSharedPreferences("login" ,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("flag" ,false);

                    Intent intent = new Intent(MainActivity.this , Loginactivity.class);
                    startActivity(intent);
                    finish();



                    Toast.makeText(MainActivity.this, "Logout success ", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });





        MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(this);

        arrayWork = myDataBaseHelper.selectTask();


        if(arrayWork.size() ==0){
               txtNoData.setText("no data found  create new ");
        }else {
            txtNoData.setVisibility(View.GONE);
        }


        RecyclerViewAdapter adapter =  new RecyclerViewAdapter(this ,arrayWork);
        recyclerView.setAdapter(adapter);





        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_details_card);
                dialog.show();

                EditText addTitle =dialog.findViewById(R.id.edTitle);
                EditText addDescription = dialog.findViewById(R.id.edDescription);
                Button addDate =dialog.findViewById(R.id.edDate);
                Button addButton =dialog.findViewById(R.id.edButton);

                final String[] selectedDate = {""};


                addDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int year, month, day;
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                addDate.setText(i2 + "/" + (i1 + 1) + "/" + i);

                                selectedDate[0] = i2 + "/" + (i1 + 1) + "/" + i;


                            }
                        }, year, month, day);
                        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                        datePickerDialog.show();
                    }
                });





                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String title = addTitle.getText().toString();
                        String description = addDescription.getText().toString();
                        String date =selectedDate[0];

                        if(!title.isEmpty() && !description.isEmpty() && !date.isEmpty()){

                            myDataBaseHelper.insertTask(title,description,date);

                            arrayWork.clear(); // Clear the current list
                            arrayWork.addAll(myDataBaseHelper.selectTask()); // Reload from DB
                            adapter.notifyItemInserted(arrayWork.size() - 1);

                            txtNoData.setVisibility(View.GONE);

                            dialog.dismiss();



                        }

                    }
                });







            }
        });





    }


}