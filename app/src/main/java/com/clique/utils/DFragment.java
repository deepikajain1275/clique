package com.clique.utils;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.clique.R;
import com.clique.activity.OrderActivity;
import com.clique.activity.OrderPlaceActivity;
import com.clique.fragment.Brandfragment;
import com.clique.gallery.CustPagerTransformer;
import com.clique.modle.FinalProduct;
import com.clique.modle.Product;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.materialintro.animation.MaterialIntroListener;

public class DFragment extends DialogFragment implements  Brandfragment.ImageClick{

	private LinearLayout llInfo;
	private CustomTextView tvHostal;
	private CustomTextView tvPhoneNo;
	private SimpleDraweeView ivBrand;
	private ViewPager viewpagerBrand;
	private CustomTextView tvTotal;
	private LinearLayout llDiscount;
	private CustomTextView tvDiscount;
	private CustomTextView tvPay;
	private View vView2;
	private CustomTextView tvCheckout;
	private SessionManager sessionManager;

	@Override
	public void onImageClick(int pos) {

	}

	private void findViews(View rootView) {
		sessionManager = new SessionManager(getContext());
		llInfo = (LinearLayout) rootView.findViewById(R.id.ll_info);
		tvHostal = (CustomTextView) rootView.findViewById(R.id.tv_hostal);
		tvPhoneNo = (CustomTextView) rootView.findViewById(R.id.tv_phone_no);
		ivBrand = (SimpleDraweeView) rootView.findViewById(R.id.iv_brand);
		viewpagerBrand = (ViewPager) rootView.findViewById(R.id.viewpager_brand_dialog);
		tvTotal = (CustomTextView) rootView.findViewById(R.id.tv_total);
		llDiscount = (LinearLayout) rootView.findViewById(R.id.ll_discount);
		tvDiscount = (CustomTextView) rootView.findViewById(R.id.tv_discount);
		tvPay = (CustomTextView) rootView.findViewById(R.id.tv_pay);
		vView2 = (View) rootView.findViewById(R.id.v_view2);
		tvCheckout = (CustomTextView) rootView.findViewById(R.id.tv_checkout);
		tvCheckout.setVisibility(View.VISIBLE);
		llInfo.setVisibility(View.VISIBLE);
		ivBrand.setVisibility(View.GONE);
		viewpagerBrand.setVisibility(View.VISIBLE);
		llDiscount.setVisibility(View.GONE);
		FinalProduct finalProduct = sessionManager.getArrayLisOrder(getContext());
		ArrayList<Product> alProduct = finalProduct.alFinalProduct;
		int width = (int) (getActivity().getWindowManager().getDefaultDisplay().getWidth() / 8 * 5.4)-100;
		viewpagerBrand.setOffscreenPageLimit(alProduct.size() - 1);
		ViewGroup.LayoutParams layoutParams = viewpagerBrand.getLayoutParams();
		layoutParams.width = width;
		if (viewpagerBrand.getParent() instanceof ViewGroup) {
			ViewGroup viewParent = ((ViewGroup) viewpagerBrand.getParent());
			viewParent.setClipChildren(false);
			viewpagerBrand.setClipChildren(false);
		}
		viewpagerBrand.setPageTransformer(false, new CustPagerTransformer(getContext(), 0));
		ArrayList<Brandfragment> productFragment= new ArrayList<>();
		for (int i = 0; i < alProduct.size(); i++) {
			Brandfragment frgment = new Brandfragment(this);
			Bundle b = new Bundle();
			b.putString(Constant.URL, alProduct.get(i).image.replace(" ", "%20"));
			b.putInt(Constant.WIDTH, width);
			b.putString(Constant.ISWING, alProduct.get(i).isWing);
			b.putInt(Constant.TYPE, -1);
			frgment.setArguments(b);
			productFragment.add(frgment);
		}
		Adapter adapter = new Adapter(getChildFragmentManager(), productFragment, 0);
		viewpagerBrand.setAdapter(adapter);
		// /setupViewPager(viewpagerBrand,getSupportFragmentManager());
		tvCheckout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismiss();
				Intent i = new Intent(getActivity(), OrderPlaceActivity.class);
				startActivity(i);
				getActivity().finish();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_current_order, container,
				false);
		findViews(rootView);
		//setStyle(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme);
//		rootView.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//		alertDialog.show();
//		alertDialog.getWindow().getDecorView().setSystemUiVisibility(
//				getWindow().getDecorView().getSystemUiVisibility());
//		alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
		return rootView;
	}
	@Override
	public void onStart()
	{
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null)
		{
			int width = ViewGroup.LayoutParams.MATCH_PARENT;
			int height = ViewGroup.LayoutParams.WRAP_CONTENT;
			dialog.getWindow().setLayout(width, height);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
			dialog.getWindow().getDecorView().setSystemUiVisibility(
					dialog.getWindow().getDecorView().getSystemUiVisibility());
			dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
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

}