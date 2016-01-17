package com.appracks.easy_wallet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.appracks.easy_wallet.data_object.BarDatas;
import com.appracks.easy_wallet.data_object.StatementData;
import com.appracks.easy_wallet.dateOperation.DateOperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by HABIB on 12/9/2015.
 */
public class DB_Manager extends SQLiteOpenHelper {
    private static DB_Manager managerInstance;
    public static final String DB_NAME = "easy_wallet";
    public static String DB_PATH;
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


    public String getPassword(){
        Cursor cursor = this.database.query("setting", null,"value=?", new String[]{"999"}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String pass = cursor.getString(cursor.getColumnIndex("name"));
            cursor.close();
            return pass;
        }
        return null;
    }
    public String getSecurityQuestion(){
        Cursor cursor = this.database.query("setting", null,"value=?", new String[]{"888"}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String pass = cursor.getString(cursor.getColumnIndex("name"));
            cursor.close();
            return pass;
        }
        return null;
    }
    public String getCurrency(){
        Cursor cursor = this.database.query("setting", null,"name=?", new String[]{"currency"}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String currency = cursor.getString(cursor.getColumnIndex("value"));
            cursor.close();
            return currency;
        }
        return null;
    }

    public String getQuestionAnswer(){
        Cursor cursor = this.database.query("setting", null,"value=?", new String[]{"777"}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String pass = cursor.getString(cursor.getColumnIndex("name"));
            cursor.close();
            return pass;
        }
        return null;
    }
    public boolean getIsPasswordOn(){
        Cursor cursor = this.database.query("setting", null,"name=?", new String[]{"isPasswordOn"}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndex("value"));
            cursor.close();
            if(id==1){
                return true;
            }
        }
        return false;
    }
    public boolean getIsAutoBackupOn(){
        Cursor cursor = this.database.query("setting", null, "name=?", new String[]{"isAutoBackupOn"}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndex("value"));
            cursor.close();
            if(id==1){
                return true;
            }
        }
        return false;
    }

    public boolean setIsPasswordOn(int value){
        ContentValues values;
        values = new ContentValues();
        values.put("value", value);

        long inserted=this.database.update("setting", values, "name=?", new String[]{"isPasswordOn"});
        if(inserted>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean setIsAutoBackupOn(int value){
        ContentValues values;
        values = new ContentValues();
        values.put("value", value);

        long inserted=this.database.update("setting", values, "name=?", new String[]{"isAutoBackupOn"});
        if(inserted>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean setPassword(String value){
        ContentValues values;
        values = new ContentValues();
        values.put("name", value);

        long inserted=this.database.update("setting", values, "value=?", new String[]{"999"});
        if(inserted>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean setCurrency(String value){
        ContentValues values;
        values = new ContentValues();
        values.put("value", value);

        long inserted=this.database.update("setting", values, "name=?", new String[]{"currency"});
        if(inserted>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean setSecurityQuestion(String value){
        ContentValues values;
        values = new ContentValues();
        values.put("name", value);

        long inserted=this.database.update("setting", values, "value=?", new String[]{"888"});
        if(inserted>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean setQuestionAnswer(String value){
        ContentValues values;
        values = new ContentValues();
        values.put("name", value);

        long inserted=this.database.update("setting", values, "value=?", new String[]{"777"});
        if(inserted>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean updateStatement(StatementData sd,int from){
        long updated;
        ContentValues values = new ContentValues();
        values.put(DATE_FIELD, sd.getDate());
        values.put(SOURCE_WAY_FIELD,sd.getSourceWay());
        values.put(DESCRIPTION_FIELD,sd.getDescription());
        values.put(AMOUNT_FIELD,sd.getAmount());
        values.put(DAY_FIELD,dt.getDay(sd.getDate()));
        values.put(MONTH_FIELD,dt.getMonth(sd.getDate()));
        values.put(YEAR_FIELD,dt.getYear(sd.getDate()));
        values.put(DATEORDER_FIELD, dt.getDateOrder(sd.getDate()));
        if(from==0){
            updated=this.database.update(INCOME_TABLE, values, ID_FIELD + "=?", new String[]{String.valueOf(sd.getId())});
        }else{
            updated=this.database.update(EXPENSE_TABLE, values, ID_FIELD + "=?", new String[]{String.valueOf(sd.getId())});
        }
        if(updated>0){
            return true;
        }
        return false;
    }

    public boolean deleteStatement(String id,int from){
        long deleted;
        if(from==0){
            deleted=this.database.delete(INCOME_TABLE, ID_FIELD + "=?", new String[]{id});
        }else{
            deleted=this.database.delete(EXPENSE_TABLE, ID_FIELD + "=?", new String[]{id});
        }
        if(deleted>0){
            return true;
        }
        return false;
    }

    public double[] getSummery(){
        double[] summery=new double[9];
        Double amount;
        Cursor cursor = this.database.query(INCOME_TABLE, new String[]{"SUM(amount) as in_week"},DATEORDER_FIELD+" BETWEEN ? AND ?", new String[]{dt.getDateOrder(dt.getCurrentDateN7()),dt.getCurrentDateOrder()}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            amount=cursor.getDouble(cursor.getColumnIndex("in_week"));
            summery[0]=round(amount, 2);
        }else{
            summery[0]=0.0;
        }
        cursor.close();
        cursor = this.database.query(EXPENSE_TABLE, new String[]{"SUM(amount) as ex_week"},DATEORDER_FIELD+" BETWEEN ? AND ?", new String[]{dt.getDateOrder(dt.getCurrentDateN7()),dt.getCurrentDateOrder()}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            amount=cursor.getDouble(cursor.getColumnIndex("ex_week"));
            summery[4]=round(amount,2);
        }else{
            summery[4]=0.0;
        }
        cursor.close();
        cursor = this.database.query(INCOME_TABLE, new String[]{"SUM(amount) as in_month"},"month=? AND year=?", new String[]{dt.getCurrentMonth(),dt.getCurrentYear()}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            amount=cursor.getDouble(cursor.getColumnIndex("in_month"));
            summery[1]=round(amount,2);
        }else{
            summery[1]=0.0;
        }
        cursor.close();
        cursor = this.database.query(EXPENSE_TABLE, new String[]{"SUM(amount) as ex_month"},"month=? AND year=?", new String[]{dt.getCurrentMonth(),dt.getCurrentYear()}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            amount=cursor.getDouble(cursor.getColumnIndex("ex_month"));
            summery[5]=round(amount,2);
        }else{
            summery[5]=0.0;
        }
        cursor.close();
        cursor = this.database.query(INCOME_TABLE, new String[]{"SUM(amount) as in_year"},"year=?", new String[]{dt.getCurrentYear()}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            amount=cursor.getDouble(cursor.getColumnIndex("in_year"));
            summery[2]=round(amount,2);
        }else{
            summery[2]=0.0;
        }
        cursor.close();
        cursor = this.database.query(EXPENSE_TABLE, new String[]{"SUM(amount) as ex_year"},"year=?", new String[]{dt.getCurrentYear()}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            amount=cursor.getDouble(cursor.getColumnIndex("ex_year"));
            summery[6]=round(amount,2);
        }else{
            summery[6]=0.0;
        }
        cursor.close();
        cursor = this.database.query(INCOME_TABLE, new String[]{"SUM(amount) as in_total"},null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            amount=cursor.getDouble(cursor.getColumnIndex("in_total"));
            summery[3]=round(amount,2);
        }else{
            summery[3]=0.0;
        }
        cursor.close();
        cursor = this.database.query(EXPENSE_TABLE, new String[]{"SUM(amount) as ex_total"},null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            amount=cursor.getDouble(cursor.getColumnIndex("ex_total"));
            summery[7]=round(amount,2);
        }else{
            summery[7]=0.0;
        }
        cursor.close();
        summery[8]=round(summery[3] - summery[7],2);

        return summery;
    }
    public ArrayList<BarDatas> getDateAndAmountBetweenDate(String date, String lastDate, String type){
        ArrayList<BarDatas> list=new ArrayList<BarDatas>();
        Cursor cursor;
        String curDate=date;
        while (1==1){
            if(type.equalsIgnoreCase("in")) {
                cursor = this.database.query(INCOME_TABLE, new String[]{AMOUNT_FIELD}, DATE_FIELD + "=?", new String[]{curDate}, null, null, null);
            }else{
                cursor = this.database.query(EXPENSE_TABLE, new String[]{AMOUNT_FIELD}, DATE_FIELD + "=?", new String[]{curDate}, null, null, null);
            }
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                double amount= round(cursor.getDouble(cursor.getColumnIndex(AMOUNT_FIELD)), 2);
                cursor.close();
                BarDatas data=new BarDatas((float)amount, curDate);
                list.add(data);
            }else{
                BarDatas data=new BarDatas((float)0.0, curDate);
                list.add(data);
            }
            if(curDate.equalsIgnoreCase(lastDate)){
                cursor.close();
                return list;
            }
            curDate=DateOperation.getNextDate(curDate);
        }
    }
    public double getBetweenDateAmount(String dateFrom,String dateTo,String type){
        Cursor cursor;
        if(type.equalsIgnoreCase("in")) {
            cursor = this.database.query(INCOME_TABLE, new String[]{"SUM(amount) as bda"}, DATEORDER_FIELD + " BETWEEN ? AND ?", new String[]{dateFrom, dateTo}, null, null, null);
        }else{
            cursor = this.database.query(EXPENSE_TABLE, new String[]{"SUM(amount) as bda"}, DATEORDER_FIELD + " BETWEEN ? AND ?", new String[]{dateFrom, dateTo}, null, null, null);
        }
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            double amount= round(cursor.getDouble(cursor.getColumnIndex("bda")), 2);
            cursor.close();
            return amount;
        }
        cursor.close();
        return 0.0;
    }
    public double getBetweenDateAmountWithCat(String dateFrom,String dateTo,String type,String cat){
        Cursor cursor;
        if(type.equalsIgnoreCase("in")) {
            cursor = this.database.query(INCOME_TABLE, new String[]{"SUM(amount) as bda"}, DATEORDER_FIELD + ">="+dateFrom+" AND "+DATEORDER_FIELD+"<="+dateTo+" AND "+SOURCE_WAY_FIELD+"=?", new String[]{cat}, null, null, null);
        }else{
            cursor = this.database.query(EXPENSE_TABLE, new String[]{"SUM(amount) as bda"}, DATEORDER_FIELD + ">="+dateFrom+" AND "+DATEORDER_FIELD+"<="+dateTo+" AND "+SOURCE_WAY_FIELD+"=?", new String[]{cat}, null, null, null);
        }
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            double amount= round(cursor.getDouble(cursor.getColumnIndex("bda")), 2);
            cursor.close();
            return amount;
        }
        cursor.close();
        return 0.0;
    }
    public String[] getAllCategory(String type){
        String[] list;
        Cursor cursor;
        cursor = this.database.query("category", new String[]{"cat_name"},"type=?", new String[]{type}, null, null, "cat_name ASC");
        int size=cursor.getCount();
        list=new String[size];
        if (cursor != null && size > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < size; i++) {
                list[i]= cursor.getString(cursor.getColumnIndex("cat_name"));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }
    public boolean addCategory(String value,String type){
        ContentValues values;
        values = new ContentValues();
        values.put("cat_name", value);
        values.put("type", type);

        long inserted=this.database.insert("category", null, values);
        if(inserted>0){
            return true;
        }else {
            return false;
        }
    }
    public ArrayList<StatementData> getAllStatement(String type){
        ArrayList<StatementData> list=new ArrayList<StatementData>();
        Cursor cursor;
        if(type.equalsIgnoreCase("in")){
            cursor = this.database.query(INCOME_TABLE, null,null, null, null, null, DATEORDER_FIELD+" DESC");
        }else{
            cursor = this.database.query(EXPENSE_TABLE, null,null, null, null, null, DATEORDER_FIELD+" DESC");
        }
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
                String date = cursor.getString(cursor.getColumnIndex(DATE_FIELD));
                String source_way = cursor.getString(cursor.getColumnIndex(SOURCE_WAY_FIELD));
                String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_FIELD));
                double amount = round(cursor.getDouble(cursor.getColumnIndex(AMOUNT_FIELD)),2);
                StatementData sd=new StatementData(id,date,source_way,description,amount,type);
                list.add(sd);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }
    public ArrayList<StatementData> getMonthStatement(String month,String year,String type){
        ArrayList<StatementData> list=new ArrayList<StatementData>();
        Cursor cursor;
        if(type.equalsIgnoreCase("in")){
            cursor = this.database.query(INCOME_TABLE, null,"month=? AND year=?", new String[]{month,year}, null, null, DATEORDER_FIELD+" DESC");
        }else{
            cursor = this.database.query(EXPENSE_TABLE, null,"month=? AND year=?", new String[]{month,year}, null, null, DATEORDER_FIELD+" DESC");
        }
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
                String date = cursor.getString(cursor.getColumnIndex(DATE_FIELD));
                String source_way = cursor.getString(cursor.getColumnIndex(SOURCE_WAY_FIELD));
                String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_FIELD));
                double amount = round(cursor.getDouble(cursor.getColumnIndex(AMOUNT_FIELD)),2);
                StatementData sd=new StatementData(id,date,source_way,description,amount,type);
                list.add(sd);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }
    public ArrayList<StatementData> getYearStatement(String year,String type){
        ArrayList<StatementData> list=new ArrayList<StatementData>();
        Cursor cursor;
        if(type.equalsIgnoreCase("in")){
            cursor = this.database.query(INCOME_TABLE, null,"year=?", new String[]{year}, null, null, DATEORDER_FIELD+" DESC");
        }else{
            cursor = this.database.query(EXPENSE_TABLE, null,"year=?", new String[]{year}, null, null, DATEORDER_FIELD+" DESC");
        }
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
                String date = cursor.getString(cursor.getColumnIndex(DATE_FIELD));
                String source_way = cursor.getString(cursor.getColumnIndex(SOURCE_WAY_FIELD));
                String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_FIELD));
                double amount = round(cursor.getDouble(cursor.getColumnIndex(AMOUNT_FIELD)),2);
                StatementData sd=new StatementData(id,date,source_way,description,amount,type);
                list.add(sd);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }
    public ArrayList<StatementData> getBetweenDateStatement(String do_from,String do_to,String type){
        ArrayList<StatementData> list=new ArrayList<StatementData>();
        Cursor cursor;
        if(type.equalsIgnoreCase("in")){
            cursor = this.database.query(INCOME_TABLE, null,DATEORDER_FIELD+" BETWEEN ? AND ?", new String[]{do_from,do_to}, null, null, DATEORDER_FIELD+" DESC");
        }else{
            cursor = this.database.query(EXPENSE_TABLE, null,DATEORDER_FIELD+" BETWEEN ? AND ?", new String[]{do_from,do_to}, null, null, DATEORDER_FIELD+" DESC");
        }
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
                String date = cursor.getString(cursor.getColumnIndex(DATE_FIELD));
                String source_way = cursor.getString(cursor.getColumnIndex(SOURCE_WAY_FIELD));
                String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_FIELD));
                double amount = round(cursor.getDouble(cursor.getColumnIndex(AMOUNT_FIELD)),2);
                StatementData sd=new StatementData(id,date,source_way,description,amount,type);
                list.add(sd);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }
    public ArrayList<StatementData> getBetweenDateStatementWithCat(String do_from,String do_to,String type,String cat){
        /*String[] cList=new String[catList.size()];
        String catQS="";
        for(int i=0;i<catList.size();i++){
            cList[i]=catList.get(i);
            catQS=catQS+" "+SOURCE_WAY_FIELD+" ="+catList.get(i)+" OR";
        }
        catQS=catQS.substring(0,catQS.length()-2);
        Log.e("query",catQS);
        if(type.equalsIgnoreCase("in")){
            cursor=this.database.rawQuery("SELECT * FROM "+INCOME_TABLE+" WHERE "+
                    DATEORDER_FIELD + ">="+do_from+" AND "+DATEORDER_FIELD+"<="+do_to+" AND"+catQS,null);
        }*/

        ArrayList<StatementData> list=new ArrayList<StatementData>();
        Cursor cursor;
            if(type.equalsIgnoreCase("in")){
                cursor = this.database.query(INCOME_TABLE, null,DATEORDER_FIELD + ">="+do_from+" AND "+DATEORDER_FIELD+"<="+do_to+" AND "+SOURCE_WAY_FIELD+"=?", new String[]{cat}, null, null, DATEORDER_FIELD+" DESC");
            }
            else{
                cursor = this.database.query(EXPENSE_TABLE, null,DATEORDER_FIELD + ">="+do_from+" AND "+DATEORDER_FIELD+"<="+do_to+" AND "+SOURCE_WAY_FIELD+"=?", new String[]{cat}, null, null, DATEORDER_FIELD+" DESC");
            }
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int j = 0; j < cursor.getCount(); j++) {
                    int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
                    String date = cursor.getString(cursor.getColumnIndex(DATE_FIELD));
                    String source_way = cursor.getString(cursor.getColumnIndex(SOURCE_WAY_FIELD));
                    String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_FIELD));
                    double amount = round(cursor.getDouble(cursor.getColumnIndex(AMOUNT_FIELD)),2);
                    StatementData sd=new StatementData(id,date,source_way,description,amount,type);
                    list.add(sd);
                    cursor.moveToNext();
                }
            }

        cursor.close();
        return list;
    }
    public boolean addStatement(StatementData sd){
        ContentValues values=new ContentValues();
        values.put(DATE_FIELD, sd.getDate());
        values.put(SOURCE_WAY_FIELD,sd.getSourceWay());
        values.put(DESCRIPTION_FIELD,sd.getDescription());
        values.put(AMOUNT_FIELD,sd.getAmount());
        values.put(DAY_FIELD,dt.getDay(sd.getDate()));
        values.put(MONTH_FIELD,dt.getMonth(sd.getDate()));
        values.put(YEAR_FIELD,dt.getYear(sd.getDate()));
        values.put(DATEORDER_FIELD, dt.getDateOrder(sd.getDate()));
        long inserted;
        if(sd.getType().equalsIgnoreCase("in")){
            inserted=this.database.insert(INCOME_TABLE, null, values);
        }else{
            inserted=this.database.insert(EXPENSE_TABLE, null, values);
        }
        if(inserted>0){
            return true;
        }
        return false;
    }

    private DB_Manager(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.database = openDatabase();
        dt=new DateOperation();
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
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
