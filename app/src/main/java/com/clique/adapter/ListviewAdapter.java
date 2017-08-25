package com.clique.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clique.R;
import com.clique.modle.HostalList;
import com.clique.utils.CustomTextView;

import java.util.ArrayList;

public class ListviewAdapter extends RecyclerView.Adapter<ListviewAdapter.View_Holder> {
    private int pos = 1;
    private ArrayList<HostalList> text;
    private OnClickList onClickList;

    public interface OnClickList {
        void onClick(int pos);
    }

    public ListviewAdapter(ArrayList<HostalList> text, Context context, OnClickList onClickList) {
        this.text = text;
        this.onClickList = onClickList;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hostel, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, final int position) {
        holder.title.setText(text.get(position).hostalname);
        holder.title.requestFocus();
        holder.title.setSelected(true);
        holder.title.setClickable(true);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickList.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return text.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder {

        CustomTextView title;

        View_Holder(View itemView) {
            super(itemView);
            title = (CustomTextView) itemView.findViewById(R.id.tv_text);
        }
    }
}

