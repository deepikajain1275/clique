package com.clique.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.clique.R;

public class CEditText extends AppCompatEditText {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public CEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public CEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init();
    }

    private void init() {
        try {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.font, defStyle, 0);

            String str = a.getString(R.styleable.font_fonttype);
            a.recycle();
            if (str != null) {
                this.setTypeface(Typeface.createFromAsset(context.getAssets(), str));
            } else {
                this.setTypeface(Typeface.createFromAsset(context.getAssets(), getResources().getString(R.string.regular)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
       /* Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/myfont.ttf");
        this.setTypeface(font);*/
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
    }
}