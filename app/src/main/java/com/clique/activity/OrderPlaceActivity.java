package com.clique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;
import com.clique.R;
import com.clique.fragment.OrderFragment;
import com.clique.fragment.ShippingFragment;
import com.clique.utils.CirclePageIndicator;

public class OrderPlaceActivity extends AppCompatActivity implements MaterialIntroListener{

    private static final long SPLASH_SCREEN_TIME_IN_MILLIS = 2500;
    private ViewPager mViewPager;
    private CirclePageIndicator circle;
    private Handler handler;
    private Thread thread;
    private ImageView ivSetting;
    private SimpleDraweeView ivProfile;
    private ImageView ivuserrImage;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_place);
        init();
        //FullScreencall();
    }

    @Override
    public void onUserClicked(String materialIntroViewId) {

    }

    public void showIntro(View view, String id, String text, FocusGravity focusGravity, int imageid, Focus focus, ShapeType shape, int delay) {
        new MaterialIntroView.Builder(this)
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
    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.vp_order);
        circle = (CirclePageIndicator) findViewById(R.id.cp_page);
        setupViewPager(mViewPager);
        ivSetting=(ImageView)findViewById(R.id.iv_setting);
        ivProfile=(SimpleDraweeView)findViewById(R.id.iv_profile);
        ivuserrImage=(ImageView)findViewById(R.id.iv_userimage);

        ivuserrImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(OrderPlaceActivity.this, InviteActivity.class);
                    startActivity(i);
            }
        });

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrderPlaceActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrderPlaceActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });


        handler = new Handler();
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SPLASH_SCREEN_TIME_IN_MILLIS);
                    handler.post(new Runnable() {
                        public void run() {
                            showIntro(circle, "status", "Check Status Here", FocusGravity.CENTER, R.drawable.ic_discount, Focus.ALL, ShapeType.RECTANGLE, 1000);

                        }
                    });
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(1);
        Adapter adapter = new Adapter(getSupportFragmentManager());
        OrderFragment frgment = new OrderFragment();
        adapter.addFragment(frgment);
        ShippingFragment shippingfrgment = new ShippingFragment();
        adapter.addFragment(shippingfrgment);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        circle.setViewPager(viewPager);
    }

    class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
            mFragments.clear();
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void changePage(int page)
    {
        mViewPager.setCurrentItem(page);
    }
}
