package com.clique.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;

import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;
import com.clique.R;
import com.clique.activity.CreateActivity;
import com.clique.modle.Data;
import com.clique.utils.Constant;
import com.clique.utils.HorizontalPicker;
import com.clique.utils.SessionManager;

/**
 * Created by Deepika on 11-02-2017.
 */

public class OrderHistoryFragment extends Fragment implements MaterialIntroListener {
    private SimpleDraweeView ivBrand;
    private HorizontalPicker rlSize;
    private LinearLayout llDiscount;
    private String DISCOUNT = "Discount";
    private Data data;

    int type = 0;
    HashMap<String, String> map = new HashMap<>();

    public OrderHistoryFragment(int type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_history_fragment, container, false);
        ivBrand = (SimpleDraweeView) v.findViewById(R.id.iv_brand);
        rlSize = (HorizontalPicker) v.findViewById(R.id.rl_size);
        llDiscount = (LinearLayout) v.findViewById(R.id.ll_discount);
        data = SessionManager.getSharedPreferenece(getContext());

        if (data.isGetSigupDisount == 0)
            showIntro(llDiscount, DISCOUNT, "Sign Up Discount", FocusGravity.CENTER, R.drawable.ic_discount, Focus.ALL, ShapeType.RECTANGLE, 1000);


        SessionManager sessionManager = new SessionManager(getActivity());
        if (type == 0) {
            map = sessionManager.get(getActivity());
        } else {
            map = sessionManager.getData2(getActivity());
        }
        ivBrand.setImageURI(map.get(Constant.IMAGE).replace(" ", "%20"));
        ivBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CreateActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return v;
    }

    @Override
    public void onUserClicked(String materialIntroViewId) {
    }

    public void showIntro(View view, String id, String text, FocusGravity focusGravity, int imageid, Focus focus, ShapeType shape, int delay) {
        new MaterialIntroView.Builder(getActivity())
                .enableDotAnimation(true)
                .setFocusGravity(focusGravity).setMaskColor(getResources().getColor(R.color.blacktransprant))
                .setFocusType(focus).setInfoTextSize(20)
                .setDelayMillis(delay)
                .enableFadeAnimation(true).setImageView(imageid)
                .setInfoText(text)
                .setTarget(view).setTextColor(getResources().getColor(R.color.colorblue))
                .setListener(this).setShape(shape)
                .setUsageId(id) //THIS SHOULD BE UNIQUE ID
                .show();// .performClick(true)
    }
}
