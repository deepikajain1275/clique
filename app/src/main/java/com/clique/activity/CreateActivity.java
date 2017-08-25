package com.clique.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.clique.R;
import com.clique.fragment.Brandfragment;
import com.clique.gallery.CustPagerTransformer;
import com.clique.modle.AllBrandData;
import com.clique.modle.Brand;
import com.clique.modle.Data;
import com.clique.modle.FinalProduct;
import com.clique.modle.Product;
import com.clique.modle.Size;
import com.clique.modle.SubBrand;
import com.clique.utils.Constant;
import com.clique.utils.CustomTextView;
import com.clique.utils.HorizontalPicker;
import com.clique.utils.InternetConnection;
import com.clique.utils.SessionManager;
import com.clique.utils.nowifiDialog;
import com.clique.webservice.ApiClient;
import com.clique.webservice.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    private CustomTextView tvNext, tvChooseBrand;
    private Animation textAni, textAniUp;
    private ViewPager rvList, rvsubbrand, rvsubsubbrand;
    private int brand = 0;
    private int click = 0;
    private ArrayList<Brand> brandarrayList;
    private HorizontalPicker rvSize;
    private View v1, v2;
    private ProgressBar pbLoader;
    private ArrayList<Brandfragment> brandFragments = new ArrayList<>();
    private ArrayList<Brandfragment> subbrandFragment = new ArrayList<>();
    private ArrayList<Brandfragment> productFragment = new ArrayList<>();
    private int subbrand = 0;
    private int product = 0;
    private int size=1;
    private int item = 1;
    private int type = 0;
    private SessionManager sessionManager;
    private nowifiDialog nowifiDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        init();
        //FullScreencall();
        callwebService();
        rvList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                brand = position;
                if (click == 1) {
                    rvsubbrand.removeAllViews();
                    rvsubbrand.setAdapter(null);
                    fillSubBrandfragment((ArrayList<SubBrand>) brandarrayList.get(brand).subBrand);
                    setAdapter(subbrandFragment, rvsubbrand, getSupportFragmentManager(), 1);
                } else if (click == 2) {
                    Intent i = new Intent(CreateActivity.this, CreateActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rvsubsubbrand.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                product = position;
//                String[] size = new String[brandarrayList.get(brand).subBrand.get(subbrand).products.get(position).size.size()];
//                for (int i = 0; i < brandarrayList.get(brand).subBrand.get(subbrand).products.get(position).size.size(); i++)
//                    size[i] = brandarrayList.get(brand).subBrand.get(subbrand).products.get(position).size.get(i).size;
//                rvSize.setValues(size);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rvsubbrand.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                subbrand = position;
                if (click == 2) {
                    rvsubsubbrand.removeAllViews();
                    rvsubsubbrand.setAdapter(null);
                    if(brandarrayList.get(brand).subBrand.get(subbrand).size!=null) {
                        fillsizelist(brandarrayList.get(brand).subBrand.get(subbrand).size);
                        fillProductfragment((ArrayList<Product>) brandarrayList.get(brand).subBrand.get(subbrand).size.get(size).products);
                        setAdapter(productFragment, rvsubsubbrand, getSupportFragmentManager(), 2);
                    }
//                    if (productFragment.size() > 1) {
//                        String[] size = new String[brandarrayList.get(brand).subBrand.get(subbrand).products.get(1).size.size()];
//                        for (int i = 0; i < brandarrayList.get(brand).subBrand.get(subbrand).products.get(1).size.size(); i++)
//                            size[i] = brandarrayList.get(brand).subBrand.get(subbrand).products.get(1).size.get(i).size;
//                        rvSize.setValues(size);
//                        if (size.length == 1)
//                            rvSize.setSelectedItem(0);
//                        else
//                            rvSize.setSelectedItem(1);
//                    } else if (productFragment.size() == 1) {
//                        String[] size = new String[brandarrayList.get(brand).subBrand.get(subbrand).products.get(0).size.size()];
//                        for (int i = 0; i < brandarrayList.get(brand).subBrand.get(subbrand).products.get(0).size.size(); i++)
//                            size[i] = brandarrayList.get(brand).subBrand.get(subbrand).products.get(0).size.get(i).size;
//                        rvSize.setValues(size);
//                        if (size.length == 1)
//                            rvSize.setSelectedItem(0);
//                        else
//                            rvSize.setSelectedItem(1);
//                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rvSize.setOnItemSelectedListener(new HorizontalPicker.OnItemSelected() {
            @Override
            public void onItemSelected(int index) {
                size= index;
                fillProductfragment((ArrayList<Product>) brandarrayList.get(brand).subBrand.get(subbrand).size.get(size).products);
                setAdapter(productFragment, rvsubsubbrand, getSupportFragmentManager(), 2);
            }
        });
    }

    private void callwebService() {
        if (InternetConnection.onCheckInternet(CreateActivity.this)) {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<AllBrandData> call = apiService.getCreateModule();
            call.enqueue(new Callback<AllBrandData>() {
                @Override
                public void onResponse(Call<AllBrandData> call, Response<AllBrandData> response) {
                    brandarrayList = (ArrayList<Brand>) response.body().data;
                    fillBrandfragment(brandarrayList);
                    setAdapter(brandFragments, rvList, getSupportFragmentManager(), 0);
                    pbLoader.setVisibility(View.GONE);
                    tvNext.setEnabled(true);
                    tvNext.setClickable(true);
                    tvNext.setVisibility(View.VISIBLE);
                    tvNext.startAnimation(textAni);
                }

                @Override
                public void onFailure(Call<AllBrandData> call, Throwable t) {
                    nowifiDialog1 = new nowifiDialog(CreateActivity.this, new nowifiDialog.MyClickListner() {
                        @Override
                        public void onClick() {
                            pbLoader.setVisibility(View.VISIBLE);
                            callwebService();
                            nowifiDialog1.dismiss();
                        }
                    }, "Something went wrong.");
                    // nowifiDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //nowifiDialog1.getWindow().
                    nowifiDialog1.show();
                    Toast.makeText(CreateActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    pbLoader.setVisibility(View.GONE);
                    tvNext.setEnabled(false);
                    tvNext.setClickable(false);
                }
            });
        } else {
            nowifiDialog1 = new nowifiDialog(CreateActivity.this, new nowifiDialog.MyClickListner() {
                @Override
                public void onClick() {
                    nowifiDialog1.dismiss();
                    callwebService();
                }
            });
            nowifiDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            nowifiDialog1.show();
        }
    }

    private void init() {
        sessionManager = new SessionManager(CreateActivity.this);
        type = getIntent().getIntExtra(Constant.TYPE, 0);
        pbLoader = (ProgressBar) findViewById(R.id.pb_loader);
        tvChooseBrand = (CustomTextView) findViewById(R.id.tv_chosseurbrand);
        tvNext = (CustomTextView) findViewById(R.id.tv_create);
        tvNext.setEnabled(false);
        tvNext.setClickable(false);
        rvsubsubbrand = (ViewPager) findViewById(R.id.viewpager_subbrand);
        rvsubbrand = (ViewPager) findViewById(R.id.viewpager_brand);
        rvList = (ViewPager) findViewById(R.id.viewpager);
        v1 = findViewById(R.id.v_view1);
        v2 = findViewById(R.id.v_view2);
        textAni = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.text_anim_translate);
        textAniUp = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.text_anim_up_translate);
        rvSize = (HorizontalPicker) findViewById(R.id.rl_size);
        tvNext.setOnClickListener(this);
        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == View.VISIBLE) {
                    if (Build.VERSION.SDK_INT < 19) {
                        View v = CreateActivity.this.getWindow().getDecorView();
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
        });
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
    }*/

   /* public void FullScreencall() {
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
           *//* decorView.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        if(Build.VERSION.SDK_INT < 19){
                            decorView.setSystemUiVisibility(View.GONE);
                        } else {
                            decorView.setSystemUiVisibility(
                                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                        }
                    }
                }
            });*//*
    }*/

    @Override
    public void onClick(View view) {
        if (click == 0) {
            tvChooseBrand.setAnimation(textAniUp);
            tvChooseBrand.setVisibility(View.GONE);
            fillSubBrandfragment((ArrayList<SubBrand>) brandarrayList.get(brand).subBrand);
            setAdapter(subbrandFragment, rvsubbrand, getSupportFragmentManager(), 1);
            SlideToAbove(rvList, (int) getResources().getDimension(R.dimen.d_25));
            onClick(click);
            click = 1;
            rvsubbrand.setVisibility(View.VISIBLE);
            Animation rvListAni = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.fade_in);
            rvsubbrand.startAnimation(rvListAni);
        } else if (click == 1) {
            if(brandarrayList.get(brand).subBrand.get(subbrand).size!=null) {
                fillsizelist(brandarrayList.get(brand).subBrand.get(subbrand).size);
                fillProductfragment((ArrayList<Product>) brandarrayList.get(brand).subBrand.get(subbrand).size.get(size).products);
                setAdapter(productFragment, rvsubsubbrand, getSupportFragmentManager(), 2);
                onClick(click);
                SlideToAbove(rvsubbrand, (int) getResources().getDimension(R.dimen.d_35));
                click = 2;
                rvsubsubbrand.setVisibility(View.VISIBLE);
                Animation rvListAni = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade_in);
                rvsubsubbrand.startAnimation(rvListAni);
                rvSize.setVisibility(View.VISIBLE);
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);
            }

