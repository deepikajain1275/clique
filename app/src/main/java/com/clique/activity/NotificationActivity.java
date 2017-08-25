package com.clique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.clique.R;
import com.clique.adapter.NotificationAdapter;
import com.clique.utils.CustomTextView;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private ImageView ivBack;
    private RecyclerView rvNotification;
    private List<String> alInvite = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    private CustomTextView tvDone;
    private LinearLayout llError;
    private CustomTextView tvName;
    private CustomTextView tvDes;
    private CustomTextView tvChoose;


    private void findViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvDone = (CustomTextView) findViewById(R.id.tv_done);
        llError = (LinearLayout) findViewById(R.id.ll_error);
        tvName = (CustomTextView) findViewById(R.id.tv_name);
        tvDes = (CustomTextView) findViewById(R.id.tv_des);
        tvChoose = (CustomTextView) findViewById(R.id.tv_choose);
        rvNotification = (RecyclerView) findViewById(R.id.rv_notification);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        findViews();
        rvNotification.setVisibility(View.VISIBLE);
        llError.setVisibility(View.GONE);
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotificationActivity.this);
        rvNotification.setLayoutManager(linearLayoutManager);
        notificationAdapter = new NotificationAdapter(alInvite);
        rvNotification.setAdapter(notificationAdapter);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create = new Intent(NotificationActivity.this, CreateActivity.class);
                create.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(create);
                finish();
            }
        });
    }
}
