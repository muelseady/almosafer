package com.arts.m3droid.samatravel.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.SpecialOffer;
import com.arts.m3droid.samatravel.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SpecialOffersAdapter extends RecyclerView.Adapter<SpecialOffersAdapter.SpecialViewHolder> {

    private OnItemClicked clickListener;
    private List<SpecialOffer> specialOffers;

    public interface OnItemClicked {
        void onClick(int position);
    }

    SpecialOffersAdapter(List<SpecialOffer> specialOffers, OnItemClicked clickListener) {
        this.clickListener = clickListener;
        this.specialOffers = specialOffers;
    }

    @NonNull
    @Override
    public SpecialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_special_offers, parent, false);

        return new SpecialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialViewHolder holder, int position) {

        Timber.plant(new Timber.DebugTree());
        Timber.d("onBind called " + specialOffers.get(position).getImageUrl());
        ImageUtils.setImageOnImageView(specialOffers.get(position).getImageUrl(), holder.offerCard);
    }


    @Override
    public int getItemCount() {
        return specialOffers.size();
    }

    class SpecialViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_offer_card)
        ImageView offerCard;

        SpecialViewHolder(View itemView) {
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
