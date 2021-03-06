package com.fahamu.tech.chat.forum.adapter;

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
import com.bumptech.glide.Glide;
import com.fahamu.tech.chat.forum.ChatActivity;
import com.fahamu.tech.chat.forum.GetTimeAgo;
import com.fahamu.tech.chat.forum.database.FirestoreUtils;
import com.fahamu.tech.chat.forum.model.Post;
import com.fahamu.tech.chat.forum.model.PostModal;
import com.fahamu.tech.chat.forum.R;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chami o n 7/9/18.
 */

public class ZanguAdapter extends RecyclerView.Adapter<ZanguAdapter.ViewHolder> {

    private List<Post> listItem;
    private Context context;
    private FirestoreUtils firestoreUtils=new FirestoreUtils();
    private GetTimeAgo getTimeAgo;

    public ZanguAdapter(List<Post> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list,
                parent, false);

        getTimeAgo = new GetTimeAgo();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post postModal = listItem.get(position);
        holder.postTitle.setText(postModal.getTitle());
        holder.postDescription.setText(postModal.getDescription());
        long t= Long.parseLong(postModal.getTime());
        Date date =new Date(t);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        holder.docId.setText(postModal.getDocId());
        holder.time.setText(getTimeAgo.getTimeAgo(Long.parseLong(postModal.getTime()),context));

        Glide.with(context).load(postModal.getUserPhoto())
                .into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView postTitle;
        TextView postDescription;
        TextView time;
        TextView docId;
        CircleImageView circleImageView;

        ViewHolder(final View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.post_title);
            postDescription = itemView.findViewById(R.id.post_description);
            this.time=itemView.findViewById(R.id.forum_time);
            this.docId=itemView.findViewById(R.id.post_id);
            this.circleImageView=itemView.findViewById(R.id.profile_image);

            itemView.setOnClickListener(v1 -> {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("title",postTitle.getText());
                intent.putExtra("description",postDescription.getText());
                intent.putExtra("docId",docId.getText());
                context.startActivity(intent);
            });

            itemView.setOnLongClickListener(v -> {
                Log.e("TAG******", " I am clicked");
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect
                                .createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        vibrator.vibrate(100);
                    }
                }
                Snackbar.make(itemView, "Futa ujumbe", Toast.LENGTH_SHORT).show();

                new MaterialStyledDialog.Builder(context)
                        .setDescription("Kubali Kufuta Ujumbe Wako")
                        .setStyle(Style.HEADER_WITH_ICON)
                        .setIcon(R.drawable.ic_delete_black_24dp)
                        .setPositiveText("KUBALI")
                        .setNegativeText("KATAA")
                        .onPositive((dialog, which) -> {
                            //TODO: delete from firestor
                            firestoreUtils.deletePost(docId.getText().toString());
                            //Log.e("TAG******","Umefua");
                            Snackbar.make(itemView, "Ujumbe umefutwa", Toast.LENGTH_SHORT).show();
                        }).onNegative((dialog, which) -> {
                    Snackbar.make(itemView, "Umegairi Kufuta ujumbe", Toast.LENGTH_SHORT).show();
                }).show();

                return true;
            });
        }
    }
}
