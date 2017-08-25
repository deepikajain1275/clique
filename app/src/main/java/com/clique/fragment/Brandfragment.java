package com.clique.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import com.clique.R;
import com.clique.utils.Constant;
import com.clique.utils.CustomTextView;

/**
 * Created by Deepika on 11-02-2017.
 */

public class Brandfragment extends Fragment {
    private SimpleDraweeView ivImage;
    private LinearLayout flMain;
    private CustomTextView textPad;
    ImageView ivWings, ivPad;
    private CustomTextView textmodify;
    private CustomTextView tv_subbrandname;
    private ImageClick imageClick;

    public Brandfragment() {

    }

    public Brandfragment(ImageClick imageClick) {
        this.imageClick = imageClick;
    }

    public interface ImageClick {
        void onImageClick(int pos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.row_brand, container, false);
        ivImage = (SimpleDraweeView) v.findViewById(R.id.iv_brand);
        flMain = (LinearLayout) v.findViewById(R.id.fl_main);
        ivWings = (ImageView) v.findViewById(R.id.iv_wings);
        textmodify = (CustomTextView) v.findViewById(R.id.tv_modifiy);
        ivPad = (ImageView) v.findViewById(R.id.iv_pad);
        textPad = (CustomTextView) v.findViewById(R.id.tv_noofpad);
        tv_subbrandname = (CustomTextView) v.findViewById(R.id.tv_subbrandname);
        String image = getArguments().getString(Constant.URL);
        int type = getArguments().getInt(Constant.TYPE);
        int width = getArguments().getInt(Constant.WIDTH);
        String name = getArguments().getString(Constant.NAME);
        tv_subbrandname.setSelected(true);
        ivImage.getLayoutParams().width = width;
        ivImage.setImageURI(image);
        if (name != null) {
            tv_subbrandname.setVisibility(View.VISIBLE);
            tv_subbrandname.setText(name);
        }
        if (type != 0) {
            /*flMain.setVisibility(View.VISIBLE);
            textPad.setText(type + "");
            if (getArguments().getString(Constant.ISWING).equals("Yes")) {
                ivWings.setVisibility(View.VISIBLE);
                ivPad.setImageResource(R.drawable.ic_pad);
            } else {
                ivWings.setVisibility(View.GONE);
                ivPad.setImageResource(R.drawable.ic_pads_withoutwings);
            }*/
        } else if (type == -1) {
            //textmodify.setVisibility(View.VISIBLE);
        }

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageClick!=null)
                imageClick.onImageClick(0);
            }
        });

        return v;
    }

    public void setImage() {
        if (ivImage != null)
            ivImage.setVisibility(View.GONE);
    }

    public void setTextChange(boolean type) {
        if (tv_subbrandname != null) {
            if (type)
                tv_subbrandname.setVisibility(View.GONE);
            else
                tv_subbrandname.setVisibility(View.VISIBLE);
        }
    }
}
