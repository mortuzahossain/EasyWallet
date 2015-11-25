package com.appracks.easy_wallet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navMenu;
    DrawerLayout myDrawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        navMenu= (NavigationView) findViewById(R.id.navMenu);
        navMenu.setNavigationItemSelectedListener(this);
        myDrawer= (DrawerLayout) findViewById(R.id.myDrawer);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, myDrawer,toolbar, R.string.drawer_open, R.string.drawer_close);
        myDrawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Are you sure to want to quit?");
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
                System.exit(0);
            }
        });
        alertDialog.setNegativeButton("No", null);
        alertDialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemId=menuItem.getItemId();
        if(itemId == R.id.mi_summery){
            myDrawer.closeDrawer(GravityCompat.START);
            Toast.makeText(getApplicationContext(),"ok 1",Toast.LENGTH_LONG).show();
            return true;
        }else if(itemId == R.id.mi_income){
            myDrawer.closeDrawer(GravityCompat.START);
            Toast.makeText(getApplicationContext(),"ok 2",Toast.LENGTH_LONG).show();
            return true;
        }else if(itemId == R.id.mi_expense){
            myDrawer.closeDrawer(GravityCompat.START);

            return true;
        }else if(itemId == R.id.mi_graph){
            myDrawer.closeDrawer(GravityCompat.START);

            return true;
        }else if(itemId == R.id.mi_others){
            myDrawer.closeDrawer(GravityCompat.START);

            return true;
        }
        return false;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
