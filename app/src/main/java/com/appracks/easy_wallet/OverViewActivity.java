package com.appracks.easy_wallet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appracks.easy_wallet.database.DB_BackupRestore;
import com.appracks.easy_wallet.database.DB_Manager;
import com.appracks.easy_wallet.operation.AddStatement;
import com.appracks.easy_wallet.operation.PasswordRecovery;
import com.appracks.easy_wallet.service.AppAnalytics;
import com.appracks.easy_wallet.view.About;
import com.appracks.easy_wallet.view.Calculator;
import com.appracks.easy_wallet.view.Expense;
import com.appracks.easy_wallet.view.Graph;
import com.appracks.easy_wallet.view.Income;
import com.appracks.easy_wallet.view.Setting;
import com.google.android.gms.analytics.HitBuilders;

public class OverViewActivity extends AppCompatActivity {
    DB_BackupRestore d;
    private boolean isAutoBackup = false;
    public static boolean alrearyChecked = false;
    public static String sign = "";
    DrawerLayout myDrawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    ImageButton btn_add_in_statement, btn_add_ex_statement;
    LinearLayout add_in_statement, add_ex_statement;
    DB_Manager dbManager;
    private TextView tv_current_status, tv_in_current_week, tv_in_current_month, tv_in_current_year, tv_in_total, tv_ex_current_week, tv_ex_current_month, tv_ex_current_year, tv_ex_total, tv_balance, tv_nav_balance;
    private AlertDialog.Builder builder;
    ImageView iv_currency_1, iv_currency_2, iv_currency_3, iv_currency_4, iv_currency_5, iv_currency_6, iv_currency_7, iv_currency_8, iv_currency_9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        dbManager = DB_Manager.getInstance(this);
        d = new DB_BackupRestore(this);
        sign = dbManager.getCurrency();
        isAutoBackup = dbManager.getIsAutoBackupOn();
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        tv_nav_balance = (TextView) findViewById(R.id.tv_current_balance);
        tv_in_current_week = (TextView) findViewById(R.id.tv_in_current_week);
        tv_ex_current_week = (TextView) findViewById(R.id.tv_ex_current_week);
        tv_in_current_month = (TextView) findViewById(R.id.tv_in_current_month);
        tv_ex_current_month = (TextView) findViewById(R.id.tv_ex_current_month);
        tv_in_current_year = (TextView) findViewById(R.id.tv_in_current_year);
        tv_ex_current_year = (TextView) findViewById(R.id.tv_ex_current_year);
        tv_in_total = (TextView) findViewById(R.id.tv_in_total);
        tv_ex_total = (TextView) findViewById(R.id.tv_ex_total);
        tv_current_status = (TextView) findViewById(R.id.tv_current_status);
        add_in_statement = (LinearLayout) findViewById(R.id.add_in_statement);
        add_ex_statement = (LinearLayout) findViewById(R.id.add_ex_statement);
        if (dbManager.getIsPasswordOn()) {
            if (alrearyChecked) {
            } else {
                setPasswordCheck();
            }
        }

