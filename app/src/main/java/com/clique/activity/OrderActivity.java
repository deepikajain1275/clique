package com.clique.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.clique.R;
import com.clique.adapter.DialogAdapter;
import com.clique.fragment.Brandfragment;
import com.clique.gallery.CustPagerTransformer;
import com.clique.modle.Data;
import com.clique.modle.FinalProduct;
import com.clique.modle.Product;
import com.clique.utils.Constant;
import com.clique.utils.CustomTextView;
import com.clique.utils.DFragment;
import com.clique.utils.SessionManager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class OrderActivity extends AppCompatActivity implements MaterialIntroListener, Brandfragment.ImageClick {
    private ImageView ivUser;
    private String userid = "USERID";
    private String DISCOUNT = "Discount";
    AlertDialog alertDialog = null;
    private SimpleDraweeView ivProfile;
    private ImageView ivbuy;
    private ImageView ivSetting;
    private ViewPager mViewPager;
    private CustomTextView tvModifiy;
    private Data data;
    private String QUANTITY = "quantity";
    private String ORDER = "order";
    private String PROFILE = "profile";
    private SessionManager sessionManager;
    private int width;
    private ArrayList<Brandfragment> productFragment = new ArrayList<>();
    private Adapter adapter;
    private LinearLayout llDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initdata();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void initdata() {
        sessionManager = new SessionManager(this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_brand);
        llDiscount = (LinearLayout) findViewById(R.id.ll_discount);
        setupViewPager(mViewPager,getSupportFragmentManager());

        ivbuy = (ImageView) findViewById(R.id.iv_buy);
        tvModifiy = (CustomTextView) findViewById(R.id.tv_modifiy);
        data = SessionManager.getSharedPreferenece(OrderActivity.this);
        ivProfile = (SimpleDraweeView) findViewById(R.id.iv_profile);
        ivUser = (ImageView) findViewById(R.id.iv_userimage);
        ivSetting = (ImageView) findViewById(R.id.iv_setting);

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrderActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data != null) {
                    if (data.isGetInviteFriendDisount == 0)
                        showAlerDailog();
                    else {
                        Intent i = new Intent(OrderActivity.this, InviteActivity.class);
                        startActivity(i);
                    }
                } else {
                    showAlerDailog();
                }
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrderActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
        ivbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DFragment dFragment = new DFragment();

                dFragment.show(getSupportFragmentManager(), "Dialog Fragment");
            }
        });

      /*  mViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlerDailogList();
            }
        });*/

        SessionManager sessionManager = new SessionManager(OrderActivity.this);
        HashMap<String, String> map = sessionManager.get(OrderActivity.this);
        if (data != null) {
            if (!TextUtils.isEmpty(data.profilePic)) {
                ivProfile.setImageURI(data.profilePic);
            }
            if (data.isGetSigupDisount == 0) {
                ivUser.setImageResource(R.drawable.ic_two_women_on_line_2);
                showIntro(llDiscount, DISCOUNT, "Sign Up Discount", FocusGravity.CENTER, R.drawable.ic_discount, Focus.ALL, ShapeType.RECTANGLE, 1000);
            } else {
                if (data.isGetInviteFriendDisount == 0)
                    showIntro(ivUser, userid, "Invite Friends to Save", FocusGravity.CENTER, R.drawable.ic_rupee_tut, Focus.ALL, ShapeType.CIRCLE, 1000);
                else
                    showIntro(llDiscount, "friend", "Friends Discount", FocusGravity.CENTER, R.drawable.ic_rupee_tut, Focus.ALL, ShapeType.RECTANGLE, 1000);
                ivUser.setImageResource(R.drawable.ic_two_women_on_line);
            }
        } else {
            showIntro(llDiscount, DISCOUNT, "Sign Up Discount", FocusGravity.CENTER, R.drawable.ic_rupee_tut, Focus.ALL, ShapeType.RECTANGLE, 1000);
        }


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

    @Override
    public void onUserClicked(String materialIntroViewId) {
        if (materialIntroViewId.equals(DISCOUNT)) {
            if (data == null || data.isGetInviteFriendDisount == 0)
                showIntro(ivUser, userid, "Invite Friends to Save", FocusGravity.CENTER, R.drawable.ic_rupee_tut, Focus.ALL, ShapeType.CIRCLE, 1000);
        } /*else if (materialIntroViewId.equals(DISCOUNT)) {
            showIntro(rlSize, QUANTITY, "Select Quantity Here", FocusGravity.CENTER, R.drawable.ic_cart, Focus.NORMAL, ShapeType.CIRCLE, 500);
        } else if (materialIntroViewId.equals(QUANTITY)) {
            showIntro(ivbuy, ORDER, "Click to Order", FocusGravity.CENTER, R.drawable.ic_cart, Focus.NORMAL, ShapeType.CIRCLE, 500);
        } else if (materialIntroViewId.equals(ORDER)) {
            showIntro(ivProfile, PROFILE, "Give Details to Deliver", FocusGravity.CENTER, R.drawable.ic_happy_2, Focus.NORMAL, ShapeType.CIRCLE, 500);
        }*/
    }

    public void showAlerDailog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_invite, null);
        CustomTextView ctInvite = (CustomTextView) dialogView.findViewById(R.id.tv_invite);
        ctInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                PackageManager pm = getPackageManager();
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "Download this fabulous girl’s hygiene app," + getString(R.string.app_name) + "! You will love it.\n http://play.google.com/store/apps/details?id=" + getPackageName();
                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(OrderActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }
              /*  Intent i = new Intent(OrderActivity.this, InviteActivity.class);
                startActivity(i);*/
            }
        });
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        alertDialog.show();
        alertDialog.getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility());
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    public void showAlerDailogList() {
        final ArrayList<String> text = new ArrayList<>();
        text.add("Add Product");
        text.add("Remove Product");
        final ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_cart_plus);
        icons.add(R.drawable.ic_cart_minus);
        ListAdapter adapter1 = new DialogAdapter(text, icons, OrderActivity.this);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderActivity.this);
        builder1.setTitle("Modify Product");
        builder1.setAdapter(adapter1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (item == 0) {
                    Intent intent = new Intent(OrderActivity.this, CreateActivity.class);
                    intent.putExtra(Constant.TYPE, 1);
                    startActivity(intent);
                    finish();
                } else {
                    AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(OrderActivity.this);
                    dialogBuilder1.setTitle("Remove Product");
                    dialogBuilder1.setMessage("Do you really want to remove this product?");
                    dialogBuilder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (productFragment.size() == 1) {
                                Intent intent = new Intent(OrderActivity.this, CreateActivity.class);
                                intent.putExtra(Constant.TYPE, 1);
                                startActivity(intent);
                                finish();
                            } else {
                                int pos = mViewPager.getCurrentItem();
                                ArrayList<Product> arrayList = sessionManager.getArrayLisOrder(OrderActivity.this).alFinalProduct;
                                arrayList.remove(pos);
                                FinalProduct finalProduct = new FinalProduct();
                                finalProduct.alFinalProduct = arrayList;
                                sessionManager.putArrayListOrder(finalProduct);
                                if (pos == productFragment.size() - 1) {
                                    mViewPager.setCurrentItem(pos - 1);
                                } else {
                                    mViewPager.setCurrentItem(pos + 1);
                                }
                                productFragment.remove(pos);
                                adapter.notifyDataSetChanged();

                            }
                        }
                    });
                    dialogBuilder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog1 = dialogBuilder1.create();
                    alertDialog1.show();

                }
            }
        });
        alertDialog = builder1.create();
        alertDialog.show();
    }


    private void setupViewPager(ViewPager viewPager,FragmentManager fm) {
        FinalProduct finalProduct = sessionManager.getArrayLisOrder(OrderActivity.this);
        ArrayList<Product> alProduct = finalProduct.alFinalProduct;
        width = (int) (((Activity) viewPager.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.4);
        viewPager.setOffscreenPageLimit(alProduct.size() - 1);
        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        layoutParams.width = width;
        if (viewPager.getParent() instanceof ViewGroup) {
            ViewGroup viewParent = ((ViewGroup) viewPager.getParent());
            viewParent.setClipChildren(false);
            viewPager.setClipChildren(false);
        }
        viewPager.setPageTransformer(false, new CustPagerTransformer(this, 0));
        fillProductfragment(alProduct);
        adapter = new Adapter(fm, productFragment, 0);
        viewPager.setAdapter(adapter);
      /*  if (brandfragments.size() > 1)
            brandRecyclerView.setCurrentItem(1);*/
    }

    @Override
    public void onImageClick(int pos) {
        showAlerDailogList();
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
            if (brandfragments.contains(object)) {
                return brandfragments.indexOf(object);
            }
            return POSITION_NONE;
        }

        public void setType(int type) {
            this.type = type;
            notifyDataSetChanged();
        }

    }

    public void fillProductfragment(ArrayList<Product> arrayList) {
        productFragment.clear();
        int width = (int) (getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.5);
        for (int i = 0; i < arrayList.size(); i++) {
            Brandfragment frgment = new Brandfragment(this);
            Bundle b = new Bundle();
            b.putString(Constant.URL, arrayList.get(i).image.replace(" ", "%20"));
            b.putInt(Constant.WIDTH, width);
            b.putString(Constant.ISWING, arrayList.get(i).isWing);
            b.putInt(Constant.TYPE, -1);
            frgment.setArguments(b);
            productFragment.add(frgment);
        }

    }



    public void showOrderAlerDailog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OrderActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_current_order, null);
        dialogBuilder.setView(dialogView);
       // findViews(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        alertDialog.show();
        alertDialog.getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility());
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

    }
}
