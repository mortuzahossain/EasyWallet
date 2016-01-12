package com.appracks.easy_wallet.database;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by HABIB on 1/11/2016.
 */
public class DB_BackupRestore {

    private Context context;
    public DB_BackupRestore(Context context){
        this.context = context;
    }
    public void backupDB(boolean b){
        try {
            String currentDBPath = "//data//"+ context.getPackageName() +"//databases//"+DB_Manager.DB_NAME;
            String backupDBPath = "EasyWallet/"+DB_Manager.DB_NAME;

            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            File f=new File(Environment.getExternalStorageDirectory() +"/EasyWallet");
            if(f.exists() && f.isDirectory()){
                f.delete();
            }
            f.mkdir();
            if (sd.canWrite()) {
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                if(b){
                    Toast.makeText(context, "Database backup succesfully to:\n"+backupDB.toString(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public boolean restoreDB(){
        try {
            File f=new File(Environment.getExternalStorageDirectory() +"/EasyWallet");
            if(f.exists() && f.isDirectory()){
                if(new File(Environment.getExternalStorageDirectory() +"/EasyWallet/"+DB_Manager.DB_NAME).exists()){}else {
                    Toast.makeText(context, "Can't find database", Toast.LENGTH_LONG).show();
                    return false;
                }
            }else{
                Toast.makeText(context, "You may not backed up database", Toast.LENGTH_LONG).show();
                return false;
            }

            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+context.getPackageName()+"//databases//"+DB_Manager.DB_NAME;
                String backupDBPath = "EasyWallet/"+DB_Manager.DB_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(context, "Database Restored successfully", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }
}
