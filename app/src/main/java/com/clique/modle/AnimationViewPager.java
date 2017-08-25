package com.clique.modle;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import com.clique.R;
import com.clique.fragment.Brandfragment;
import com.clique.gallery.CustPagerTransformer;
import com.clique.utils.Constant;

/**
 * Created by Deepika on 24-02-2017.
 */

public class AnimationViewPager {
    ArrayList<Brand> arrayList;
    ArrayList<SubBrand> SubbrandarrayList;
    int type = 0;
    private Context mContext;
    private ViewPager brandRecyclerView;
    private FragmentManager fragmentManager;
    private List<Brandfragment> fragments = new ArrayList<>();

    public AnimationViewPager(ArrayList<Brand> arrayList, int type, Context mContext, ViewPager brandRecyclerView, FragmentManager fragmentManager) {
        this.arrayList = arrayList;
        this.type = type;
        this.mContext = mContext;
        this.brandRecyclerView = brandRecyclerView;
        this.fragmentManager = fragmentManager;
        setAdapter(arrayList, brandRecyclerView, type, fragmentManager, mContext);
    }

    public AnimationViewPager(ArrayList<SubBrand> arrayList, Context mContext, ViewPager brandRecyclerView, FragmentManager fragmentManager) {
        this.SubbrandarrayList = arrayList;
        this.mContext = mContext;
        this.brandRecyclerView = brandRecyclerView;
        this.fragmentManager = fragmentManager;
        setAdapter(arrayList, brandRecyclerView, fragmentManager, mContext);
    }

