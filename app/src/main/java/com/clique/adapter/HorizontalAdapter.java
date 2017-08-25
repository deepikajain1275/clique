package com.clique.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.clique.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

    private List<String> horizontalList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView txtView;

        public MyViewHolder(View view) {
            super(view);
            txtView = (SimpleDraweeView) view.findViewById(R.id.iv_brand);

        }
    }

    public HorizontalAdapter(List<String> horizontalList) {
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_text, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       //holder.txtView.setImageURI(R);
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}

