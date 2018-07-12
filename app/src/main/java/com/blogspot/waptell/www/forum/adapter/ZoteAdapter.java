package com.blogspot.waptell.www.forum.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blogspot.waptell.www.forum.ChatActivity;
import com.blogspot.waptell.www.forum.model.PostModal;
import com.blogspot.waptell.www.forum.R;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import java.util.List;

/**
 * Created by chami o n  7/10/18.
 */

public class ZoteAdapter extends RecyclerView.Adapter<ZoteAdapter.ViewHolder> {

    private List<PostModal> listItem;
    private Context context;

    public ZoteAdapter(List<PostModal> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PostModal postModal = listItem.get(position);
        holder.postTitle.setText(postModal.getPost_title());
        holder.postDescription.setText(postModal.getPost_description());

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView postTitle;
        TextView postDescription;

        ViewHolder(final View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.post_title);
            postDescription = itemView.findViewById(R.id.post_description);

            itemView.setOnClickListener(v1 -> {
                context.startActivity(new Intent(context, ChatActivity.class));
            });

        }
    }
}
