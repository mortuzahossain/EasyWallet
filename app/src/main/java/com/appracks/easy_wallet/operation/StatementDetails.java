package com.appracks.easy_wallet.operation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appracks.easy_wallet.CustomInterfaceAdapter;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.data_object.StatementData;
import com.appracks.easy_wallet.expense.Expense;
import com.appracks.easy_wallet.income.Income;

public class StatementDetails extends AppCompatActivity {
    private Button btn_back,btn_delete,btn_update;
    private TextView tv_date,tv_source_way_text,tv_source_way,tv_description,tv_amount;
    private int from;
    StatementData sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement_details);
        btn_back=(Button)findViewById(R.id.btn_back);
        btn_delete=(Button)findViewById(R.id.btn_delete);
        btn_update=(Button)findViewById(R.id.btn_update);
        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_source_way_text=(TextView)findViewById(R.id.tv_soure_way_text);
        tv_source_way=(TextView)findViewById(R.id.tv_source_way);
        tv_description=(TextView)findViewById(R.id.tv_description);
        tv_amount=(TextView)findViewById(R.id.tv_amount);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        sd=(StatementData)getIntent().getSerializableExtra("statementObject");
        from=getIntent().getIntExtra("from",0);
        tv_date.setText(sd.getDate());
        tv_source_way.setText(sd.getSourceWay());
        tv_description.setText(sd.getDescription());
        tv_amount.setText(String.valueOf(sd.getAmount()));
        if(from==0){
            tv_source_way_text.setText("Income source");
        }else{
            tv_source_way_text.setText("Expense way");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(from==0){
            startActivity(new Intent(StatementDetails.this, Income.class).putExtra("cat_type",0));
        }else{
            startActivity(new Intent(StatementDetails.this, Expense.class).putExtra("cat_type",0));
        }
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
        finish();
    }
}