        btn_add_in_statement = (ImageButton) findViewById(R.id.btn_add_in_statement);
        btn_add_in_statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_in_statement.setBackgroundResource(R.color.color_in_pressed);
                startActivity(new Intent(OverViewActivity.this, AddStatement.class).putExtra("from", "main"));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        btn_add_ex_statement = (ImageButton) findViewById(R.id.btn_add_ex_statement);
        btn_add_ex_statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_ex_statement.setBackgroundResource(R.color.color_ex_pressed);
                startActivity(new Intent(OverViewActivity.this, AddStatement.class).putExtra("from", "exFromMain"));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });

        setSummery();
        setNavMenu();
        setSummeryClick();
        setCurrencyIcon();
        AppAnalytics.tracker().send(new HitBuilders.EventBuilder("ui", "open")
                .setLabel(getString(R.string.app_name))
                .build());
    }

    public void add_in_statement(View v) {
        startActivity(new Intent(OverViewActivity.this, AddStatement.class).putExtra("from", "main"));
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }

    public void add_ex_statement(View v) {
        startActivity(new Intent(OverViewActivity.this, AddStatement.class).putExtra("from", "exFromMain"));
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }

    private void setCurrencyIcon() {
        iv_currency_1 = (ImageView) findViewById(R.id.iv_currency_1);
        iv_currency_2 = (ImageView) findViewById(R.id.iv_currency_2);
        iv_currency_3 = (ImageView) findViewById(R.id.iv_currency_3);
        iv_currency_4 = (ImageView) findViewById(R.id.iv_currency_4);
        iv_currency_5 = (ImageView) findViewById(R.id.iv_currency_5);
        iv_currency_6 = (ImageView) findViewById(R.id.iv_currency_6);
        iv_currency_7 = (ImageView) findViewById(R.id.iv_currency_7);
        iv_currency_8 = (ImageView) findViewById(R.id.iv_currency_8);
        iv_currency_9 = (ImageView) findViewById(R.id.iv_currency_9);
        if (sign.equalsIgnoreCase("TAKA")) {
            iv_currency_1.setImageResource(R.drawable.taka_sign);
            iv_currency_2.setImageResource(R.drawable.taka_sign);
            iv_currency_3.setImageResource(R.drawable.taka_sign);
            iv_currency_4.setImageResource(R.drawable.taka_sign);
            iv_currency_5.setImageResource(R.drawable.taka_sign);
            iv_currency_6.setImageResource(R.drawable.taka_sign);
            iv_currency_7.setImageResource(R.drawable.taka_sign);
            iv_currency_8.setImageResource(R.drawable.taka_sign);
            iv_currency_9.setImageResource(R.drawable.taka_sign);
        } else if (sign.equalsIgnoreCase("DOLLAR")) {
            iv_currency_1.setImageResource(R.drawable.dollar_sign);
            iv_currency_2.setImageResource(R.drawable.dollar_sign);
            iv_currency_3.setImageResource(R.drawable.dollar_sign);
            iv_currency_4.setImageResource(R.drawable.dollar_sign);
            iv_currency_5.setImageResource(R.drawable.dollar_sign);
            iv_currency_6.setImageResource(R.drawable.dollar_sign);
            iv_currency_7.setImageResource(R.drawable.dollar_sign);
            iv_currency_8.setImageResource(R.drawable.dollar_sign);
            iv_currency_9.setImageResource(R.drawable.dollar_sign);
        } else if (sign.equalsIgnoreCase("POUND")) {
            iv_currency_1.setImageResource(R.drawable.pound_sign);
            iv_currency_2.setImageResource(R.drawable.pound_sign);
            iv_currency_3.setImageResource(R.drawable.pound_sign);
            iv_currency_4.setImageResource(R.drawable.pound_sign);
            iv_currency_5.setImageResource(R.drawable.pound_sign);
            iv_currency_6.setImageResource(R.drawable.pound_sign);
            iv_currency_7.setImageResource(R.drawable.pound_sign);
            iv_currency_8.setImageResource(R.drawable.pound_sign);
            iv_currency_9.setImageResource(R.drawable.pound_sign);
        } else if (sign.equalsIgnoreCase("EURO")) {
            iv_currency_1.setImageResource(R.drawable.euro_sign);
            iv_currency_2.setImageResource(R.drawable.euro_sign);
            iv_currency_3.setImageResource(R.drawable.euro_sign);
            iv_currency_4.setImageResource(R.drawable.euro_sign);
            iv_currency_5.setImageResource(R.drawable.euro_sign);
            iv_currency_6.setImageResource(R.drawable.euro_sign);
            iv_currency_7.setImageResource(R.drawable.euro_sign);
            iv_currency_8.setImageResource(R.drawable.euro_sign);
            iv_currency_9.setImageResource(R.drawable.euro_sign);
        } else if (sign.equalsIgnoreCase("RUPEE")) {
            iv_currency_1.setImageResource(R.drawable.rupee_sign);
            iv_currency_2.setImageResource(R.drawable.rupee_sign);
            iv_currency_3.setImageResource(R.drawable.rupee_sign);
            iv_currency_4.setImageResource(R.drawable.rupee_sign);
            iv_currency_5.setImageResource(R.drawable.rupee_sign);
            iv_currency_6.setImageResource(R.drawable.rupee_sign);
            iv_currency_7.setImageResource(R.drawable.rupee_sign);
            iv_currency_8.setImageResource(R.drawable.rupee_sign);
            iv_currency_9.setImageResource(R.drawable.rupee_sign);
        } else if (sign.equalsIgnoreCase("RIAL")) {
            iv_currency_1.setImageResource(R.drawable.rial_sign);
            iv_currency_2.setImageResource(R.drawable.rial_sign);
            iv_currency_3.setImageResource(R.drawable.rial_sign);
            iv_currency_4.setImageResource(R.drawable.rial_sign);
            iv_currency_5.setImageResource(R.drawable.rial_sign);
            iv_currency_6.setImageResource(R.drawable.rial_sign);
            iv_currency_7.setImageResource(R.drawable.rial_sign);
            iv_currency_8.setImageResource(R.drawable.rial_sign);
            iv_currency_9.setImageResource(R.drawable.rial_sign);
        } else if (sign.equalsIgnoreCase("YEN")) {
            iv_currency_1.setImageResource(R.drawable.yen_sign);
            iv_currency_2.setImageResource(R.drawable.yen_sign);
            iv_currency_3.setImageResource(R.drawable.yen_sign);
            iv_currency_4.setImageResource(R.drawable.yen_sign);
            iv_currency_5.setImageResource(R.drawable.yen_sign);
            iv_currency_6.setImageResource(R.drawable.yen_sign);
            iv_currency_7.setImageResource(R.drawable.yen_sign);
            iv_currency_8.setImageResource(R.drawable.yen_sign);
            iv_currency_9.setImageResource(R.drawable.yen_sign);
        } else if (sign.equalsIgnoreCase("YUAN")) {
            iv_currency_1.setImageResource(R.drawable.yuan_sign);
            iv_currency_2.setImageResource(R.drawable.yuan_sign);
            iv_currency_3.setImageResource(R.drawable.yuan_sign);
            iv_currency_4.setImageResource(R.drawable.yuan_sign);
            iv_currency_5.setImageResource(R.drawable.yuan_sign);
            iv_currency_6.setImageResource(R.drawable.yuan_sign);
            iv_currency_7.setImageResource(R.drawable.yuan_sign);
            iv_currency_8.setImageResource(R.drawable.yuan_sign);
            iv_currency_9.setImageResource(R.drawable.yuan_sign);
        } else if (sign.equalsIgnoreCase("FRANC")) {
            iv_currency_1.setImageResource(R.drawable.franc_sign);
            iv_currency_2.setImageResource(R.drawable.franc_sign);
            iv_currency_3.setImageResource(R.drawable.franc_sign);
            iv_currency_4.setImageResource(R.drawable.franc_sign);
            iv_currency_5.setImageResource(R.drawable.franc_sign);
            iv_currency_6.setImageResource(R.drawable.franc_sign);
            iv_currency_7.setImageResource(R.drawable.franc_sign);
            iv_currency_8.setImageResource(R.drawable.franc_sign);
            iv_currency_9.setImageResource(R.drawable.franc_sign);
        } else if (sign.equalsIgnoreCase("NO CURRENCY")) {
            iv_currency_1.setImageResource(R.drawable.no_currency);
            iv_currency_2.setImageResource(R.drawable.no_currency);
            iv_currency_3.setImageResource(R.drawable.no_currency);
            iv_currency_4.setImageResource(R.drawable.no_currency);
            iv_currency_5.setImageResource(R.drawable.no_currency);
            iv_currency_6.setImageResource(R.drawable.no_currency);
            iv_currency_7.setImageResource(R.drawable.no_currency);
            iv_currency_8.setImageResource(R.drawable.no_currency);
            iv_currency_9.setImageResource(R.drawable.no_currency);
        }

    }

    private void setPasswordCheck() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher);
        this.builder = builder.setTitle("Easy Wallet is locked  !");
        final EditText input = new EditText(this);
        input.setHint("Enter password");
        input.setGravity(Gravity.CENTER);
        input.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setSelection(input.getText().length());
        builder.setView(input);
        builder.setPositiveButton("Unlock", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().trim().equals(dbManager.getPassword())) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    alrearyChecked = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong password! try again...", Toast.LENGTH_SHORT).show();
                    setPasswordCheck();
                }
            }
        });
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });
        builder.setNeutralButton("Forget?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(OverViewActivity.this, PasswordRecovery.class));
                finish();
            }
        });
        builder.show();
    }

    private void setSummery() {
        double[] summery = dbManager.getSummery();
        tv_in_current_week.setText(String.valueOf(summery[0]));
        tv_ex_current_week.setText(String.valueOf(summery[4]));
        tv_in_current_month.setText(String.valueOf(summery[1]));
        tv_ex_current_month.setText(String.valueOf(summery[5]));
        tv_in_current_year.setText(String.valueOf(summery[2]));
        tv_ex_current_year.setText(String.valueOf(summery[6]));
        tv_in_total.setText(String.valueOf(summery[3]));
        tv_ex_total.setText(String.valueOf(summery[7]));
        tv_balance.setText(String.valueOf(summery[8]));
        if (summery[8] < 0) {
            tv_balance.setTextColor(Color.RED);
            tv_nav_balance.setTextColor(Color.RED);
            tv_current_status.setText("NOT GOOD\nYour expense is higher than income. Save money.");
            tv_current_status.setTextColor(Color.parseColor("#FF3300"));
        } else {
            tv_nav_balance.setText(String.valueOf(summery[8]));
            tv_current_status.setText("GOOD");
        }
    }

    private void setSummeryClick() {
        LinearLayout ly_current_week_in, ly_current_week_ex, ly_current_month_in, ly_current_month_ex, ly_current_year_in, ly_current_year_ex, ly_total_in, ly_total_ex;
        ly_current_week_in = (LinearLayout) findViewById(R.id.ly_current_week_in);
        ly_current_week_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverViewActivity.this, Income.class).putExtra("cat_type", 0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_current_week_ex = (LinearLayout) findViewById(R.id.ly_current_week_ex);
        ly_current_week_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverViewActivity.this, Expense.class).putExtra("cat_type", 0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_current_month_in = (LinearLayout) findViewById(R.id.ly_current_month_in);
        ly_current_month_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverViewActivity.this, Income.class).putExtra("cat_type", 1));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_current_month_ex = (LinearLayout) findViewById(R.id.ly_current_month_ex);
        ly_current_month_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverViewActivity.this, Expense.class).putExtra("cat_type", 1));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_current_year_in = (LinearLayout) findViewById(R.id.ly_current_year_in);
        ly_current_year_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverViewActivity.this, Income.class).putExtra("cat_type", 2));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_current_year_ex = (LinearLayout) findViewById(R.id.ly_current_year_ex);
        ly_current_year_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverViewActivity.this, Expense.class).putExtra("cat_type", 2));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_total_in = (LinearLayout) findViewById(R.id.ly_total_in);
        ly_total_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverViewActivity.this, Income.class).putExtra("cat_type", 3));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        ly_total_ex = (LinearLayout) findViewById(R.id.ly_total_ex);
        ly_total_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverViewActivity.this, Expense.class).putExtra("cat_type", 3));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
    }

    private void setNavMenu() {
        myDrawer = (DrawerLayout) findViewById(R.id.myDrawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, myDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        myDrawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        LinearLayout ly_summery = (LinearLayout) findViewById(R.id.ly_summery);
        ly_summery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(), "You are here", Toast.LENGTH_LONG).show();
            }
        });
        LinearLayout ly_income = (LinearLayout) findViewById(R.id.ly_income);
        ly_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(OverViewActivity.this, Income.class).putExtra("cat_type", 0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_expense = (LinearLayout) findViewById(R.id.ly_expense);
        ly_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(OverViewActivity.this, Expense.class).putExtra("cat_type", 0));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_graph = (LinearLayout) findViewById(R.id.ly_graph);
        ly_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(OverViewActivity.this, Graph.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
        LinearLayout ly_tax_calculator = (LinearLayout) findViewById(R.id.ly_tax_calculator);
        ly_tax_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(OverViewActivity.this, Calculator.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        myDrawer.closeDrawer(GravityCompat.START);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Are you sure to want to quit?");
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (isAutoBackup) {
                    d.backupDB(false);
                }
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
            startActivity(new Intent(OverViewActivity.this, Setting.class));
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            finish();
            return true;
        } else if (id == R.id.mi_about) {
            startActivity(new Intent(OverViewActivity.this, About.class));
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            return true;
        } else if (id == R.id.mi_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Download Easy Wallet application from below link:\n https://play.google.com/store/apps/details?id=com.appracks.easy_wallet");
            intent.setType("text/plain");
            startActivity(intent);
            return true;
        } else if (id == R.id.mi_backup) {
            d.backupDB(true);
        } else if (id == R.id.mi_restore) {
            if (d.restoreDB()) {
                setSummery();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
/*
Easy Wallet created by Habibur Rahman
Mobile: 8801726628182
 */
