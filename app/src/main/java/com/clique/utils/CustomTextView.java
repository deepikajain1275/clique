package com.clique.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.clique.R;

public class CustomTextView extends TextView {

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

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

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressWarnings("unused")
    private void internalInit(Context context, AttributeSet attrs) {

    }
}