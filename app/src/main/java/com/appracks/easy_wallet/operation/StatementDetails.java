package com.appracks.easy_wallet.operation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.data_object.StatementData;
import com.appracks.easy_wallet.database.DB_Manager;
import com.appracks.easy_wallet.view.Expense;
import com.appracks.easy_wallet.view.Income;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class StatementDetails extends AppCompatActivity {
    private Button btn_back,btn_delete,btn_update;
    private TextView tv_date,tv_source_way_text,tv_source_way,tv_description,tv_amount;
    private int from;
    StatementData sd;
    DB_Manager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement_details);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sd=(StatementData)getIntent().getSerializableExtra("statementObject");
        from=getIntent().getIntExtra("from",0);
        dbManager=DB_Manager.getInstance(this);
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
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(StatementDetails.this);
                alertDialog.setTitle("Are you want to delete?");
                alertDialog.setIcon(R.mipmap.ic_launcher);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dbManager.deleteStatement(String.valueOf(sd.getId()), from)) {
                            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(getApplicationContext(), "ERROR !", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", null);
                alertDialog.show();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StatementDetails.this, UpdateStatement.class).putExtra("from", from).putExtra("statementObject", sd));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });

        tv_date.setText(": " + sd.getDate());
        tv_source_way.setText(": "+sd.getSourceWay());
        tv_description.setText(": "+sd.getDescription());
        tv_amount.setText(": "+String.valueOf(sd.getAmount()));
        if(from==0){
            tv_source_way_text.setText("Income source");
            getSupportActionBar().setTitle("Income details");
        }else{
            tv_source_way_text.setText("Expense way");
            getSupportActionBar().setTitle("Expense details");
        }
        showBannerAds();
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
