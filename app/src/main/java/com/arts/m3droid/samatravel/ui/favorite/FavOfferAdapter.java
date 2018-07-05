package com.arts.m3droid.samatravel.ui.favorite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.SpecialOffer;
import com.arts.m3droid.samatravel.ui.mainOffers.OffersAdapter;
import com.arts.m3droid.samatravel.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavOfferAdapter extends RecyclerView.Adapter<FavOfferAdapter.FavOfferViewHolder> {

    private OffersAdapter.OnItemClicked clickListener;
    private List<SpecialOffer> favOffers;

    public interface OnItemClicked {
        void onClick(int position);
    }


    FavOfferAdapter(OffersAdapter.OnItemClicked clickListener, List<SpecialOffer> favOffers) {
        this.clickListener = clickListener;
        this.favOffers = favOffers;
    }

    @NonNull
    @Override
    public FavOfferAdapter.FavOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_favorite_offer, parent, false);

        return new FavOfferAdapter.FavOfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavOfferAdapter.FavOfferViewHolder holder, int position) {

        SpecialOffer specialOffer = favOffers.get(position);

        ImageUtils.setImageOnImageView(specialOffer.getImageUrl(), holder.offerCard);


    }


    @Override
    public int getItemCount() {
        if (favOffers == null) return 0;
        return favOffers.size();
    }

    class FavOfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_offer_card)
        ImageView offerCard;

        FavOfferViewHolder(View itemView) {
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
