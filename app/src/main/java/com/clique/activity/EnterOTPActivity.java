package com.clique.activity;

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

public class EnterOTPActivity extends AppCompatActivity {

    private CustomTextView launch, tvResendCode;
    private CEditText cetPhoneNo;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private String phoneNo;
    private PhoneAuthProvider.ForceResendingToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);
        mVerificationId = getIntent().getStringExtra(Constant.ID);
        token = getIntent().getParcelableExtra(Constant.TOKEN);
        phoneNo=getIntent().getStringExtra(Constant.PHONENO);
        mAuth = FirebaseAuth.getInstance();
        init();
    }

    private void init() {
        tvResendCode = (CustomTextView) findViewById(R.id.tv_resendcode);
        launch = (CustomTextView) findViewById(R.id.tv_Lunch);
        cetPhoneNo = (CEditText) findViewById(R.id.et_phone_no);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(EnterOTPActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = task.getResult().getUser();
                                    Intent io = new Intent(EnterOTPActivity.this, ProfileActivity.class);
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
                                   PhoneAuthProvider.ForceResendingToken mtoken) {
                mVerificationId = verificationId;
                token = mtoken;
                //cetPhoneNo.setError("Code sent");
            }
        };

        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, cetPhoneNo.getText().toString());
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(EnterOTPActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = task.getResult().getUser();
                                    Intent io = new Intent(EnterOTPActivity.this, ProfileActivity.class);
                                    io.putExtra(Constant.TYPE, 2);
                                    startActivity(io);
                                    finish();
                                } else {
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        cetPhoneNo.setError("Invalid code.");
                                    }
                                }
                            }
                        });
            }
        });

        cetPhoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 6) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(cetPhoneNo.getWindowToken(), 0);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tvResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNo,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        EnterOTPActivity.this,               // Activity (for callback binding)
                        mCallbacks);

               /* Intent i = new Intent(EnterPincodeActivity.this, NotifimeActivity.class);
                startActivity(i);
                finish();*/
            }
        });

    }


}
