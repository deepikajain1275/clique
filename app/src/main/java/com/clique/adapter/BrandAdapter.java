package com.clique.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import com.clique.R;
import com.clique.gallery.FancyCoverFlowAdapter;
import com.clique.modle.BrandModle;


/**
 * Created by Admin on 15-06-2015.
 */
public class BrandAdapter extends FancyCoverFlowAdapter {
    ArrayList<BrandModle> al_data;
    Context context;
    LayoutInflater li;
    ViewHolder holder;
    boolean flag;
    private int type = 0;
    private IMyViewHolderClicks iMyViewHolderClicks;

    public BrandAdapter(Context context, ArrayList<BrandModle> al, IMyViewHolderClicks iMyViewHolderClicks) {
        this.context = context;
        this.iMyViewHolderClicks = iMyViewHolderClicks;
        this.al_data = al;
        li = LayoutInflater.from(context);
    }

    @Override
    public View getCoverFlowItem(int position, View reusableView, ViewGroup parent) {
        if (reusableView != null) {
            // imageView = (View) reuseableView;
        } else {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            reusableView = inflater.inflate(R.layout.row_brand, parent, false);
            holder = new ViewHolder();
            holder.ivBrand = (ImageView) reusableView.findViewById(R.id.iv_brand);
            holder.ivBrand.setImageResource(al_data.get(position).alBrand);
            if (type == 1) {
                holder.ivBrand.setVisibility(View.GONE);
            } else {
                holder.ivBrand.setVisibility(View.VISIBLE);
            }
        }
        return reusableView;
    }

    @Override
    public int getCount() {
        return al_data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static interface IMyViewHolderClicks {
        public void onItemClick(int pos);
    }

    class ViewHolder {
        public ImageView ivBrand;
    }

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }

}
