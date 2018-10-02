package com.fahamu.tech.chat.forum.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fahamu.tech.chat.forum.R;
import com.fahamu.tech.chat.forum.model.Receipt;
import com.fahamu.tech.chat.forum.vholder.ReceiptsViewHolder;

import java.util.List;

public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsViewHolder> {

    private Context context;
    private List<Receipt> receipts;

    public ReceiptsAdapter(Context context, List<Receipt> receipt) {
        this.context = context;
        this.receipts = receipt;
    }

    @NonNull
    @Override
    public ReceiptsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.receipt, parent, false);
        return new ReceiptsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        //fake data
        return 20;
    }
}
