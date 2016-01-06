package com.appracks.easy_wallet.operation;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appracks.easy_wallet.MainActivity;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.database.DB_Manager;

public class PasswordRecovery extends AppCompatActivity {

    private Button btn_getPassword,btn_back;
    private EditText et_answer;
    private TextView tv_question;
    DB_Manager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        dbManager=DB_Manager.getInstance(this);
        btn_getPassword=(Button)findViewById(R.id.btn_getPassword);
        btn_back=(Button)findViewById(R.id.btn_back);
        et_answer=(EditText)findViewById(R.id.et_answer);
        tv_question=(TextView)findViewById(R.id.tv_question);
        tv_question.setText(dbManager.getSecurityQuestion());
        btn_getPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_answer.getText().toString().equals(dbManager.getQuestionAnswer())) {
                    showPassword("Your password is: " + dbManager.getPassword());
                } else {
                    showPassword("Your answer is wrong !!!");
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void showPassword(String message){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", null);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PasswordRecovery.this, MainActivity.class));
        overridePendingTransition(R.anim.style_static, R.anim.push_down_out);
        finish();
    }
}