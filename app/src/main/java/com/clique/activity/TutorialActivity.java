package com.clique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.clique.R;
import com.clique.fragment.TutFragment;
import com.clique.utils.CirclePageIndicator;
import com.clique.utils.Constant;
import com.clique.utils.CustomTextView;
import com.clique.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class TutorialActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private CirclePageIndicator circle;
    private int pos = 0;
    private CustomTextView tvCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FullScreencall();
        setContentView(R.layout.activity_tutorial);
        Init();
       /* View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == View.VISIBLE) {
                    if (Build.VERSION.SDK_INT < 19) {
                        View v = TutorialActivity.this.getWindow().getDecorView();
                        v.setSystemUiVisibility(View.GONE);
                    } else {
                        getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                        View.SYSTEM_UI_FLAG_IMMERSIVE
                        );

                    }
                } else {
                }
            }
        });*/
    }

    @Override
    protected void onResume() {
        // FullScreencall();
        super.onResume();
    }


   /* public void FullScreencall() {
        if (Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for higher api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions =
                    View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);
        }

    }
*/

    private void Init() {
        SessionManager sessionManager = new SessionManager(TutorialActivity.this);
        sessionManager.putTut(1);
        tvCreate = (CustomTextView) findViewById(R.id.tv_create);
        mViewPager = (ViewPager) findViewById(R.id.vp_tut);
        circle = (CirclePageIndicator) findViewById(R.id.cp_page);
        setupViewPager(mViewPager);
        tvCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TutorialActivity.this, CreateActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(2);
        Adapter adapter = new Adapter(getSupportFragmentManager());
        TutFragment frgment;
        for (int i = 0; i < 3; i++) {
            frgment = new TutFragment();
            Bundle b = new Bundle();
            b.putInt(Constant.POSTION, i);
            frgment.setArguments(b);
            adapter.addFragment(frgment);
        }

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
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
}
