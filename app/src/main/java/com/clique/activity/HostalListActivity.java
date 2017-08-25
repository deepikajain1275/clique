package com.clique.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.clique.R;
import com.clique.adapter.ListviewAdapter;
import com.clique.adapter.SimpleDividerItemDecoration;
import com.clique.modle.AllBrandData;
import com.clique.modle.Data;
import com.clique.modle.HostalList;
import com.clique.modle.Pincode;
import com.clique.utils.Constant;
import com.clique.utils.InternetConnection;
import com.clique.utils.SessionManager;
import com.clique.utils.nowifiDialog;
import com.clique.webservice.ApiClient;
import com.clique.webservice.ApiInterface;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostalListActivity extends AppCompatActivity implements ListviewAdapter.OnClickList {

    private RecyclerView spinner;
    private ProgressDialog progressDialog;
    private AuthCallback callback;
    private int selectedpos = 0;
    private int check = 0;
    private ProgressBar pbLoader;
    private SessionManager sharedPreferences;
    private nowifiDialog nowifiDialog1;
    private ArrayList<HostalList> alHostalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostal_list);
        sharedPreferences= new SessionManager(this);
        spinner = (RecyclerView) findViewById(R.id.sp_hostal);
        pbLoader = (ProgressBar) findViewById(R.id.pb_loader);
        spinner.setLayoutManager(new LinearLayoutManager(this));
        spinner.addItemDecoration(new SimpleDividerItemDecoration(this));
        callwebService();
    }


    private void init(final int hostalid) {
        progressDialog = new ProgressDialog(HostalListActivity.this);
        progressDialog.setMessage("Please wait..");
        final DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setAuthTheme(R.style.CustomDigitsTheme);

        callback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                if (selectedpos ==alHostalList.size() - 1) {
                   callPhonewebService(phoneNumber);
                } else {
                    callSignupwebService(phoneNumber, hostalid);
                }
            }

            @Override
            public void failure(DigitsException error) {
                Toast.makeText(HostalListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };


    }

    @Override
    public void onClick(int pos) {
        selectedpos = pos;
        init(alHostalList.get(pos).hostalid);
        AuthConfig.Builder authConfigBuilder = new AuthConfig.Builder()
                .withAuthCallBack(callback)
                .withPhoneNumber("+91");
        Digits.authenticate(authConfigBuilder.build());
    }


    private void callwebService() {
        if (InternetConnection.onCheckInternet(HostalListActivity.this)) {
            final ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<AllBrandData> call = apiService.getHostalList();
            call.enqueue(new Callback<AllBrandData>() {
                @Override
                public void onResponse(Call<AllBrandData> call, Response<AllBrandData> response) {
                    pbLoader.setVisibility(View.GONE);
                    alHostalList.clear();
                    alHostalList.addAll(response.body().Data);
                    HostalList hostalList = new HostalList();
                    hostalList.hostalid = -1;
                    hostalList.hostalname = "None Of The Above";
                    alHostalList.add(hostalList);
                    ListviewAdapter listviewAdapter = new ListviewAdapter(alHostalList, HostalListActivity.this, HostalListActivity.this);
                    spinner.setAdapter(listviewAdapter);

                }

                @Override
                public void onFailure(Call<AllBrandData> call, Throwable t) {
                    nowifiDialog1 = new nowifiDialog(HostalListActivity.this, new nowifiDialog.MyClickListner() {
                        @Override
                        public void onClick() {
                            pbLoader.setVisibility(View.VISIBLE);
                            callwebService();
                            nowifiDialog1.dismiss();
                        }
                    }, "Something went wrong.");
                    nowifiDialog1.show();
                    Toast.makeText(HostalListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    pbLoader.setVisibility(View.GONE);
                }
            });
        } else {
            nowifiDialog1 = new nowifiDialog(HostalListActivity.this, new nowifiDialog.MyClickListner() {
                @Override
                public void onClick() {
                    nowifiDialog1.dismiss();
                    callwebService();
                }
            });
            nowifiDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            nowifiDialog1.show();
        }
    }

    private void callSignupwebService(final String phoneno, final int hostalid) {
        if (InternetConnection.onCheckInternet(HostalListActivity.this)) {
            progressDialog.show();
            final ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Pincode> call = apiService.phoneAndHostal(phoneno, hostalid);
            call.enqueue(new Callback<Pincode>() {
                @Override
                public void onResponse(Call<Pincode> call, Response<Pincode> response) {
                    progressDialog.dismiss();
                    Data data= response.body().data;
                    if(data!=null)
                    sharedPreferences.putSharedPreferenece(data.name,data.userid,data.Hostelname,data.phoneNo,data.hostalid+"",data.profilePic,
                            data.isGetSigupDisount,data.isGetInviteFriendDisount,data.isinhostelist);
                    Intent io = new Intent(HostalListActivity.this, ProfileActivity.class);
                    io.putExtra(Constant.TYPE, 2);
                    io.putExtra(Constant.DATA, response.body().data);
                    startActivity(io);
                    finish();
                }

                @Override
                public void onFailure(Call<Pincode> call, Throwable t) {
                    progressDialog.dismiss();
                    nowifiDialog1 = new nowifiDialog(HostalListActivity.this, new nowifiDialog.MyClickListner() {
                        @Override
                        public void onClick() {
                            progressDialog.show();
                            callSignupwebService(phoneno,hostalid);
                            nowifiDialog1.dismiss();
                        }
                    }, "Something went wrong.");
                    nowifiDialog1.show();
                    Toast.makeText(HostalListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            nowifiDialog1 = new nowifiDialog(HostalListActivity.this, new nowifiDialog.MyClickListner() {
                @Override
                public void onClick() {
                    nowifiDialog1.dismiss();
                    callSignupwebService(phoneno,hostalid);
                }
            });
            nowifiDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            nowifiDialog1.show();
        }
    }

    private void callPhonewebService(final String phoneno) {
        if (InternetConnection.onCheckInternet(HostalListActivity.this)) {
            progressDialog.show();
            final ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Pincode> call = apiService.phoneNo(phoneno);
            call.enqueue(new Callback<Pincode>() {
                @Override
                public void onResponse(Call<Pincode> call, Response<Pincode> response) {
                    progressDialog.dismiss();
                    Data data= response.body().data;
                    if(data!=null)
                        sharedPreferences.putSharedPreferenece(data.name,data.userid,data.Hostelname,data.phoneNo,data.hostalid+"",data.profilePic,
                                data.isGetSigupDisount,data.isGetInviteFriendDisount,data.isinhostelist);
                    Intent io = new Intent(HostalListActivity.this, EnterPincodeActivity.class);
                    io.putExtra(Constant.USERID, data.userid);
                    startActivity(io);
                    finish();
                }

                @Override
                public void onFailure(Call<Pincode> call, Throwable t) {
                    progressDialog.dismiss();
                    nowifiDialog1 = new nowifiDialog(HostalListActivity.this, new nowifiDialog.MyClickListner() {
                        @Override
                        public void onClick() {
                            progressDialog.show();
                            callPhonewebService(phoneno);
                            nowifiDialog1.dismiss();
                        }
                    }, "Something went wrong.");
                    nowifiDialog1.show();
                    Toast.makeText(HostalListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            nowifiDialog1 = new nowifiDialog(HostalListActivity.this, new nowifiDialog.MyClickListner() {
                @Override
                public void onClick() {
                    nowifiDialog1.dismiss();
                    callPhonewebService(phoneno);
                }
            });
            nowifiDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            nowifiDialog1.show();
        }
    }
}