//            fillProductfragment((ArrayList<Product>) brandarrayList.get(brand).subBrand.get(subbrand).products);
//            setAdapter(productFragment, rvsubsubbrand, getSupportFragmentManager(), 2);
//            if (productFragment.size() > 1) {
//                String[] size = new String[brandarrayList.get(brand).subBrand.get(subbrand).products.get(1).size.size()];
//                for (int i = 0; i < brandarrayList.get(brand).subBrand.get(subbrand).products.get(1).size.size(); i++)
//                    size[i] = brandarrayList.get(brand).subBrand.get(subbrand).products.get(1).size.get(i).size;
//                rvSize.setValues(size);
//            } else if (productFragment.size() == 1) {
//                String[] size = new String[brandarrayList.get(brand).subBrand.get(subbrand).products.get(0).size.size()];
//                for (int i = 0; i < brandarrayList.get(brand).subBrand.get(subbrand).products.get(0).size.size(); i++)
//                    size[i] = brandarrayList.get(brand).subBrand.get(subbrand).products.get(0).size.get(i).size;
//                rvSize.setValues(size);
//            }

        } else if (click == 2) {
            if (type == 0) {
                //sessionManager.putData(brandarrayList.get(brand).subBrand.get(subbrand).products.get(product).image, brandarrayList.get(brand).subBrand.get(subbrand).products.get(product).productName, brandarrayList.get(brand).subBrand.get(subbrand).products.get(product).iD, 100 + "", brandarrayList.get(brand).subBrand.get(subbrand).products.get(product).size.get(rvSize.getSelectedItem()).size);
                ArrayList<Product> arrayList = new ArrayList<>();
                arrayList.add(brandarrayList.get(brand).subBrand.get(subbrand).size.get(size).products.get(product));
                FinalProduct finalProduct = new FinalProduct();
                finalProduct.alFinalProduct = arrayList;
                sessionManager.putArrayListOrder(finalProduct);

                Intent i = new Intent(CreateActivity.this, HeavyFlowActivity.class);
                i.putExtra(Constant.PRICE, 300);
                i.putExtra(Constant.IMAGE, brandarrayList.get(brand).subBrand.get(subbrand).size.get(size).products.get(product).image);
                i.putExtra(Constant.FLOW, brandarrayList.get(brand).subBrand.get(subbrand).FlowType);
                startActivity(i);
                finish();
            } else {
                // sessionManager.putData1(brandarrayList.get(brand).subBrand.get(subbrand).products.get(product).image, brandarrayList.get(brand).subBrand.get(subbrand).products.get(product).productName, brandarrayList.get(brand).subBrand.get(subbrand).products.get(product).iD, 100 + "", brandarrayList.get(brand).subBrand.get(subbrand).products.get(product).size.get(rvSize.getSelectedItem()).size);

                ArrayList<Product> arrayList = sessionManager.getArrayLisOrder(CreateActivity.this).alFinalProduct;
                arrayList.add(brandarrayList.get(brand).subBrand.get(subbrand).size.get(size).products.get(product));
                FinalProduct finalProduct = new FinalProduct();
                finalProduct.alFinalProduct = arrayList;
                sessionManager.putArrayListOrder(finalProduct);
                final Data data = sessionManager.getSharedPreferenece(CreateActivity.this);
                if (data == null) {
                    Intent i = new Intent(CreateActivity.this, PincodeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent io = new Intent(CreateActivity.this, OrderActivity.class);
                    startActivity(io);
                    finish();
                }
            }
        }
    }

    public void fillBrandfragment(ArrayList<Brand> arrayList) {
        int width = (int) (getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.4);
        for (int i = 0; i < arrayList.size(); i++) {
            Brandfragment frgment = new Brandfragment();
            Bundle b = new Bundle();
            b.putString(Constant.URL, arrayList.get(i).image.replace(" ", "%20"));
            b.putInt(Constant.WIDTH, width);
            frgment.setArguments(b);
            brandFragments.add(frgment);
        }
        if (arrayList.size() > 1)
            brand = 1;
    }

    public void fillSubBrandfragment(ArrayList<SubBrand> arrayList) {
        subbrandFragment.clear();
        int width = (int) (getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.5);
        for (int i = 0; i < arrayList.size(); i++) {
            Brandfragment frgment = new Brandfragment();
            Bundle b = new Bundle();
            b.putString(Constant.URL, arrayList.get(i).image.replace(" ", "%20"));
            b.putInt(Constant.WIDTH, width);
            b.putString(Constant.NAME, arrayList.get(i).subBrandName);
            frgment.setArguments(b);
            subbrandFragment.add(frgment);
        }
        if (arrayList.size() > 1)
            subbrand = 1;
    }

    public void fillProductfragment(ArrayList<Product> arrayList) {
        if(arrayList!=null) {
            productFragment.clear();
            int width = (int) (getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.5);
            for (int i = 0; i < arrayList.size(); i++) {
                Brandfragment frgment = new Brandfragment();
                Bundle b = new Bundle();
                b.putString(Constant.URL, arrayList.get(i).image.replace(" ", "%20"));
                b.putInt(Constant.WIDTH, width);
                b.putString(Constant.ISWING, arrayList.get(i).isWing);
                b.putInt(Constant.TYPE, arrayList.get(i).pads);
                frgment.setArguments(b);
                productFragment.add(frgment);
            }
            if (arrayList.size() > 1)
                product = 1;
        }
    }

    public void fillsizelist(List<Size> sizeList) {
        String[] size = new String[sizeList.size()];
        for (int i = 0; i < sizeList.size(); i++)
            size[i] = sizeList.get(i).name;
        rvSize.setValues(size);
        if (size.length == 1) {
            rvSize.setSelectedItem(0);
            this.size=0;
        }
        else {
            this.size=1;
            rvSize.setSelectedItem(1);
        }
    }

    public void setAdapter(ArrayList<Brandfragment> brandfragments, final ViewPager brandRecyclerView, FragmentManager fm, int type) {
        int width = 0;
        if (type == 0)
            width = (int) (((Activity) brandRecyclerView.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.2);
        else if (type == 1)
            width = (int) (((Activity) brandRecyclerView.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.4);
        else if (type == 2)
            width = (int) (((Activity) brandRecyclerView.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.4);
        /*Animation rvListAni = AnimationUtils.loadAnimation(CreateActivity.this,
                R.anim.fade_in);
        brandRecyclerView.startAnimation(rvListAni);*/
        brandRecyclerView.setOffscreenPageLimit(brandfragments.size() - 1);

        ViewGroup.LayoutParams layoutParams = brandRecyclerView.getLayoutParams();
        layoutParams.width = width;
        if (brandRecyclerView.getParent() instanceof ViewGroup) {
            ViewGroup viewParent = ((ViewGroup) brandRecyclerView.getParent());
            viewParent.setClipChildren(false);
            brandRecyclerView.setClipChildren(false);
        }
        brandRecyclerView.setPageTransformer(false, new CustPagerTransformer(this, 0));
        Adapter adapter = new Adapter(fm, brandfragments, type);
        brandRecyclerView.setAdapter(adapter);
        if (brandfragments.size() > 1)
            brandRecyclerView.setCurrentItem(1);
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

    public void onClick(int clickno) {
        if (clickno == 0) {
            final int height = rvList.getHeight();
            ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.8f);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float endScale = Float.parseFloat(valueAnimator.getAnimatedValue() + "");
                    ViewGroup.LayoutParams layoutParams = rvList.getLayoutParams();
                    layoutParams.width = (int) (((Activity) rvList.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * endScale * 5.8f);
                    layoutParams.height = (int) (height * endScale * .75);
                    rvList.setLayoutParams(layoutParams);
                }
            });

            animator.setDuration(2000);
            animator.start();
        } else if (clickno == 1) {
            for (int i = 0; i < subbrandFragment.size(); i++) {
                subbrandFragment.get(i).setImage();
            }
            final int height = rvsubbrand.getHeight();

            ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.6f);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float endScale = Float.parseFloat(valueAnimator.getAnimatedValue() + "");
                    ViewGroup.LayoutParams layoutParams = rvsubbrand.getLayoutParams();
                    layoutParams.width = (int) (getWindowManager().getDefaultDisplay().getWidth() / 8 * endScale * 7.7f);
                    layoutParams.height = (int) (height * endScale);
                    rvsubbrand.setLayoutParams(layoutParams);
                }
            });

            animator.setDuration(2000);
            animator.start();
        } /*else if (clickno == 2) {

        }*/
    }


    public void SlideToAbove(final View view, final int margin) {
        int[] coords = {0, 0};
        view.getLocationOnScreen(coords);
        final int y = coords[1];
        final int height = view.getHeight();

        TranslateAnimation toTop = new TranslateAnimation(0, 0, 0, ((-y) + height / 2) + margin);
        toTop.setDuration(2000);
        toTop.setFillAfter(true);
        view.startAnimation(toTop);


        toTop.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                view.clearAnimation();
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
                lp.setMargins(0, ((-y) + height / 2) + margin, 0, 0);
                view.setLayoutParams(lp);
            }

        });

    }
}
