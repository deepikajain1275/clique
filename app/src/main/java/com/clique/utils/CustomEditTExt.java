package com.clique.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.clique.R;

public class CustomEditTExt extends EditText {

    public CustomEditTExt(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public CustomEditTExt(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        if (!isInEditMode()) {
            try {
                TypedArray a = context.obtainStyledAttributes(attrs,
                        R.styleable.font, defStyle, 0);

                String str = a.getString(R.styleable.font_fonttype);
                a.recycle();
                if (str != null) {
                    setTypeface(Typeface.createFromAsset(context.getAssets(), str));
                } else {
                    setTypeface(Typeface.createFromAsset(context.getAssets(), getResources().getString(R.string.regular)));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}