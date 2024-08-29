package com.example.login_techtimes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login_techtimes.DataBase.MyDataBaseHelper;
import com.example.login_techtimes.Model.workModel;

import java.util.Calendar;

public class TaskDetails extends AppCompatActivity {

    TextView titleDetails ,descriptionDetails , selectDate , leftDay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_details);



        findId();
        // Retrieve the data passed from the RecyclerView item
        int id = getIntent().getIntExtra("id",0);
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("date");
        String leftDays = getIntent().getStringExtra("leftDays");

        titleDetails.setText(title);
        descriptionDetails.setText(description);
        selectDate.setText(date);
        if (leftDays.isEmpty()){
            leftDay.setText("Date Passed");
        }else {
            leftDay.setText(leftDays +" day left");
        }

        // on the  update  button click  popup dialog open  for  update

//        updateTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dialog dialog = new Dialog(TaskDetails.this);
//                dialog.setContentView(R.layout.add_details_card);
//                dialog.show();
//
//                EditText titleInput = dialog.findViewById(R.id.edTitle);
//                EditText descriptionInput = dialog.findViewById(R.id.edDescription);
//                Button dateInput = dialog.findViewById(R.id.edDate);
//                Button updateButton = dialog.findViewById(R.id.edButton);
//                TextView taskTitle = dialog.findViewById(R.id.taskTitle);
//
//                taskTitle.setText("update Task");
//                titleInput.setText(title);
//                descriptionInput.setText(description);
//                dateInput.setText(date);
//                updateButton.setText("update");
//
//
//
//                final String[] selectedDate = {""};
//
//
//                dateInput.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int year ,month ,day ;
//                        Calendar calendar = Calendar.getInstance();
//                        year = calendar.get(Calendar.YEAR);
//                        month =calendar.get(Calendar.MONTH);
//                        day =calendar.get(Calendar.DAY_OF_MONTH);
//
//
//                        DatePickerDialog datePickerDialog = new DatePickerDialog(TaskDetails.this, new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                                dateInput.setText(i2+"/"+(i1+1)+"/"+i);
//
//                                selectedDate[0] = i2 + "/" + (i1 + 1) + "/" + i;
//
//
//                            }
//                        },year ,month,day);
//                        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
//                        datePickerDialog.show();
//                    }
//                });
//
//
//
//
//
//
//
////                updateButton.setOnClickListener(new View.OnClickListener() {
////                  @Override
////                  public void onClick(View view) {
////                         String titleUD = titleInput.getText().toString();
////                         String descriptionUD = descriptionInput.getText().toString();
////                         String dateUD = dateInput.getText().toString();
////
////
////                      if(!titleUD.isEmpty() && !descriptionUD.isEmpty() && !dateUD.isEmpty()){
////                          workModel model = new workModel(id,titleUD,descriptionUD,dateUD);
////
////                          dataBaseHelper.updateTask(model);
////                          dialog.dismiss();
////
////
////                          titleDetails.setText(titleUD);
////                          descriptionDetails.setText(descriptionUD);
////                          selectDate.setText(dateUD);
////                          // Calculate and set the days left again
////                          // Assuming you have a method to calculate this:
////                          leftDay.setText(0 +" days left");
////
////
////
////
////
////
////                      }
////                  }
////              });
//
//
//
//
//
//
//
//
//
//            }
//        });











    }


    private void findId() {
        titleDetails = findViewById(R.id.titleDetail);
        descriptionDetails = findViewById(R.id.descriptionDetail);
        selectDate = findViewById(R.id.selectDate);
        leftDay = findViewById(R.id.leftDay);
//        updateTask =findViewById(R.id.updateTask);
    }


}