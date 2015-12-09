package com.appracks.easy_wallet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appracks.easy_wallet.data_object.StatementData;
import com.appracks.easy_wallet.dateOperation.DateOperation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by HABIB on 12/9/2015.
 */
public class DB_Manager extends SQLiteOpenHelper {
    private static DB_Manager managerInstance;
    private static final String DB_NAME = "easy_wallet";
    private static String DB_PATH;
    private SQLiteDatabase database;
    private Context context;

    private static final String INCOME_TABLE = "incomestatement";
    private static final String EXPENSE_TABLE = "expensestatement";
    private static final String ID_FIELD = "id";
    private static final String DATE_FIELD = "date";
    private static final String SOURCE_WAY_FIELD = "source_way";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String AMOUNT_FIELD = "amount";
    private static final String DAY_FIELD = "day";
    private static final String MONTH_FIELD = "month";
    private static final String YEAR_FIELD = "year";
    private static final String DATEORDER_FIELD = "dateorder";

    DateOperation dt;
    public boolean addStatement(StatementData sd){
        dt=new DateOperation();
        ContentValues values=new ContentValues();
        values.put(DATE_FIELD, sd.getDate());
        values.put(SOURCE_WAY_FIELD,sd.getSourceWay());
        values.put(DESCRIPTION_FIELD,sd.getDescription());
        values.put(AMOUNT_FIELD,sd.getAmount());
        values.put(DAY_FIELD,dt.getDay(sd.getDate()));
        values.put(MONTH_FIELD,dt.getMonth(sd.getDate()));
        values.put(YEAR_FIELD,dt.getYear(sd.getDate()));
        values.put(DATEORDER_FIELD, dt.getDateOrder(sd.getDate()));
        if(sd.getType().equalsIgnoreCase("in")){
            this.database.insert(INCOME_TABLE,null,values);
        }else{
            this.database.insert(EXPENSE_TABLE,null,values);
        }

        return true;
    }

    private DB_Manager(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.database = openDatabase();
    }

    public static DB_Manager getInstance(Context context) {
        if (managerInstance == null) {
            managerInstance = new DB_Manager(context);
        }
        return managerInstance;
    }

    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    public SQLiteDatabase openDatabase() {
        String path = DB_PATH + DB_NAME;
        if (database == null) {
            createDatabase();
            database = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    private void createDatabase() {
        String path = DB_PATH + DB_NAME;
        File file = new File(path);
        if (file.exists()) {
        } else {
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private void copyDatabase() {
        try {
            InputStream dbInputStream = context.getAssets().open(DB_NAME);
            String path = DB_PATH + DB_NAME;
            OutputStream dbOutputStream = new FileOutputStream(path);
            byte[] buffer = new byte[4096];
            int readCount = 0;
            while ((readCount = dbInputStream.read(buffer)) > 0) {
                dbOutputStream.write(buffer, 0, readCount);
            }
            dbInputStream.close();
            dbOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
