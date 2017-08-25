package com.clique.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.clique.R;
import com.clique.adapter.OrderHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_orderhistory);
        RecyclerView rvOrder = (RecyclerView) findViewById(R.id.rv_order_history);
        ImageView ivCross = (ImageView) findViewById(R.id.ic_cross);
        List<String> alInvite = new ArrayList<>();
        alInvite.add("");
        alInvite.add("");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyOrderActivity.this);
        rvOrder.setLayoutManager(linearLayoutManager);
        OrderHistoryAdapter orderAdapter = new OrderHistoryAdapter(alInvite);
        rvOrder.setAdapter(orderAdapter);
        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
