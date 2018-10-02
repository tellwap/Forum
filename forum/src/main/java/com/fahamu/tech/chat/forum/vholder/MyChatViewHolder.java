package com.fahamu.tech.chat.forum.vholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fahamu.tech.chat.forum.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyChatViewHolder extends RecyclerView.ViewHolder {

    private CardView cardViewSender;
    private ImageView imageViewSender;
    private TextView messageTextViewSender;
    private TextView timeTextViewSender;

    private CardView cardViewReceiver;
    private ImageView imageViewReceiver;
    private TextView messageTextViewReceiver;
    private TextView timeTextViewReceiver;

    public MyChatViewHolder(View itemView) {
        super(itemView);
        cardViewSender = itemView.findViewById(R.id.chat_buble_card_send);
        imageViewSender = itemView.findViewById(R.id.chat_buble_image_send);
        messageTextViewSender = itemView.findViewById(R.id.chat_buble_mesage_send);
        timeTextViewSender = itemView.findViewById(R.id.chat_buble_time_send);

        cardViewReceiver = itemView.findViewById(R.id.chat_buble_card_receive);
        imageViewReceiver = itemView.findViewById(R.id.chat_buble_image_receive);
        messageTextViewReceiver = itemView.findViewById(R.id.chat_buble_mesage_receive);
        timeTextViewReceiver = itemView.findViewById(R.id.chat_buble_time_receive);
    }

    public CardView getCardViewSender() {
        return cardViewSender;
    }

    public ImageView getImageViewSender() {
        return imageViewSender;
    }

    public TextView getMessageTextViewSender() {
        return messageTextViewSender;
    }

    public TextView getTimeTextViewSender() {
        return timeTextViewSender;
    }

    public CardView getCardViewReceiver() {
        return cardViewReceiver;
    }

    public ImageView getImageViewReceiver() {
        return imageViewReceiver;
    }

    public TextView getMessageTextViewReceiver() {
        return messageTextViewReceiver;
    }

    public TextView getTimeTextViewReceiver() {
        return timeTextViewReceiver;
    }
}
