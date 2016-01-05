package com.appracks.easy_wallet.operation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appracks.easy_wallet.MainActivity;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.data_object.StatementData;
import com.appracks.easy_wallet.database.DB_Manager;
import com.appracks.easy_wallet.expense.Expense;
import com.appracks.easy_wallet.income.Income;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

public class AddStatement extends AppCompatActivity {

    private TextInputLayout inputLayoutDescription,inputLayoutAmount;
    private EditText et_description,et_amount;
    private ImageButton btn_date_picker;
    private Button btn_save,btn_save_and_new;
    private Spinner spn_in_ex_cat;
    private RadioGroup rg_in_ex_type;
    private RadioButton rb_income,rb_expense;
    private TextView tv_date,tv_inex_way;
    private int year, month, day;
    private Calendar calendar;
    DB_Manager db_manager;
    private String type,from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statement);
        db_manager=DB_Manager.getInstance(this);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_inex_way=(TextView)findViewById(R.id.tv_inex_way);
        rg_in_ex_type=(RadioGroup)findViewById(R.id.rg_statementType);
        rb_income=(RadioButton)findViewById(R.id.rb_income);
        rb_expense=(RadioButton)findViewById(R.id.rb_expense);
        rg_in_ex_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setSpinnerCat(checkedId);
            }
        });
        type="in";
        setInputLayout();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDate(year, month + 1, day);
        showBannerAds();
    }

    private void setSpinnerCat(int id){
        if(id==R.id.rb_income){
            type="in";
            tv_inex_way.setText("Income source:");
            spn_in_ex_cat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.income_category)));
        }else{
            type="ex";
            tv_inex_way.setText("Expense way:");
            spn_in_ex_cat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.expense_category)));
        }
    }
    private void setInputLayout(){
        spn_in_ex_cat=(Spinner)findViewById(R.id.spn_in_ex_cat);
        spn_in_ex_cat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.income_category)));
        inputLayoutDescription = (TextInputLayout) findViewById(R.id.input_layout_description);
        inputLayoutAmount = (TextInputLayout) findViewById(R.id.input_layout_amount);
        et_description = (EditText) findViewById(R.id.et_description);

        et_amount = (EditText) findViewById(R.id.et_amount);
        et_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateAmount();
            }
        });
        btn_date_picker = (ImageButton) findViewById(R.id.btn_date_picker);
        btn_date_picker.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateAmount()) {
                    return;
                }
                StatementData sd=new StatementData(tv_date.getText().toString(),spn_in_ex_cat.getSelectedItem().toString(),et_description.getText().toString(),Double.valueOf(et_amount.getText().toString()),type);
                if(db_manager.addStatement(sd)){
                    Toast.makeText(getApplicationContext(),"Statement saved",Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else{
                    Snackbar.make(v,"Error! not saved",Snackbar.LENGTH_LONG).show();
                }
            }
        });
        btn_save_and_new = (Button) findViewById(R.id.btn_save_and_new);
        btn_save_and_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateAmount()) {
                    return;
                }
                StatementData sd=new StatementData(tv_date.getText().toString(),spn_in_ex_cat.getSelectedItem().toString(),et_description.getText().toString(),Double.valueOf(et_amount.getText().toString()),type);
                if(db_manager.addStatement(sd)){
                    Toast.makeText(getApplicationContext(),"Statement saved",Toast.LENGTH_LONG).show();
                    et_description.setText("");
                    et_amount.setText("");
                    inputLayoutAmount.setErrorEnabled(false);
                }else{
                    Snackbar.make(v,"Error! not saved",Snackbar.LENGTH_LONG).show();
                }
            }
        });
        from=getIntent().getStringExtra("from");
        if(from.equalsIgnoreCase("ex")){
            setSpinnerCat(R.id.rb_expense);
            rg_in_ex_type.check(R.id.rb_expense);
        }else if(from.equalsIgnoreCase("exFromMain")){
            setSpinnerCat(R.id.rb_expense);
            rg_in_ex_type.check(R.id.rb_expense);
        }
    }
    private void setDate(int year,int month, int day){
        String d;
        String m;
        if (day < 10) {
            d = "0" + String.valueOf(day);
        } else {
            d = String.valueOf(day);
        }
        if (month < 10) {
            m = "0" + String.valueOf(month);
        } else {
            m = String.valueOf(month);
        }
        tv_date.setText(d+"-"+m+"-"+String.valueOf(year));
    }
    private boolean validateAmount() {
        if (et_amount.getText().toString().trim().isEmpty()) {
            inputLayoutAmount.setError("Enter amount");
            et_amount.requestFocus();
            return false;
        } else {
            inputLayoutAmount.setErrorEnabled(false);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(from.equalsIgnoreCase("in")){
            startActivity(new Intent(AddStatement.this, Income.class).putExtra("cat_type", 0));
        }else if(from.equalsIgnoreCase("ex")){
            startActivity(new Intent(AddStatement.this,Expense.class).putExtra("cat_type",0));
        }else {
            startActivity(new Intent(AddStatement.this,MainActivity.class));
        }
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_statement, menu);
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
    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    setDate(arg1, arg2 + 1, arg3);
                }
            }, year, month, day);
        }
        return null;
    }
    public void showBannerAds(){
        final AdView mAdView = (AdView) findViewById(R.id.adView);
        final AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }
        });
    }
}
