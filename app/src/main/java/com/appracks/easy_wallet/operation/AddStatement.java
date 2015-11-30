package com.appracks.easy_wallet.operation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.appracks.easy_wallet.R;
import java.util.Calendar;

public class AddStatement extends AppCompatActivity {

    private TextInputLayout inputLayoutDescription,inputLayoutAmount;
    private EditText et_description,et_amount;
    private ImageButton btn_date_picker;
    private Button btn_save;
    private Spinner spn_in_ex_cat;
    private RadioGroup rg_in_ex_type;
    private RadioButton rb_income,rb_expense;
    private TextView tv_date;
    private int year, month, day;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statement);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tv_date=(TextView)findViewById(R.id.tv_date);
        rg_in_ex_type=(RadioGroup)findViewById(R.id.rg_statementType);
        rb_income=(RadioButton)findViewById(R.id.rb_income);
        rb_expense=(RadioButton)findViewById(R.id.rb_expense);
        rg_in_ex_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setSpinnerCat(checkedId);
            }
        });
        setInputLayout();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDate(year, month + 1, day);
    }

    private void setSpinnerCat(int id){
        if(id==R.id.rb_income){
            spn_in_ex_cat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.income_category)));
        }else{
            spn_in_ex_cat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.expense_category)));
        }
    }
    private void setInputLayout(){
        spn_in_ex_cat=(Spinner)findViewById(R.id.spn_in_ex_cat);
        spn_in_ex_cat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.income_category)));
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
                Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setDate(int year,int month, int day){
        tv_date.setText(day+"-"+month+"-"+year);
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
}