    public void setAdapter(ArrayList<Brand> arrayList, final ViewPager brandRecyclerView, int type, FragmentManager fm, Context mContext) {
        int width = 0;
        if (type == 0)
            width = (int) (((Activity) brandRecyclerView.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.4);
        else if (type == 1)
            width = (int) (((Activity) brandRecyclerView.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.5);
        else if (type == 2)
            width = (int) (((Activity) brandRecyclerView.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.5);


        for (int i = 0; i < arrayList.size(); i++) {
            Brandfragment frgment = new Brandfragment();
            Bundle b = new Bundle();
            b.putString(Constant.URL, arrayList.get(i).image);
            b.putInt(Constant.WIDTH, width);
           /* if (arrayList.get(i).name != null) {
                b.putString(Constant.NAME, arrayList.get(i).name);
            }
            if (arrayList.get(i).pad != 0) {
                b.putInt(Constant.TYPE, arrayList.get(i).pad);
            }*/
            frgment.setArguments(b);
            fragments.add(frgment);
        }
        Animation rvListAni = AnimationUtils.loadAnimation(mContext,
                R.anim.fade_in);
        brandRecyclerView.startAnimation(rvListAni);
        brandRecyclerView.setOffscreenPageLimit(fragments.size() - 1);

        ViewGroup.LayoutParams layoutParams = brandRecyclerView.getLayoutParams();
        layoutParams.width = width;
        if (brandRecyclerView.getParent() instanceof ViewGroup) {
            ViewGroup viewParent = ((ViewGroup) brandRecyclerView.getParent());
            viewParent.setClipChildren(false);
            brandRecyclerView.setClipChildren(false);
        }

        brandRecyclerView.setPageTransformer(false, new CustPagerTransformer(mContext, 0));
        Adapter adapter = new Adapter(fm, fragments, type);
        brandRecyclerView.setAdapter(adapter);
       /* if (type == 1) {
            brandRecyclerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int pos) {
                    for (int i = 0; i < fragments.size(); i++) {
                        if (i == pos) {
                            fragments.get(pos).setTextChange(true);
                        } else {
                            fragments.get(pos).setTextChange(false);
                        }

                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }*/
        brandRecyclerView.setCurrentItem(1);
    }

    public void setAdapter(ArrayList<SubBrand> arrayList, final ViewPager brandRecyclerView, FragmentManager fm, Context mContext) {
        int width = 0;
        width = (int) (((Activity) brandRecyclerView.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.5);
        for (int i = 0; i < arrayList.size(); i++) {
            Brandfragment frgment = new Brandfragment();
            Bundle b = new Bundle();
            b.putString(Constant.URL, arrayList.get(i).image);
            b.putInt(Constant.WIDTH, width);
           /* if (arrayList.get(i).name != null) {
                b.putString(Constant.NAME, arrayList.get(i).name);
            }
            if (arrayList.get(i).pad != 0) {
                b.putInt(Constant.TYPE, arrayList.get(i).pad);
            }*/
            frgment.setArguments(b);
            fragments.add(frgment);
        }
        Animation rvListAni = AnimationUtils.loadAnimation(mContext,
                R.anim.fade_in);
        brandRecyclerView.startAnimation(rvListAni);
        brandRecyclerView.setOffscreenPageLimit(fragments.size() - 1);

        ViewGroup.LayoutParams layoutParams = brandRecyclerView.getLayoutParams();
        layoutParams.width = width;
        if (brandRecyclerView.getParent() instanceof ViewGroup) {
            ViewGroup viewParent = ((ViewGroup) brandRecyclerView.getParent());
            viewParent.setClipChildren(false);
            brandRecyclerView.setClipChildren(false);
        }

        brandRecyclerView.setPageTransformer(false, new CustPagerTransformer(mContext, 0));
        Adapter adapter = new Adapter(fm, fragments, type);
        brandRecyclerView.setAdapter(adapter);
       /* if (type == 1) {
            brandRecyclerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int pos) {
                    for (int i = 0; i < fragments.size(); i++) {
                        if (i == pos) {
                            fragments.get(pos).setTextChange(true);
                        } else {
                            fragments.get(pos).setTextChange(false);
                        }

                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }*/
        brandRecyclerView.setCurrentItem(1);
    }

    public void onClick(int clickno) {
        if (clickno == 0) {
            TranslateAnimation scaleani = new TranslateAnimation(0, 0, 0, mContext.getResources().getDimensionPixelSize(R.dimen.dp_100));
            scaleani.setFillAfter(true);
            scaleani.setDuration(1500);
            brandRecyclerView.startAnimation(scaleani);
            final int height = brandRecyclerView.getHeight();
            scaleani.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    brandRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            final FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) brandRecyclerView.getLayoutParams();
                            lp.setMargins(0, (int) mContext.getResources().getDimensionPixelSize(R.dimen.dp_100), 0, 0);
                            brandRecyclerView.setLayoutParams(lp);
                            brandRecyclerView.clearAnimation();
                        }
                    });
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.8f);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float endScale = Float.parseFloat(valueAnimator.getAnimatedValue() + "");
                    ViewGroup.LayoutParams layoutParams = brandRecyclerView.getLayoutParams();
                    layoutParams.width = (int) (((Activity) brandRecyclerView.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * endScale * 5.8f);
                    layoutParams.height = (int) (height * endScale * .95);
                    brandRecyclerView.setLayoutParams(layoutParams);
                }
            });

            animator.setDuration(2000);
            animator.start();
        } else if (clickno == 1) {
            for (int i = 0; i < fragments.size(); i++) {
                fragments.get(i).setImage();
            }
            TranslateAnimation scaleani = new TranslateAnimation(0, 0, 0, mContext.getResources().getDimensionPixelSize(R.dimen.dp_280));
            scaleani.setFillAfter(true);
            scaleani.setDuration(1500);
            brandRecyclerView.startAnimation(scaleani);
            final int height = brandRecyclerView.getHeight();
            scaleani.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    brandRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            final FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) brandRecyclerView.getLayoutParams();
                            lp.setMargins(0, (int) mContext.getResources().getDimensionPixelSize(R.dimen.dp_280), 0, 0);
                            brandRecyclerView.setLayoutParams(lp);
                            brandRecyclerView.clearAnimation();
                        }
                    });
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.6f);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float endScale = Float.parseFloat(valueAnimator.getAnimatedValue() + "");
                    ViewGroup.LayoutParams layoutParams = brandRecyclerView.getLayoutParams();
                    layoutParams.width = (int) (((Activity) brandRecyclerView.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * endScale * 7.7f);
                    layoutParams.height = (int) (height * endScale);
                    brandRecyclerView.setLayoutParams(layoutParams);
                }
            });

            animator.setDuration(2000);
            animator.start();
        } else if (clickno == 2) {

        }
    }

    class Adapter extends FragmentStatePagerAdapter {
        List<Brandfragment> brandfragments;
        private int type;

        public Adapter(FragmentManager fm, List<Brandfragment> fragment, int type) {
            super(fm);
            brandfragments = fragment;
            this.type = type;
        }

        @Override
        public Fragment getItem(int position) {
            return brandfragments.get(position);
        }

        @Override
        public int getCount() {
            return brandfragments.size();
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void setType(int type) {
            this.type = type;
            notifyDataSetChanged();
        }
    }

    public void notifiData(ArrayList<BrandModle> arrayList, final ViewPager brandRecyclerView, int type, FragmentManager fm, Context mContext) {
        fragments.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            Brandfragment frgment = new Brandfragment();
            Bundle b = new Bundle();
            b.putInt(Constant.URL, arrayList.get(i).alBrand);
            if (arrayList.get(i).name != null) {
                b.putString(Constant.NAME, arrayList.get(i).name);
            }
            if (arrayList.get(i).pad != 0) {
                b.putInt(Constant.TYPE, arrayList.get(i).pad);
            }
            frgment.setArguments(b);
            fragments.add(frgment);
        }
        Animation rvListAni = AnimationUtils.loadAnimation(mContext,
                R.anim.fade_in);
        brandRecyclerView.startAnimation(rvListAni);
        brandRecyclerView.setOffscreenPageLimit(fragments.size() - 1);

            /*ViewGroup.LayoutParams layoutParams = brandRecyclerView.getLayoutParams();
            if (type == 0)
                layoutParams.width = (int) (((Activity) brandRecyclerView.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * 6);
            else if (type == 1)
                layoutParams.width = (int) (((Activity) brandRecyclerView.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.5);
            else if (type == 2)
                layoutParams.width = (int) (((Activity) brandRecyclerView.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.5);

            if (brandRecyclerView.getParent() instanceof ViewGroup) {
                ViewGroup viewParent = ((ViewGroup) brandRecyclerView.getParent());
                viewParent.setClipChildren(false);
                brandRecyclerView.setClipChildren(false);
            }

            brandRecyclerView.setPageTransformer(false, new CustPagerTransformer(mContext, 0));*/

        brandRecyclerView.getAdapter().notifyDataSetChanged();

        //    brandRecyclerView.setCurrentItem(1);
    }
}



