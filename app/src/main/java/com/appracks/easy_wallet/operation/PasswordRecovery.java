package com.appracks.easy_wallet.operation;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appracks.easy_wallet.OverViewActivity;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.database.DB_Manager;

public class PasswordRecovery extends AppCompatActivity {

    private Button btn_getPassword;
    private EditText et_answer;
    private TextView tv_question;
    DB_Manager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbManager=DB_Manager.getInstance(this);
        btn_getPassword=(Button)findViewById(R.id.btn_getPassword);
        et_answer=(EditText)findViewById(R.id.et_answer);
        tv_question=(TextView)findViewById(R.id.tv_question);
        tv_question.setText(dbManager.getSecurityQuestion());
        btn_getPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_answer.getText().toString().equals(dbManager.getQuestionAnswer())) {
                    showPassword("Your password is: " + dbManager.getPassword());
                }else if (et_answer.getText().toString().trim().equals("")) {
                    Snackbar.make(v,"Please enter your answer",Snackbar.LENGTH_LONG).show();
                }else {
                    showPassword("Your answer is wrong !!!");
                }
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
        startActivity(new Intent(PasswordRecovery.this, OverViewActivity.class));
        overridePendingTransition(R.anim.style_static, R.anim.push_down_out);
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
