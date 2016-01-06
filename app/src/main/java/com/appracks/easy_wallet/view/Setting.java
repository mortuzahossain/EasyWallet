package com.appracks.easy_wallet.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.database.DB_Manager;

public class Setting extends AppCompatActivity {

    private Switch switch_pass_on;
    private DB_Manager dbManager;
    private EditText et_password,et_answer;
    private Button btn_setPassword;
    private LinearLayout ly_password;
    private Spinner spn_sq;
    private ArrayAdapter<CharSequence> adapter;
    private String[] qList;
    private String question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        qList=new String[]{"What's your nick name?","What's your gf name?","What's your bf name?","What's your favourite book?"};
        spn_sq=(Spinner) findViewById(R.id.spn_sq);
        et_answer=(EditText)findViewById(R.id.et_sq_ans);
        adapter=new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, qList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_sq.setAdapter(adapter);

        spn_sq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                question=qList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                question=qList[0];
            }
        });
        switch_pass_on=(Switch)findViewById(R.id.sw_password);
        et_password=(EditText)findViewById(R.id.et_password);
        btn_setPassword=(Button)findViewById(R.id.btn_setPassword);
        ly_password=(LinearLayout)findViewById(R.id.ly_password);
        ly_password.setVisibility(View.GONE);
        btn_setPassword.setEnabled(false);
        dbManager=DB_Manager.getInstance(this);
        if(dbManager.getIsPasswordOn()){
            switch_pass_on.setChecked(true);
        }else{
            switch_pass_on.setChecked(false);
        }
        switch_pass_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_pass_on.isChecked()) {
                    ly_password.setVisibility(View.VISIBLE);
                    ly_password.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_up_in));
                } else {
                    ly_password.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down_out));
                    ly_password.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Password lock turned off", Toast.LENGTH_LONG).show();
                    dbManager.setIsPasswordOn(0);
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

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.style_static, R.anim.push_down_out);
        finish();
    }
}
