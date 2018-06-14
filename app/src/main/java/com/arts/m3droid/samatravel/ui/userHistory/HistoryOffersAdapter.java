package com.arts.m3droid.samatravel.ui.userHistory;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.SpecialOfferRequest;
import com.arts.m3droid.samatravel.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryOffersAdapter extends RecyclerView.Adapter<HistoryOffersAdapter.HistoryViewHolder> {


    private OnItemClicked clickListener;
    private List<SpecialOfferRequest> specialOffers;

    public interface OnItemClicked {
        void onClick(int position);
    }

    HistoryOffersAdapter(List<SpecialOfferRequest> specialOffers, OnItemClicked clickListener) {
        this.clickListener = clickListener;
        this.specialOffers = specialOffers;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_history_offer, parent, false);

        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.offerName.setText(specialOffers.get(position).getOfferName());
        ImageUtils.setImageOnImageView(specialOffers.get(position).getOfferImageUrl(), holder.offerCard);
    }


    @Override
    public int getItemCount() {
        if (specialOffers == null) return 0;
        return specialOffers.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_offer_card)
        ImageView offerCard;
        @BindView(R.id.offer_name)
        TextView offerName;
        @BindView(R.id.emp_name)
        TextView empName;

        HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(getAdapterPosition());
        }
    }
}