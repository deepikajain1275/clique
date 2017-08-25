package com.clique.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListAdapter;

import com.clique.R;
import com.clique.activity.OrderPlaceActivity;
import com.clique.adapter.DialogAdapter;
import com.clique.gallery.CustPagerTransformer;
import com.clique.modle.FinalProduct;
import com.clique.modle.Product;
import com.clique.utils.Constant;
import com.clique.utils.CustomTextView;
import com.clique.utils.SessionManager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deepika on 11-02-2017.
 */

public class OrderFragment extends Fragment implements Brandfragment.ImageClick {
    // private SimpleDraweeView ivBrand;
    private Animation textAni;
    private CustomTextView tvOrderPlaced;
    private ViewPager mViewPager;
    private SessionManager sessionManager;
    private int width;
    private Adapter adapter;
    private CustomTextView tvModify;
    private List<Brandfragment> productFragment = new ArrayList<>();
    private AlertDialog alertDialog;
    private ArrayList<Product> alProduct = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_fragment, container, false);
        sessionManager = new SessionManager(getContext());
        tvModify = (CustomTextView) v.findViewById(R.id.tv_modifiy);
        //ivBrand = (SimpleDraweeView) v.findViewById(R.id.iv_brand);
        mViewPager = (ViewPager) v.findViewById(R.id.vp_order);
        setupViewPager(mViewPager);

      /*  mViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlerDailogList();
            }
        });*/
        tvOrderPlaced = (CustomTextView) v.findViewById(R.id.tv_orderplaced);
        textAni = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_invisible);
        tvOrderPlaced.startAnimation(textAni);
        return v;
    }


    public void showAlerDailogList() {
        final ArrayList<String> text = new ArrayList<>();
        text.add("Current Order");
        text.add("Cancel Order");
        final ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_curent_cart);
        icons.add(R.drawable.ic_cart_cancel);
        ListAdapter adapter = new DialogAdapter(text, icons, getActivity());
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setTitle("Modify Order");
        builder1.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    showAlerDailog();
                } else {
                    AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(getContext());
                    dialogBuilder1.setTitle("Cancel Order");
                    dialogBuilder1.setMessage("Do you really want to cancel this order?");
                    dialogBuilder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            showAlerDailogNotCancle();
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
        AlertDialog alert1 = builder1.create();
        alert1.show();
    }


    private void setupViewPager(ViewPager viewPager) {
        FinalProduct finalProduct = sessionManager.getArrayLisOrder(getContext());
        alProduct = finalProduct.alFinalProduct;
        width = (int) ((int) getActivity().getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.4);
        viewPager.setOffscreenPageLimit(alProduct.size() - 1);

        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        layoutParams.width = width;
        if (viewPager.getParent() instanceof ViewGroup) {
            ViewGroup viewParent = ((ViewGroup) viewPager.getParent());
            viewParent.setClipChildren(false);
            viewPager.setClipChildren(false);
        }
        viewPager.setPageTransformer(false, new CustPagerTransformer(getContext(), 0));
        fillProductfragment(alProduct);
        adapter = new Adapter(getChildFragmentManager(), productFragment, 0);
        mViewPager.setAdapter(adapter);

    }

    public void fillProductfragment(ArrayList<Product> arrayList) {
        productFragment.clear();
        int width = (int) (getActivity().getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.5);
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

    public void showAlerDailog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_current_order, null);
        SimpleDraweeView imageView = (SimpleDraweeView) dialogView.findViewById(R.id.iv_brand);
        imageView.setImageURI(alProduct.get(mViewPager.getCurrentItem()).image);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        alertDialog.show();
        alertDialog.getWindow().getDecorView().setSystemUiVisibility(
                getActivity().getWindow().getDecorView().getSystemUiVisibility());
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

    }

    public void showAlerDailogNotCancle() {
        CustomTextView customName, checkstaus;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_error, null);
        customName = (CustomTextView) dialogView.findViewById(R.id.tv_name);
        checkstaus = (CustomTextView) dialogView.findViewById(R.id.tv_create);
        checkstaus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OrderPlaceActivity) getActivity()).changePage(1);
                alertDialog.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        alertDialog.show();
      /*  alertDialog.getWindow().getDecorView().setSystemUiVisibility(
                getActivity(). getWindow().getDecorView().getSystemUiVisibility());
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);*/

    }

}
