package com.clique.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.clique.R;

public class DialogAdapter extends ArrayAdapter {

    private int pos = 1;
    private List<Integer> images;
    private List<String> text;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) LayoutInflater
                    .from(getContext());
            view = inflater.inflate(R.layout.row_dialog_item, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_text);
        textView.setText(text.get(position));
        ImageView imageView=(ImageView)view.findViewById(R.id.iv_image);
        imageView.setImageResource(images.get(position));
/*
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(images.get(position), 0, 0, 0);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(images.get(position), 0, 0, 0);
        }
        textView.setCompoundDrawablePadding(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getContext().getResources().getDisplayMetrics()));
        textView.setTextColor(parent.getResources().getColor(R.color.gray));
       // textView.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,5, getContext().getResources().getDisplayMetrics()));
        //textView.setMinHeight(0);*/
        return view;
    }


    public DialogAdapter(List<String> text, List<Integer> images, Context context) {
        super(context, android.R.layout.select_dialog_item, text);
        this.text = text;
        this.images = images;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}

