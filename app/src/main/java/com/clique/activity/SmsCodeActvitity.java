package com.clique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;

import com.clique.R;
import com.clique.utils.CEditText;

public class SmsCodeActvitity extends AppCompatActivity {

    private CEditText cetPincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sms_code_actvitity);
        init();
    }

    private void init() {
        cetPincode = (CEditText) findViewById(R.id.cet_phoneno);
        cetPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 6) {
                   /* cetPincode.setError("Enter correct sms code");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(cetPincode.getWindowToken(), 0);*/
                    Intent io = new Intent(SmsCodeActvitity.this, OrderActivity.class);
                    startActivity(io);
                    finish();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
