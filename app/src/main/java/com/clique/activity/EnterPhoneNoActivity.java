package com.clique.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.clique.R;
import com.clique.utils.CEditText;
import com.clique.utils.Constant;
import com.clique.utils.CustomTextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterPhoneNoActivity extends AppCompatActivity {

    private CustomTextView launch;
    private CEditText cetPhoneNo;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone_no);
        init();
    }

    private void init() {
        launch = (CustomTextView) findViewById(R.id.tv_Lunch);
        cetPhoneNo = (CEditText) findViewById(R.id.et_phone_no);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.getPhoneNumber();
            Intent io = new Intent(EnterPhoneNoActivity.this, ProfileActivity.class);
            io.putExtra(Constant.TYPE, 2);
            startActivity(io);
            finish();
        }
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(EnterPhoneNoActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = task.getResult().getUser();
                                    Intent io = new Intent(EnterPhoneNoActivity.this, ProfileActivity.class);
                                    io.putExtra(Constant.TYPE, 2);
                                    startActivity(io);
                                    finish();
                                } else {
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        cetPhoneNo.setError("Error");
                                    }
                                }
                            }
                        });
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    cetPhoneNo.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                Intent io = new Intent(EnterPhoneNoActivity.this, EnterOTPActivity.class);
                io.putExtra(Constant.ID, mVerificationId);
                io.putExtra(Constant.TOKEN, token);
                io.putExtra(Constant.PHONENO, "+91"+cetPhoneNo.getText().toString());
                startActivity(io);
                finish();
                //cetPhoneNo.setError("Code sent");
            }
        };

        cetPhoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 10) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(cetPhoneNo.getWindowToken(), 0);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkGooglePlayServices()) {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91"+cetPhoneNo.getText().toString(),        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            EnterPhoneNoActivity.this,               // Activity (for callback binding)
                            mCallbacks);
                }

               /* Intent i = new Intent(EnterPincodeActivity.this, NotifimeActivity.class);
                startActivity(i);
                finish();*/
            }
        });
    }


    private boolean checkGooglePlayServices() {
        final int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            // ask user to update google play services.
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, 1);
            dialog.show();
            return false;
        } else {
            // google play services is updated.
            //your code goes here...
            return true;
        }
    }
}
