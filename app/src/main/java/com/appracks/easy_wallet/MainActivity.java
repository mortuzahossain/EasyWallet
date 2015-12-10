package com.appracks.easy_wallet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.appracks.easy_wallet.database.DB_Manager;
import com.appracks.easy_wallet.expense.Expense;
import com.appracks.easy_wallet.income.Income;
import com.appracks.easy_wallet.operation.AddStatement;

public class MainActivity extends AppCompatActivity {

    DrawerLayout myDrawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    ImageButton btn_add_statement;
    DB_Manager dbManager;
    private TextView tv_in_current_week,tv_in_current_month,tv_in_current_year,tv_in_total,tv_ex_current_week,tv_ex_current_month,tv_ex_current_year,tv_ex_total,tv_balance,tv_nav_balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        dbManager=DB_Manager.getInstance(this);
        tv_balance=(TextView)findViewById(R.id.tv_balance);
        tv_nav_balance=(TextView)findViewById(R.id.tv_current_balance);
        tv_in_current_week=(TextView)findViewById(R.id.tv_in_current_week);
        tv_ex_current_week=(TextView)findViewById(R.id.tv_ex_current_week);
        tv_in_current_month=(TextView)findViewById(R.id.tv_in_current_month);
        tv_ex_current_month=(TextView)findViewById(R.id.tv_ex_current_month);
        tv_in_current_year=(TextView)findViewById(R.id.tv_in_current_year);
        tv_ex_current_year=(TextView)findViewById(R.id.tv_ex_current_year);
        tv_in_total=(TextView)findViewById(R.id.tv_in_total);
        tv_ex_total=(TextView)findViewById(R.id.tv_ex_total);

        btn_add_statement=(ImageButton)findViewById(R.id.btn_add_statement);
        btn_add_statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddStatement.class).putExtra("from","main"));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });

        setSummery();
        setNavMenu();
        setSummeryClick();
    }

    private void setSummery(){
        double[] summery=dbManager.getSummery();
        tv_in_current_week.setText(String.valueOf(summery[0]));
        tv_ex_current_week.setText(String.valueOf(summery[4]));
        tv_in_current_month.setText(String.valueOf(summery[1]));
        tv_ex_current_month.setText(String.valueOf(summery[5]));
        tv_in_current_year.setText(String.valueOf(summery[2]));
        tv_ex_current_year.setText(String.valueOf(summery[6]));
        tv_in_total.setText(String.valueOf(summery[3]));
        tv_ex_total.setText(String.valueOf(summery[7]));
        tv_balance.setText(String.valueOf(summery[8]));
        if(summery[8]<0){
            tv_balance.setTextColor(Color.RED);
            tv_nav_balance.setTextColor(Color.RED);
        }
        tv_nav_balance.setText(String.valueOf(summery[8]));
    }
    private void setSummeryClick(){
        LinearLayout ly_current_week_in,ly_current_week_ex,ly_current_month_in,ly_current_month_ex,ly_current_year_in,ly_current_year_ex,ly_total_in,ly_total_ex;
        ly_current_week_in=(LinearLayout)findViewById(R.id.ly_current_week_in);
        ly_current_week_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Income.class).putExtra("cat_type",0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_current_week_ex=(LinearLayout)findViewById(R.id.ly_current_week_ex);
        ly_current_week_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Expense.class).putExtra("cat_type",0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_current_month_in=(LinearLayout)findViewById(R.id.ly_current_month_in);
        ly_current_month_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Income.class).putExtra("cat_type",1));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_current_month_ex=(LinearLayout)findViewById(R.id.ly_current_month_ex);
        ly_current_month_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Expense.class).putExtra("cat_type",1));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_current_year_in=(LinearLayout)findViewById(R.id.ly_current_year_in);
        ly_current_year_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Income.class).putExtra("cat_type", 2));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_current_year_ex=(LinearLayout)findViewById(R.id.ly_current_year_ex);
        ly_current_year_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Expense.class).putExtra("cat_type",2));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_total_in=(LinearLayout)findViewById(R.id.ly_total_in);
        ly_total_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Income.class).putExtra("cat_type", 3));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_total_ex=(LinearLayout)findViewById(R.id.ly_total_ex);
        ly_total_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Expense.class).putExtra("cat_type",3));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
    }
    private void setNavMenu(){
        myDrawer= (DrawerLayout) findViewById(R.id.myDrawer);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, myDrawer,toolbar, R.string.drawer_open, R.string.drawer_close);
        myDrawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        LinearLayout ly_summery=(LinearLayout) findViewById(R.id.ly_summery);
        ly_summery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(), "You are here", Toast.LENGTH_LONG).show();
            }
        });
        LinearLayout ly_income=(LinearLayout) findViewById(R.id.ly_income);
        ly_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, Income.class).putExtra("cat_type", 0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_expense=(LinearLayout) findViewById(R.id.ly_expense);
        ly_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, Expense.class).putExtra("cat_type", 0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_graph=(LinearLayout) findViewById(R.id.ly_graph);
        ly_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(),"ly_graph",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        myDrawer.closeDrawer(GravityCompat.START);
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
