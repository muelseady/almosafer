package com.arts.m3droid.samatravel.ui.userHistory.historyDetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.Message;
import com.bumptech.glide.Glide;
import com.github.library.bubbleview.BubbleTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.ViewGroup.*;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private List<Message> mMessagesList;

    MessagesAdapter(List<Message> messagesList) {
        mMessagesList = messagesList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        Message message = mMessagesList.get(position);
        holder.senderName.setText(message.getSenderName());
        holder.messageBody.setText(message.getMessageBody());

        if (message.getSenderCategory().equals(Constants.CAT_USER)) {
            holder.messageBody.setVisibility(View.VISIBLE);
            holder.messageEmpBody.setVisibility(View.GONE);
            holder.messageBody.setText(message.getMessageBody());
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.START;

            holder.senderName.setLayoutParams(params);
        } else {
            holder.messageEmpBody.setVisibility(View.VISIBLE);
            holder.messageBody.setVisibility(View.GONE);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.END;

            holder.senderName.setLayoutParams(params);
            holder.messageEmpBody.setText(message.getMessageBody());
        }

        if (message.getImageUrl() != null) {
            holder.messageBody.setVisibility(View.GONE);
            holder.messageEmpBody.setVisibility(View.GONE);

            Glide.with(holder.messageImage.getContext())
                    .load(message.getImageUrl())
                    .into(holder.messageImage);
        }
    }

    @Override
    public int getItemCount() {
        if (mMessagesList == null) return 0;
        return mMessagesList.size();
    }

    public void clear() {
        if (mMessagesList == null) return;
        final int size = mMessagesList.size();
        mMessagesList.clear();
        notifyItemRangeRemoved(0, size);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nameTextView)
        TextView senderName;
        @BindView(R.id.body_for_me)
        BubbleTextView messageBody;
        @BindView(R.id.body_for_employee)
        BubbleTextView messageEmpBody;
        @BindView(R.id.photoImageView)
        ImageView messageImage;

        MessageViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
