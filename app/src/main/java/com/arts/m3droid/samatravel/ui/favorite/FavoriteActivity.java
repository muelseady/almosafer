package com.arts.m3droid.samatravel.ui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.SpecialOffer;
import com.arts.m3droid.samatravel.model.User;
import com.arts.m3droid.samatravel.ui.mainOffers.details.SpecialOffersDetailsActivity;
import com.arts.m3droid.samatravel.utils.FirebaseFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.arts.m3droid.samatravel.utils.SocialMediaButtonsHandler.handleFb;
import static com.arts.m3droid.samatravel.utils.SocialMediaButtonsHandler.handleInsta;
import static com.arts.m3droid.samatravel.utils.SocialMediaButtonsHandler.handleSnap;
import static com.arts.m3droid.samatravel.utils.SocialMediaButtonsHandler.handleTwitter;

public class FavoriteActivity extends AppCompatActivity implements FavOfferAdapter.OnItemClicked {

    @BindView(R.id.rv_special_offers)
    RecyclerView rvSpecialOffers;
    @BindView(R.id.iv_twitter)
    ImageView ivTwitter;
    @BindView(R.id.iv_fb)
    ImageView ivFb;
    @BindView(R.id.iv_instagram)
    ImageView ivInstagram;
    @BindView(R.id.iv_snap)
    ImageView ivSnap;

    private ArrayList<SpecialOffer> specialOffers = new ArrayList<>();
    private List<SpecialOffer> currentFavOffers = new ArrayList<>();
    private User user;
    private String userKey;

    private DatabaseReference userReference =
            FirebaseFactory.getDatabase().getReference(Constants.NODE_USERS);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        specialOffers = getIntent().getParcelableArrayListExtra(Constants.DATA_SPECIAL_OFFER);
        user = getIntent().getParcelableExtra(Constants.CAT_USER);
        userKey = getIntent().getStringExtra("userKey");

        socialMediaButtonsHandling();
        loadFavOffers();

    }

    private void socialMediaButtonsHandling() {
        ivFb.setOnClickListener(v -> handleFb(this));
        ivTwitter.setOnClickListener(v -> handleTwitter(this));
        ivInstagram.setOnClickListener(v -> handleInsta(this));
        ivSnap.setOnClickListener(v -> handleSnap(this));

    }

    private void loadFavOffers() {
        userReference.child(userKey).child(Constants.FAV_OFFERS).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        currentFavOffers.clear();
                        for (DataSnapshot goinOffersSnap : dataSnapshot.getChildren()) {
                            for (SpecialOffer specialOffer : specialOffers) {

                                if (Objects.requireNonNull(goinOffersSnap.getKey()).equals(specialOffer.getUid())) {
                                    currentFavOffers.add(specialOffer);
                                }
                            }
                            setUpRecyclerView();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


    }

    private void setUpRecyclerView() {
        rvSpecialOffers.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rvSpecialOffers.setLayoutManager(layoutManager);
        FavOfferAdapter adapter = new FavOfferAdapter(
                this::onClick,
                currentFavOffers
        );
        rvSpecialOffers.setAdapter(adapter);

    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, SpecialOffersDetailsActivity.class);
        intent.putExtra(Constants.DATA_SPECIAL_OFFER, specialOffers.get(position));
        intent.putExtra(Constants.NODE_USERS, user);
        startActivity(intent);
    }
}
