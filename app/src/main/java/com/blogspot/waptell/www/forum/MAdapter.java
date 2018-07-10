package com.blogspot.waptell.www.forum;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chami on 7/9/18.
 */

public class MAdapter extends RecyclerView.Adapter<MAdapter.ViewHolder> {

    private List<PostModal> listItem;
    private Context context;

    public MAdapter(List<PostModal> listItem, Context context) {
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

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView postTitle;
        TextView postDescription;

        ViewHolder(View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.post_title);
            postDescription = itemView.findViewById(R.id.post_description);
        }
    }
}
