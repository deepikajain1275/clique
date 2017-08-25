package com.clique.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clique.R;
import com.clique.utils.CustomTextView;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {
    private List<String> inviteList;
    private List<String> productItem = new ArrayList<>();
    private HorizontalAdapter horizontalAdapter;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextView tvOrderid;
        public CustomTextView tvTotal;
        public CustomTextView tvTime;
        public RecyclerView rvOrder;

        public MyViewHolder(View view) {
            super(view);
            horizontalAdapter = new HorizontalAdapter(productItem);
            rvOrder = (RecyclerView) view.findViewById(R.id.rv_order);
            rvOrder.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            rvOrder.setAdapter(horizontalAdapter);
            tvOrderid = (CustomTextView) view.findViewById(R.id.tv_orderid);
            tvTotal = (CustomTextView) view.findViewById(R.id.tv_total);
            tvTime = (CustomTextView) view.findViewById(R.id.tv_time);
        }
    }


    public OrderHistoryAdapter(List<String> inviteList) {
        this.inviteList = inviteList;
        productItem.add("");
        productItem.add("");
        productItem.add("");
        productItem.add("");
        productItem.add("");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order, parent, false);

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

