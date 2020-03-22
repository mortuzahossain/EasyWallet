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
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appracks.easy_wallet.OverViewActivity;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.data_object.StatementData;
import com.appracks.easy_wallet.database.DB_Manager;
import com.appracks.easy_wallet.view.Expense;
import com.appracks.easy_wallet.view.Income;

import java.util.Calendar;

public class AddStatement extends AppCompatActivity {

    private TextInputLayout inputLayoutDescription,inputLayoutAmount;
    private EditText et_description,et_amount,et_add_category;
    private ImageButton btn_date_picker;
    private Button btn_save,btn_save_and_new;
    private Spinner spn_in_ex_cat,spn_cat_type;
    private RadioGroup rg_in_ex_type;
    private RadioButton rb_income,rb_expense;
    private TextView tv_date,tv_inex_way;
    private int year, month, day;
    private Calendar calendar;
    DB_Manager db_manager;
    private String type,from;
    private String[] in_cat,ex_cat;
    LinearLayout lay_add_cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statement);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initLayout();
    }

    private void initLayout(){
        db_manager=DB_Manager.getInstance(this);
        in_cat=db_manager.getAllCategory("in");
        ex_cat=db_manager.getAllCategory("ex");

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

        lay_add_cat=(LinearLayout)findViewById(R.id.lay_add_cat);
        et_add_category=(EditText)findViewById(R.id.et_add_category);
        spn_cat_type=(Spinner)findViewById(R.id.spn_cat_type);
        spn_cat_type.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,new String[]{"INCOME","EXPENSE"}));

    }
    private void setSpinnerCat(int id){
        if(id==R.id.rb_income){
            type="in";
            tv_inex_way.setText("Income source:");
            spn_in_ex_cat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,in_cat));
        }else{
            type="ex";
            tv_inex_way.setText("Expense way:");
            spn_in_ex_cat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ex_cat));
        }
    }
    private void setInputLayout(){
        spn_in_ex_cat=(Spinner)findViewById(R.id.spn_in_ex_cat);
        spn_in_ex_cat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, in_cat));
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

    public void btn_add_cat_cancel(View v){
        lay_add_cat.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down_out));
        lay_add_cat.setVisibility(View.GONE);
    }
    public void btn_add_cat(View v){
        String cName=et_add_category.getText().toString();

        if(cName.trim().equals("")){
            Toast.makeText(getApplicationContext(),"Enter category name",Toast.LENGTH_SHORT).show();
        }else {
            cName=cName.substring(0, 1).toUpperCase() + cName.substring(1);
            if (spn_cat_type.getSelectedItem().toString().equals("INCOME")){
                if(db_manager.addCategory(cName,"in")){
                    Toast.makeText(getApplicationContext(),"Added category "+cName,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }else{
                if(db_manager.addCategory(cName,"ex")){
                    Toast.makeText(getApplicationContext(),"Added category "+cName,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
            lay_add_cat.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down_out));
            lay_add_cat.setVisibility(View.GONE);
            et_add_category.setText("");
            spn_cat_type.setSelection(0);
            initLayout();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(from.equalsIgnoreCase("in")){
            startActivity(new Intent(AddStatement.this, Income.class).putExtra("cat_type", 0));
        }else if(from.equalsIgnoreCase("ex")){
            startActivity(new Intent(AddStatement.this,Expense.class).putExtra("cat_type",0));
        }else {
            startActivity(new Intent(AddStatement.this,OverViewActivity.class));
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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.mi_add_cat:
                lay_add_cat.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_up_in));
                lay_add_cat.setVisibility(View.VISIBLE);
                et_add_category.setFocusable(true);
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
