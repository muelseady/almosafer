package com.arts.m3droid.samatravel.ui.mainOffers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.SpecialOffer;
import com.arts.m3droid.samatravel.utils.ImageUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.SpecialViewHolder> {

    private OnItemClicked clickListener;
    private OnFavButtonClicked onFavButtonClicked;
    private Map<String, String> favOffers;
    private List<SpecialOffer> specialOffers;
    private Context context;

    public interface OnItemClicked {
        void onClick(int position);
    }

    public interface OnFavButtonClicked {
        void onFavClicked(int position, ImageView view);
    }

    OffersAdapter(List<SpecialOffer> specialOffers, OnItemClicked clickListener,
                  OnFavButtonClicked onFavClickedListener, Context context, Map<String, String> favOffers) {
        this.clickListener = clickListener;
        this.specialOffers = specialOffers;
        this.onFavButtonClicked = onFavClickedListener;
        this.favOffers = favOffers;
        this.context = context;
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

        ImageUtils.setImageOnImageView(specialOffers.get(position).getImageUrl(), holder.offerCard);

        boolean favoriteOffer = false;
        for (Map.Entry<String, String> entry : favOffers.entrySet()) {
            if (entry.getValue().equals(specialOffers.get(position).getUid()))//Offer already in the map
                favoriteOffer = true;
        }

        if (favoriteOffer)
            holder.btnFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.fav_icon));
        else {
            holder.btnFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.unfav_icon));
        }
    }


    @Override
    public int getItemCount() {
        if (specialOffers == null) return 0;
        return specialOffers.size();
    }

    class SpecialViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_offer_card)
        ImageView offerCard;
        @BindView(R.id.iv_fav_btn)
        ImageView btnFav;

        SpecialViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            btnFav.setOnClickListener(v -> onFavButtonClicked.onFavClicked(getAdapterPosition(), btnFav));
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(getAdapterPosition());
        }
    }
}
