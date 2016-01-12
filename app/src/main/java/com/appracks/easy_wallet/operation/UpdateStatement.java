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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.data_object.StatementData;
import com.appracks.easy_wallet.database.DB_Manager;
import com.appracks.easy_wallet.dateOperation.DateOperation;
import com.appracks.easy_wallet.view.Expense;
import com.appracks.easy_wallet.view.Income;

public class UpdateStatement extends AppCompatActivity {

    private TextInputLayout inputLayoutDescription,inputLayoutAmount;
    private EditText et_description,et_amount;
    private ImageButton btn_date_picker;
    private Button btn_save,btn_back;
    private Spinner spn_in_ex_cat;
    private TextView tv_date,tv_inex_way;
    private int year, month, day;
    DB_Manager db_manager;
    DateOperation dt;
    StatementData sd;
    private  int from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_statement);
        db_manager=DB_Manager.getInstance(this);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_inex_way=(TextView)findViewById(R.id.tv_inex_way);
        dt=new DateOperation();
        sd=(StatementData)getIntent().getSerializableExtra("statementObject");
        from=getIntent().getIntExtra("from", 0);
        setInputLayout();
        year = Integer.valueOf(dt.getYear(sd.getDate()));
        month = Integer.valueOf(dt.getMonth(sd.getDate()))-1;
        day = Integer.valueOf(dt.getDay(sd.getDate()));
        setDate(year, month + 1, day);
    }
    private void setInputLayout(){
        spn_in_ex_cat=(Spinner)findViewById(R.id.spn_in_ex_cat);
        String[] catList;
        if(from==0){
            catList=db_manager.getAllCategory("in");
            tv_inex_way.setText("Income source:");
        }else{
            catList=db_manager.getAllCategory("ex");
            tv_inex_way.setText("Expense way:");
        }
        spn_in_ex_cat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, catList));
        spn_in_ex_cat.setSelection(getSelectedCat(catList));
        inputLayoutDescription = (TextInputLayout) findViewById(R.id.input_layout_description);
        inputLayoutAmount = (TextInputLayout) findViewById(R.id.input_layout_amount);
        et_description = (EditText) findViewById(R.id.et_description);
        if(sd.getDescription()==null){
            et_description.setText("");
        }else{
            et_description.setText(sd.getDescription());
        }
        et_amount = (EditText) findViewById(R.id.et_amount);
        et_amount.setText(String.valueOf(sd.getAmount()));
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
                sd=new StatementData(sd.getId(),tv_date.getText().toString(),spn_in_ex_cat.getSelectedItem().toString(),et_description.getText().toString(),Double.valueOf(et_amount.getText().toString()),"inex");
                if(db_manager.updateStatement(sd,from)){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                    if(from==0){
                        startActivity(new Intent(UpdateStatement.this, Income.class).putExtra("cat_type",0));
                    }else{
                        startActivity(new Intent(UpdateStatement.this, Expense.class).putExtra("cat_type",0));
                    }
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    finish();
                }else{
                    Snackbar.make(v, "Error! not updated", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private int getSelectedCat(String[] list){
        for (int i=0;i<list.length;i++){
            if(list[i].equalsIgnoreCase(sd.getSourceWay())){
                return i;

            }
        }
        return 0;
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
        startActivity(new Intent(UpdateStatement.this,StatementDetails.class).putExtra("statementObject",sd).putExtra("from",from));
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        finish();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
