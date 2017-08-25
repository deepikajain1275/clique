package com.clique.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import com.clique.R;
import com.clique.utils.CustomTextView;

/**
 * Created by Deepika on 11-02-2017.
 */

public class ShippingFragment extends Fragment {
    private static final long SPLASH_SCREEN_TIME_IN_MILLIS = 5000;
    private SimpleDraweeView ivBrand;
    private CustomTextView tvDate;
    private CustomTextView tvMonth;
    private CustomTextView tvTime;
    private CustomTextView tvChange;
    private ImageView ivPacked;
    private View vPacked;
    private ImageView ivShipped;
    private View vShipped;
    private ImageView ivOutofdelivery;
    private View vOutofdeliver;
    private ImageView ivDeviered;
    private Handler handler,handler1,handler2;
    private Thread thread;
    private int counter = 0;
    private Thread thread1;
    private Thread thread2;

    private void findViews(View rootView) {
        tvDate = (CustomTextView) rootView.findViewById(R.id.tv_date);
        tvMonth = (CustomTextView) rootView.findViewById(R.id.tv_month);
        tvTime = (CustomTextView) rootView.findViewById(R.id.tv_time);
        tvChange = (CustomTextView) rootView.findViewById(R.id.tv_change);
        ivPacked = (ImageView) rootView.findViewById(R.id.iv_packed);
        vPacked = (View) rootView.findViewById(R.id.v_packed);
        ivShipped = (ImageView) rootView.findViewById(R.id.iv_shipped);
        vShipped = (View) rootView.findViewById(R.id.v_shipped);
        ivOutofdelivery = (ImageView) rootView.findViewById(R.id.iv_outofdelivery);
        vOutofdeliver = (View) rootView.findViewById(R.id.v_outofdeliver);
        ivDeviered = (ImageView) rootView.findViewById(R.id.iv_deviered);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shipping_fragment, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
        findViews(v);
        handler = new Handler();
        handler1 = new Handler();
        handler2 = new Handler();
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SPLASH_SCREEN_TIME_IN_MILLIS);
                    handler.post(new Runnable() {
                        public void run() {
                            if (counter == 0) {
                                counter++;
                                ivShipped.setImageResource(R.drawable.ic_round_purple);
                                vPacked.setBackgroundResource(R.color.colorblue);
                                thread1.start();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();
        thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SPLASH_SCREEN_TIME_IN_MILLIS);
                    handler1.post(new Runnable() {
                        public void run() {
                             if (counter == 1) {
                                counter++;
                                ivOutofdelivery.setImageResource(R.drawable.ic_round_purple);
                                vShipped.setBackgroundResource(R.color.colorblue);
                                thread2.start();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                }
            }
        };

        thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SPLASH_SCREEN_TIME_IN_MILLIS);
                    handler2.post(new Runnable() {
                        public void run() {
                             if (counter == 2) {
                                ivDeviered.setImageResource(R.drawable.ic_round_purple);
                                vOutofdeliver.setBackgroundResource(R.color.colorblue);
                            }
                        }
                    });
                } catch (InterruptedException e) {
                }
            }
        };

    }
}
