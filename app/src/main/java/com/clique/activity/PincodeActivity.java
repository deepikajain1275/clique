package com.clique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.clique.R;
import com.clique.utils.CustomTextView;

public class PincodeActivity extends AppCompatActivity {


    private CustomTextView cetPincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode);
        init();
    }

    private void init() {
        cetPincode = (CustomTextView) findViewById(R.id.tv_pincode);
        cetPincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PincodeActivity.this, HostalListActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
}
