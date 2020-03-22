package com.appracks.easy_wallet.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appracks.easy_wallet.OverViewActivity;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.database.DB_Manager;

public class Setting extends AppCompatActivity {

    private Switch switch_pass_on,sw_auto_backup;
    private DB_Manager dbManager;
    private EditText et_password,et_answer;
    private Button btn_setPassword,btn_setCurrency,btn_password_cancel;
    private LinearLayout ly_password;
    private Spinner spn_sq;
    private ArrayAdapter<CharSequence> adapter;
    private String[] qList;
    private String question;
    LinearLayout lay_currency;
    ImageButton btn_currency_lay;
    RadioButton rb_0,rb_1,rb_2,rb_3,rb_4,rb_5,rb_6,rb_7,rb_8,rb_9;
    TextView tv_currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbManager=DB_Manager.getInstance(this);

        rb_0=(RadioButton)findViewById(R.id.rb_0);
        rb_1=(RadioButton)findViewById(R.id.rb_1);
        rb_2=(RadioButton)findViewById(R.id.rb_2);
        rb_3=(RadioButton)findViewById(R.id.rb_3);
        rb_4=(RadioButton)findViewById(R.id.rb_4);
        rb_5=(RadioButton)findViewById(R.id.rb_5);
        rb_6=(RadioButton)findViewById(R.id.rb_6);
        rb_7=(RadioButton)findViewById(R.id.rb_7);
        rb_8=(RadioButton)findViewById(R.id.rb_8);
        rb_9=(RadioButton)findViewById(R.id.rb_9);
        rb_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllRBUnChecked();
                rb_0.setChecked(true);
            }
        });
        rb_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllRBUnChecked();
                rb_1.setChecked(true);
            }
        });
        rb_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllRBUnChecked();
                rb_2.setChecked(true);
            }
        });
        rb_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllRBUnChecked();
                rb_3.setChecked(true);
            }
        });
        rb_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllRBUnChecked();
                rb_4.setChecked(true);
            }
        });
        rb_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllRBUnChecked();
                rb_5.setChecked(true);
            }
        });
        rb_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllRBUnChecked();
                rb_6.setChecked(true);
            }
        });
        rb_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllRBUnChecked();
                rb_7.setChecked(true);
            }
        });
        rb_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllRBUnChecked();
                rb_8.setChecked(true);
            }
        });
        rb_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllRBUnChecked();
                rb_9.setChecked(true);
            }
        });
        if (rb_1.getText().toString().equalsIgnoreCase(dbManager.getCurrency())){
            rb_1.setChecked(true);
        }else if (rb_2.getText().toString().equalsIgnoreCase(dbManager.getCurrency())){
            rb_2.setChecked(true);
        }else if (rb_3.getText().toString().equalsIgnoreCase(dbManager.getCurrency())){
            rb_3.setChecked(true);
        }else if (rb_4.getText().toString().equalsIgnoreCase(dbManager.getCurrency())){
            rb_4.setChecked(true);
        }else if (rb_5.getText().toString().equalsIgnoreCase(dbManager.getCurrency())){
            rb_5.setChecked(true);
        }else if (rb_6.getText().toString().equalsIgnoreCase(dbManager.getCurrency())){
            rb_6.setChecked(true);
        }else if (rb_7.getText().toString().equalsIgnoreCase(dbManager.getCurrency())){
            rb_7.setChecked(true);
        }else if (rb_8.getText().toString().equalsIgnoreCase(dbManager.getCurrency())){
            rb_8.setChecked(true);
        }else if (rb_9.getText().toString().equalsIgnoreCase(dbManager.getCurrency())){
            rb_9.setChecked(true);
        }else if (rb_0.getText().toString().equalsIgnoreCase(dbManager.getCurrency())){
            rb_0.setChecked(true);
        }

        tv_currency=(TextView)findViewById(R.id.tv_currency);
        tv_currency.setText(dbManager.getCurrency());
        lay_currency=(LinearLayout)findViewById(R.id.lay_currency);
        lay_currency.setVisibility(View.GONE);

        btn_password_cancel=(Button)findViewById(R.id.btn_password_cancel);
        btn_password_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ly_password.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down_out));
                ly_password.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Password lock turned off", Toast.LENGTH_LONG).show();
                switch_pass_on.setChecked(false);
            }
        });
        btn_currency_lay=(ImageButton)findViewById(R.id.btn_currency_lay);
        qList=getResources().getStringArray(R.array.security_question);
        spn_sq=(Spinner) findViewById(R.id.spn_sq);
        et_answer=(EditText)findViewById(R.id.et_sq_ans);
        adapter=new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, qList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_sq.setAdapter(adapter);

        spn_sq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                question = qList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                question = qList[0];
            }
        });
        switch_pass_on=(Switch)findViewById(R.id.sw_password);
        sw_auto_backup=(Switch)findViewById(R.id.sw_auto_backup);
        et_password=(EditText)findViewById(R.id.et_password);
        btn_setPassword=(Button)findViewById(R.id.btn_setPassword);
        btn_setCurrency=(Button)findViewById(R.id.btn_setCurrency);
        ly_password=(LinearLayout)findViewById(R.id.ly_password);
        ly_password.setVisibility(View.GONE);
        btn_setPassword.setEnabled(false);

        if(dbManager.getIsPasswordOn()){
            switch_pass_on.setChecked(true);
        }else{
            switch_pass_on.setChecked(false);
        }
        if(dbManager.getIsAutoBackupOn()){
            sw_auto_backup.setChecked(true);
        }else{
            sw_auto_backup.setChecked(false);
        }
        switch_pass_on.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ly_password.setVisibility(View.VISIBLE);
                    lay_currency.setVisibility(View.GONE);
                    ly_password.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_up_in));
                    OverViewActivity.alrearyChecked=true;
                } else {
                    ly_password.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down_out));
                    ly_password.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Password lock turned off", Toast.LENGTH_LONG).show();
                    dbManager.setIsPasswordOn(0);
                }
            }
        });
        sw_auto_backup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(dbManager.setIsAutoBackupOn(1)){
                        Toast.makeText(getApplicationContext(), "Auto backup turned on", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if(dbManager.setIsAutoBackupOn(0)){
                        Toast.makeText(getApplicationContext(), "Auto backup turned off", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        et_answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0 && et_password.getText().toString().trim().length() > 3) {
                    btn_setPassword.setEnabled(true);
                } else {
                    btn_setPassword.setEnabled(false);
                }
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()>3 && et_answer.getText().toString().trim().length()>0){
                    btn_setPassword.setEnabled(true);
                }else{
                    btn_setPassword.setEnabled(false);
                }
            }
        });
        btn_setPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.setPassword(et_password.getText().toString().trim());
                dbManager.setSecurityQuestion(question);
                dbManager.setQuestionAnswer(et_answer.getText().toString());
                dbManager.setIsPasswordOn(1);
                Toast.makeText(getApplicationContext(), "Password lock turned on ", Toast.LENGTH_LONG).show();
                ly_password.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down_out));
                ly_password.setVisibility(View.GONE);
            }
        });
        btn_currency_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_currency.setVisibility(View.VISIBLE);
                lay_currency.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_up_in));
                ly_password.setVisibility(View.GONE);
                if(dbManager.getIsPasswordOn()){}else{
                    switch_pass_on.setChecked(false);
                }
            }
        });
        btn_setCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rb_1.isChecked()){
                    tv_currency.setText("DOLLAR");
                    dbManager.setCurrency("DOLLAR");
                }else if(rb_2.isChecked()){
                    tv_currency.setText("TAKA");
                    dbManager.setCurrency("TAKA");
                }else if(rb_3.isChecked()){
                    tv_currency.setText("POUND");
                    dbManager.setCurrency("POUND");
                }else if(rb_4.isChecked()){
                    tv_currency.setText("RUPEE");
                    dbManager.setCurrency("RUPEE");
                }else if(rb_5.isChecked()){
                    tv_currency.setText("RIAL");
                    dbManager.setCurrency("RIAL");
                }else if(rb_6.isChecked()){
                    tv_currency.setText("EURO");
                    dbManager.setCurrency("EURO");
                }else if(rb_7.isChecked()){
                    tv_currency.setText("YEN");
                    dbManager.setCurrency("YEN");
                }else if(rb_8.isChecked()){
                    tv_currency.setText("YUAN");
                    dbManager.setCurrency("YUAN");
                }else if(rb_9.isChecked()){
                    tv_currency.setText("FRANC");
                    dbManager.setCurrency("FRANC");
                }else if(rb_0.isChecked()){
                    tv_currency.setText("NO CURRENCY");
                    dbManager.setCurrency("NO CURRENCY");
                }

                lay_currency.setVisibility(View.GONE);
                lay_currency.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down_out));
            }
        });

    }
    private void setAllRBUnChecked(){
        rb_0.setChecked(false);
        rb_1.setChecked(false);
        rb_2.setChecked(false);
        rb_3.setChecked(false);
        rb_4.setChecked(false);
        rb_5.setChecked(false);
        rb_6.setChecked(false);
        rb_7.setChecked(false);
        rb_8.setChecked(false);
        rb_9.setChecked(false);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Setting.this, OverViewActivity.class));
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
