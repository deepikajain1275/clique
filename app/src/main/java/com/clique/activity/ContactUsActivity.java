package com.clique.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.clique.R;
import com.clique.utils.CustomTextView;

public class ContactUsActivity extends AppCompatActivity {

    private ImageView ivBack;
    private CustomTextView tvcontact;
    private String phoneno="9723186737";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ivBack=(ImageView)findViewById(R.id.iv_back);
        tvcontact=(CustomTextView)findViewById(R.id.tv_invite);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhatsappContact(phoneno);
                finish();
            }
        });
    }
    void openWhatsappContact(String number) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, ""));
    }
}
