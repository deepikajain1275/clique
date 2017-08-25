package com.clique.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.clique.R;
import com.clique.modle.Data;
import com.clique.modle.FinalProduct;
import com.clique.modle.Pincode;
import com.clique.modle.Product;
import com.clique.utils.CEditText;
import com.clique.utils.Constant;
import com.clique.utils.CustomTextView;
import com.clique.utils.InternetConnection;
import com.clique.utils.SessionManager;
import com.clique.utils.nowifiDialog;
import com.clique.webservice.ApiClient;
import com.clique.webservice.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterPincodeActivity extends AppCompatActivity {

    private CustomTextView launch;
    private CEditText cetPincode;
    private ProgressDialog progressDialog;
    private nowifiDialog nowifiDialog1;
    private int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pincode);
        init();
    }

    private void init() {
        userid=getIntent().getIntExtra(Constant.USERID,0);
        progressDialog = new ProgressDialog(EnterPincodeActivity.this);
        progressDialog.setMessage("Please wait..");
        launch=(CustomTextView)findViewById(R.id.tv_Lunch);
        cetPincode=(CEditText) findViewById(R.id.cet_pincode);
       /* cetPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 6) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(cetPincode.getWindowToken(), 0);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(cetPincode.getText()))
                    callUnRegisterAddresswebService(cetPincode.getText().toString());
//                Intent i = new Intent(EnterPincodeActivity.this, NotifimeActivity.class);
//                startActivity(i);
//                finish();
            }
        });
    }

    private void callUnRegisterAddresswebService(final String address) {
        if (InternetConnection.onCheckInternet(EnterPincodeActivity.this)) {
            progressDialog.show();
            final ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            SessionManager sessionManager = new SessionManager(EnterPincodeActivity.this);
            FinalProduct finalProduct = sessionManager.getArrayLisOrder(EnterPincodeActivity.this);
            ArrayList<Product> alProduct = finalProduct.alFinalProduct;
            ArrayList<String> productid= new ArrayList<>();
            for(int i=0;i<alProduct.size();i++)
            {
                productid.add(alProduct.get(i).iD);
            }
            Call<Pincode> call = apiService.unregisteraddress(address,userid,productid);
            call.enqueue(new Callback<Pincode>() {
                @Override
                public void onResponse(Call<Pincode> call, Response<Pincode> response) {
                    progressDialog.dismiss();
                    Data data= response.body().data;
                    Intent i = new Intent(EnterPincodeActivity.this, NotifimeActivity.class);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onFailure(Call<Pincode> call, Throwable t) {
                    progressDialog.dismiss();
                    nowifiDialog1 = new nowifiDialog(EnterPincodeActivity.this, new nowifiDialog.MyClickListner() {
                        @Override
                        public void onClick() {
                            progressDialog.show();
                            callUnRegisterAddresswebService(address);
                            nowifiDialog1.dismiss();
                        }
                    }, "Something went wrong.");
                    nowifiDialog1.show();
                    Toast.makeText(EnterPincodeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            nowifiDialog1 = new nowifiDialog(EnterPincodeActivity.this, new nowifiDialog.MyClickListner() {
                @Override
                public void onClick() {
                    nowifiDialog1.dismiss();
                    callUnRegisterAddresswebService(address);
                }
            });
            nowifiDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            nowifiDialog1.show();
        }
    }
}
