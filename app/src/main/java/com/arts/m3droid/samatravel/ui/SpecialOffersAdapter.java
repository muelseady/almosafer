package com.arts.m3droid.samatravel.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arts.m3droid.samatravel.R;

import butterknife.ButterKnife;

public class SpecialOffersAdapter extends RecyclerView.Adapter<SpecialOffersAdapter.SpecialViewHolder> {

    private OnItemClicked clickListener;

    public interface OnItemClicked {
        void onClick(int position);
    }

    SpecialOffersAdapter(OnItemClicked clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SpecialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from
                (parent.getContext()).inflate(R.layout.card_special_offers, parent, false);

        return new SpecialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 10;
    }

    class SpecialViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
