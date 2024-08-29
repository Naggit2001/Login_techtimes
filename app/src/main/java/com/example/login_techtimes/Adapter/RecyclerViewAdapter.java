package com.example.login_techtimes.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_techtimes.Model.workModel;
import com.example.login_techtimes.DataBase.MyDataBaseHelper;
import com.example.login_techtimes.R;
import com.example.login_techtimes.TaskDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

         Context context ;
         ArrayList <workModel> arrayWork;
         MyDataBaseHelper dataBaseHelper ;

    public RecyclerViewAdapter(Context context, ArrayList<workModel> arrayWork) {
        this.context = context;
        this.arrayWork = arrayWork;
        this.dataBaseHelper = new MyDataBaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.detail_show_card,parent ,false);
            ViewHolder viewHolder = new ViewHolder(view);

         return viewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        workModel model = arrayWork.get(position);
        holder.showTitle.setText(arrayWork.get(position).title);
        holder.showDate.setText(arrayWork.get(position).date);

//        cal curate the  left day
        String dateStr = arrayWork.get(position).date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String LeftDays = "";
        try {
            Date targetDate = sdf.parse(dateStr);
            if (targetDate != null) {
                Calendar targetCalendar = Calendar.getInstance();
                targetCalendar.setTime(targetDate);

                Calendar todayCalendar = Calendar.getInstance();

                long diffInMillis = targetCalendar.getTimeInMillis() - todayCalendar.getTimeInMillis();
                long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);

                if (diffInDays >= 0) {
                    holder.showLeftDate.setText(diffInDays +"day left ");
                    LeftDays = String.valueOf(diffInDays);
                } else {
                    holder.showLeftDate.setText("Date Passed");
                }
            }
        } catch (ParseException e) {

        }

        String finalLeftDays = LeftDays;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskDetails.class);
                intent.putExtra("id",model.id);
                intent.putExtra("title", model.title);
                intent.putExtra("description", model.description);
                intent.putExtra("date", model.date);
                intent.putExtra("leftDays", finalLeftDays);
                context.startActivity(intent);
            }
        });

//           it is  for  the  update
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                   showUpdateDialog(model ,position);


                return true;
            }
        });

        //  open the delete  button
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog(position, model.id);
                Toast.makeText(context ," delete the item " ,Toast.LENGTH_LONG).show();
            }
        });




    }

    private void showUpdateDialog(workModel model , int position) {


        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_details_card);
        dialog.show();

        EditText titleInput = dialog.findViewById(R.id.edTitle);
        EditText descriptionInput = dialog.findViewById(R.id.edDescription);
        Button dateInput = dialog.findViewById(R.id.edDate);
        Button updateButton = dialog.findViewById(R.id.edButton);
        TextView taskTitle = dialog.findViewById(R.id.taskTitle);

        taskTitle.setText("update Task");
        titleInput.setText(model.title);
        descriptionInput.setText(model.description);
        dateInput.setText(model.date);
        updateButton.setText("update");

        final String[] selectedDate = {model.date};

        dateInput.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, (datePicker, i, i1, i2) -> {
                selectedDate[0] = i2 + "/" + (i1 + 1) + "/" + i;
                dateInput.setText(selectedDate[0]);
            }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleUD = titleInput.getText().toString();
                String descriptionUD = descriptionInput.getText().toString();
                String dateUD = dateInput.getText().toString();


                if(!titleUD.isEmpty() && !descriptionUD.isEmpty() && !dateUD.isEmpty()){
                    workModel models = new workModel(model.id,titleUD,descriptionUD,dateUD);

                    dataBaseHelper.updateTask(models);
                    arrayWork.set(position,models);
                    notifyItemChanged(position);
                    dialog.dismiss();
                }
            }
        });



    }

    private void showDeleteDialog(int position, int itemId) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Delete");
        alertDialog.setIcon(R.drawable.baseline_delete_24);
        alertDialog.setMessage("Do you want to delete this task?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Delete the item from the database
                boolean deleted = dataBaseHelper.deleteTask(itemId);
                if (deleted) {
                    removeItem(position);
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed to delete item", Toast.LENGTH_LONG).show();
                }
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    public void removeItem(int position) {
        arrayWork.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayWork.size());
    }



    @Override
    public int getItemCount() {
        return arrayWork.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView showTitle ,showDescription , showDate ,showLeftDate;
        Button buttonDelete ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showTitle=itemView.findViewById(R.id.showTitle);
            buttonDelete =  itemView.findViewById(R.id.buttonDelete);
            showDate =itemView.findViewById(R.id.showDate);
            showLeftDate =itemView.findViewById(R.id.showLeftDate);



        }
    }
}

