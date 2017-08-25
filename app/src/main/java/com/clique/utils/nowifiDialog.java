package com.clique.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.clique.R;

/**
 * Created by Deepika on 18-05-2017.
 */

public class nowifiDialog extends Dialog {
    private final MyClickListner myclick;
    private CustomTextView tvCreate;
    private CustomTextView tvDes;
    private String des = null;

    public interface MyClickListner {
        public void onClick();
    }

    public nowifiDialog(Context context, MyClickListner myClickListner) {
        super(context);
       getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.myclick = myClickListner;
    }

    public nowifiDialog(Context context, MyClickListner myClickListner, String des) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.des = des;
        this.myclick = myClickListner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_nowifi);
        setCancelable(false);
        int diviedid = getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = findViewById(diviedid);
        if (divider != null)
            divider.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
        tvCreate = (CustomTextView) findViewById(R.id.tv_create);
        tvDes = (CustomTextView) findViewById(R.id.tv_des);
        if (!TextUtils.isEmpty(des))
            tvDes.setText(des);
        tvCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myclick.onClick();
            }
        });
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
    }
}
