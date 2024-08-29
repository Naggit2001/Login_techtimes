package com.example.login_techtimes.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.login_techtimes.Model.workModel;

import java.util.ArrayList;

public class MyDataBaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "user_task";
    private static final String TASK_TABLE ="task";
    private static final String TASK_ID  = "id";
    private static final String TASK_TITLE ="title";
    private static final String TASK_DESCRIPTION ="description";
    private static final String TASK_DATE ="date";



    public MyDataBaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TASK_TABLE +" ( " +TASK_ID +" INTEGER  PRIMARY KEY AUTOINCREMENT ,"+ TASK_TITLE +" TEXT ,"
                           +  TASK_DESCRIPTION +" TEXT ," + TASK_DATE+ " TEXT )" );



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }

    public void insertTask(String title ,String description ,String date){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_TITLE,title);
        values.put(TASK_DESCRIPTION,description);
        values.put(TASK_DATE,date);

        database.insert(TASK_TABLE,null,values);
        database.close();


    }

    public ArrayList<workModel> selectTask(){
          SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(" SELECT * FROM "+ TASK_TABLE ,null);

        ArrayList<workModel> arrayList = new ArrayList<>();

        while (cursor.moveToNext()){

               int   id = cursor.getInt(0);
               String title =cursor.getString(1);
               String description =cursor.getString(2);
               String date =cursor.getString(3);
               workModel model = new workModel(id,title,description ,date);

               arrayList.add(model);


        }
        cursor.close();
        database.close();
        return arrayList;


    }

   public  void  updateTask(workModel model){
         SQLiteDatabase database = this.getWritableDatabase();
         ContentValues values = new ContentValues();
         values.put(TASK_TITLE,model.title);
         values.put(TASK_DESCRIPTION,model.description);
         values.put(TASK_DATE,model.date);

         database.update(TASK_TABLE,values ,TASK_ID +" = "+ model.id ,null);
         database.close();

   }


    public boolean deleteTask(int id){

        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(TASK_TABLE ,TASK_ID +" = ?",new String[]{String.valueOf(id)});


        return true;
    }



}
