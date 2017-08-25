package com.clique.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.clique.R;
import com.clique.adapter.DialogAdapter;
import com.clique.modle.Data;
import com.clique.modle.Pincode;
import com.clique.modle.ProfileData;
import com.clique.utils.CEditText;
import com.clique.utils.Constant;
import com.clique.utils.CustomTextView;
import com.clique.utils.EasyPermissions;
import com.clique.utils.InternetConnection;
import com.clique.utils.SessionManager;
import com.clique.utils.TakeImageFromCameraGallery;
import com.clique.utils.nowifiDialog;
import com.clique.webservice.ApiClient;
import com.clique.webservice.ApiInterface;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks, TakeImageFromCameraGallery.GetImage {

    private ImageView ivBack;
    private SimpleDraweeView ivProfileimage;
    private CustomTextView tvDone;
    private AlertDialog alertDialog;
    private int selectOptions = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 105;
    private String[] permission = {android.Manifest.permission_group.STORAGE};
    private TakeImageFromCameraGallery takeImageFromCameraGallery;
    private Data data;
    private File profilefle;
    private CEditText cetname, cetLsatName, cetHostalName, cetphoneno;
    private boolean isChange = false;
    private RequestBody fbody;
    private MultipartBody.Part filePart;
    private ProgressDialog progressDialog;
    private nowifiDialog nowifiDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Please wait..");
        takeImageFromCameraGallery = new TakeImageFromCameraGallery(ProfileActivity.this, ProfileActivity.this);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivProfileimage = (SimpleDraweeView) findViewById(R.id.iv_profile);
        cetname = (CEditText) findViewById(R.id.cet_name);
        cetLsatName = (CEditText) findViewById(R.id.cet_last_name);
        cetHostalName = (CEditText) findViewById(R.id.cet_address1);
        cetphoneno = (CEditText) findViewById(R.id.cet_phoneno);
        tvDone = (CustomTextView) findViewById(R.id.tv_done);
        ivBack.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        ivProfileimage.setOnClickListener(this);
        data = SessionManager.getSharedPreferenece(ProfileActivity.this);
        if (data != null) {
            cetphoneno.setText(data.phoneNo);
            if (data.name.split(" ").length > 1)
                cetLsatName.setText(data.name.split(" ")[1]);
            cetname.setText(data.name.split(" ")[0]);
            cetHostalName.setText(data.Hostelname);
            ivProfileimage.setImageURI(data.profilePic);
        }
        cetname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (data != null) {
                    if (!data.name.equals(charSequence.toString())) {
                        tvDone.setBackgroundResource(R.drawable.round_corner_green);
                        isChange = true;
                    }
                } else {
                    tvDone.setBackgroundResource(R.drawable.round_corner_green);
                    isChange = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cetLsatName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (data != null) {
                    if (data.name.split(" ").length > 1) {
                        if (!data.name.split(" ")[1].equals(charSequence.toString())) {
                            tvDone.setBackgroundResource(R.drawable.round_corner_green);
                            isChange = true;
                        }
                    } else {
                        tvDone.setBackgroundResource(R.drawable.round_corner_green);
                        isChange = true;
                    }
                } else {
                    isChange = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void callwebService() {
        if (InternetConnection.onCheckInternet(ProfileActivity.this)) {
            progressDialog.show();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            if (profilefle != null)
                filePart = MultipartBody.Part.createFormData("Profilepic", profilefle.getName(), RequestBody.create(MediaType.parse("image/*"), profilefle));

            // fbody = RequestBody.create(MediaType.parse("image/*"), profilefle);
            Call<ProfileData> call = apiService.updateProfile(data.userid + "", data.hostalid,data.phoneNo,cetname
                    .getText().toString() + " " + cetLsatName.getText().toString());
            call.enqueue(new Callback<ProfileData>() {
                @Override
                public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                    progressDialog.dismiss();
                    SessionManager sessionManager = new SessionManager(ProfileActivity.this);
                    Data data= response.body().data.get(0);
                    if(data!=null)
                    sessionManager.putSharedPreferenece(data.Name,data.userid,data.Hostelname,data.phoneno,data.hostalid+"",data.profilePic,
                            data.isGetSigupDisount,data.isGetInviteFriendDisount,data.isinhostelist);
                    if (getIntent().getIntExtra(Constant.TYPE, 0) == 1) {
                        Intent i = new Intent(ProfileActivity.this, OrderPlaceActivity.class);
                        startActivity(i);
                    } else if (getIntent().getIntExtra(Constant.TYPE, 0) == 2) {
                        Intent i = new Intent(ProfileActivity.this, OrderActivity.class);
                        startActivity(i);
                    }
                    finish();
                }

                @Override
                public void onFailure(Call<ProfileData> call, Throwable t) {
                    nowifiDialog1 = new nowifiDialog(ProfileActivity.this, new nowifiDialog.MyClickListner() {
                        @Override
                        public void onClick() {
                            progressDialog.show();
                            callwebService();
                            nowifiDialog1.dismiss();
                        }
                    }, "Something went wrong.");
                    nowifiDialog1.show();
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        } else {
            nowifiDialog1 = new nowifiDialog(ProfileActivity.this, new nowifiDialog.MyClickListner() {
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

    @Override
    public void onBackPressed() {
        if (getIntent().getIntExtra(Constant.TYPE, 0) == 2) {
            Intent i = new Intent(ProfileActivity.this, OrderActivity.class);
            startActivity(i);
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        if (view == ivBack) {
            onBackPressed();
        } else if (view == tvDone) {
            View view1 = this.getCurrentFocus();
            if (view1 != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            }
            if (!isChange)
                onBackPressed();
            else {
//                if (getIntent().getIntExtra(Constant.TYPE, 0) == 1) {
//                    Intent i = new Intent(ProfileActivity.this, OrderPlaceActivity.class);
//                    startActivity(i);
//                    finish();
//                } else if (getIntent().getIntExtra(Constant.TYPE, 0) == 2) {
//                    Intent i = new Intent(ProfileActivity.this, OrderActivity.class);
//                    startActivity(i);
//                    finish();
//                }
                callwebService();
            }
        } else if (view == ivProfileimage) {
            showAlerDailog();
        }
    }

    public void showAlerDailog() {
        final ArrayList<String> text = new ArrayList<>();
        text.add(getString(R.string.capture_from_camera));
        text.add(getString(R.string.choose_from_gallery));
        final ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_camera);
        icons.add(R.drawable.ic_gallary);
        ListAdapter adapter = new DialogAdapter(text, icons, ProfileActivity.this);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Add picture");
        builder1.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                selectOptions = item;
                EasyPermissions.requestPermissions(ProfileActivity.this, ProfileActivity.this, "Write external storeage", REQUEST_EXTERNAL_STORAGE, permission);
            }
        });
        AlertDialog alert1 = builder1.create();
        alert1.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        takeImageFromCameraGallery.dispatchTakePictureIntent(ProfileActivity.this, true, selectOptions);

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsPermanentlyDeclined(int requestCode, List<String> perms) {
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takeImageFromCameraGallery.onActivityForResult(requestCode, resultCode, data, ProfileActivity.this, ProfileActivity.this);
    }


    @Override
    public void onGetTakeImagefromCamera(Uri uri, File file) {
        profilefle = file;
        isChange = true;
        ivProfileimage.setImageURI(uri);
        tvDone.setBackgroundResource(R.drawable.round_corner_green);
    }
}
