package com.clique.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.clique.R;
import com.clique.utils.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private int pos = 1;
    private List<String> inviteList;
    private OnItemClick onitem;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextView txtView;
        public SimpleDraweeView ivprofile;
        public CustomTextView tvDisount;
        public ImageView ivChecked;

        public MyViewHolder(View view) {
            super(view);
            txtView = (CustomTextView) view.findViewById(R.id.tv_name);
            ivprofile = (SimpleDraweeView) view.findViewById(R.id.iv_profile);
            tvDisount = (CustomTextView) view.findViewById(R.id.tv_discount);
            ivChecked = (ImageView) view.findViewById(R.id.iv_checked);
        }
    }

    public interface OnItemClick {
        public void onItemclick(int pos);
    }


    public NotificationAdapter(List<String> inviteList) {
        this.inviteList = inviteList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return inviteList.size();
    }
}

