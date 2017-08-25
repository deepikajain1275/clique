package com.clique.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.clique.R;
import com.clique.utils.Constant;
import com.clique.utils.CustomTextView;

/**
 * Created by Deepika on 11-02-2017.
 */

public class TutFragment extends Fragment {
    private CustomTextView tvText1, tvText2;
    private ImageView ivImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tut_fragemt, container, false);
        tvText1 = (CustomTextView) v.findViewById(R.id.tv_text1);
        tvText2 = (CustomTextView) v.findViewById(R.id.tv_text2);
        ivImage = (ImageView) v.findViewById(R.id.iv_image);
        int pos = getArguments().getInt(Constant.POSTION);
        if (pos == 0) {
            tvText1.setText(R.string.saygoodbye);
            tvText2.setText(R.string.embrasment);
            ivImage.setImageResource(R.drawable.ic_tut1);
        } else if (pos == 1) {
            tvText1.setText(R.string.easytofast);
            tvText2.setText(R.string.atyourfootsteps);
            ivImage.setImageResource(R.drawable.ic_tut2);
        } else {
            tvText1.setText(R.string.save);
            tvText2.setText(R.string.whenfriendbuywithyou);
            ivImage.setImageResource(R.drawable.ic_tut3);

        }
        return v;
    }
}
