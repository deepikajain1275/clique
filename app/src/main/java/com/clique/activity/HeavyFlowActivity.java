package com.clique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.clique.R;
import com.clique.modle.Data;
import com.clique.utils.Constant;
import com.clique.utils.CustomTextView;
import com.clique.utils.SessionManager;

public class HeavyFlowActivity extends AppCompatActivity {

    private CustomTextView cfflow, tvYes, tvNo;
    SessionManager sessionManager;
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FullScreencall();
        setContentView(R.layout.activity_heavy_flow);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        type = getIntent().getIntExtra(Constant.FLOW, 0);
        sessionManager = new SessionManager(HeavyFlowActivity.this);
        final Data data = sessionManager.getSharedPreferenece(HeavyFlowActivity.this);
        cfflow = (CustomTextView) findViewById(R.id.tv_flow);
        tvNo = (CustomTextView) findViewById(R.id.tv_no);
        tvYes = (CustomTextView) findViewById(R.id.tv_yes);
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HeavyFlowActivity.this, CreateActivity.class);
                i.putExtra(Constant.TYPE,1);
                startActivity(i);
                 finish();
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if (data == null) {
                    Intent i = new Intent(HeavyFlowActivity.this, PincodeActivity.class);
                    startActivity(i);
                    finish();
               /* } else {
                    Intent io = new Intent(HeavyFlowActivity.this, OrderActivity.class);
                    startActivity(io);
                    finish();
                }*/
            }
        });
        if (type == 0) {
            cfflow.setText("Heavy Flow Pad");
        } else {
            cfflow.setText("Regular Flow Pad");
        }
    }
}
