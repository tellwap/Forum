package com.fahamu.tech.chat.forum.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fahamu.tech.chat.forum.R;
import com.fahamu.tech.chat.forum.model.Messages;
import com.fahamu.tech.chat.forum.vholder.MyChatViewHolder;

import java.util.List;

public class MyChatViewAdapter extends RecyclerView.Adapter<MyChatViewHolder> {

    private List<Messages> messagesList;
    private Context context;

    public MyChatViewAdapter(List<Messages> messages, Context context) {
        this.messagesList = messages;
        this.context = context;
    }


    @NonNull
    @Override
    public MyChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context)
                .inflate(R.layout.message_buble, parent, false);
        return new MyChatViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyChatViewHolder holder, int position) {
        holder.getCardViewReceiver().setVisibility(View.VISIBLE);
        holder.getCardViewSender().setVisibility(View.VISIBLE);

        //holder.getImageViewReceiver().setVisibility(View.VISIBLE);
        //holder.getImageViewSender().setVisibility(View.VISIBLE);

        holder.getMessageTextViewReceiver().setVisibility(View.VISIBLE);
        holder.getMessageTextViewSender().setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
