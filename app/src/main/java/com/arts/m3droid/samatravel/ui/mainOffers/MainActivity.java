package com.arts.m3droid.samatravel.ui.mainOffers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.SpecialOffer;
import com.arts.m3droid.samatravel.model.User;
import com.arts.m3droid.samatravel.ui.callUs.CallUsActivity;
import com.arts.m3droid.samatravel.ui.customOffers.CustomOffersActivity;
import com.arts.m3droid.samatravel.ui.mainOffers.details.SpecialOffersDetailsActivity;
import com.arts.m3droid.samatravel.ui.userHistory.HistoryActivity;
import com.arts.m3droid.samatravel.utils.FirebaseFactory;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.arts.m3droid.samatravel.Constants.RC_SIGN_IN;

public class MainActivity extends AppCompatActivity implements SpecialOffersAdapter.OnItemClicked {

    @BindView(R.id.drawerlayout)
    FlowingDrawer mDrawer;

    @BindView(R.id.rv_special_offers)
    RecyclerView rvSpecialOffers;
    @BindView(R.id.tv_custom_offer)
    TextView tvCustomOffer;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.tv_call_us)
    TextView tvCallUs;
    @BindView(R.id.tv_signOut)
    TextView signOut;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference userReference;
    private DatabaseReference specialOfferReference;
    private List<SpecialOffer> specialOffers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        verifyUserAuth();

        specialOffers = new ArrayList<>();


        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        setUpToolbar();
        loadData();
        setUpAnimations();


        tvCustomOffer.setOnClickListener(v -> startAnotherActivity(CustomOffersActivity.class));
        tvHistory.setOnClickListener(v -> startAnotherActivity(HistoryActivity.class));
        tvCallUs.setOnClickListener(v -> startAnotherActivity(CallUsActivity.class));
        signOut.setOnClickListener(v -> AuthUI.getInstance().signOut(this)); // Sign out from Auth as simple as that
    }

    private void verifyUserAuth() {
        userReference =
                FirebaseFactory.getDatabase().getReference(Constants.NODE_USERS);
        firebaseAuth = FirebaseAuth.getInstance();
        authListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) { // User authorized
                addTheUserDataToDb(user);
            } else { // User not authorized
                showSignIn();
            }
        };
        firebaseAuth.addAuthStateListener(authListener);
    }

    /**
     * @param authUser we extract the user info as user id and name and then populate the AuthUser
     *                 Object and send it to the database
     */
    private void addTheUserDataToDb(FirebaseUser authUser) {
        String userKey = authUser.getUid();
        String userName = authUser.getDisplayName();
        String userNumber = null;
        String userEmail = null;
        if (authUser.getPhoneNumber() != null) {
            userNumber = authUser.getPhoneNumber();
        }
        if (authUser.getEmail() != null) {
            userEmail = authUser.getEmail();
        }
        User user = new User(userKey, userName, userNumber, userEmail);

        // To uniquely store the user once with no duplication , Store the user unique id as a key
        // in the node then store the name and the location of the image url as values to this key
        //  /"users"-
        //           |- "userId"-
        //                       |- "userName"
        //                       |- "imageUrl"
        userReference.child(userKey).setValue(user);
    }


    private void showSignIn() {

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build()
                                //Todo Here is the place to add more providers
                        ))
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded
                Toast.makeText(this, R.string.txt_welcom, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, R.string.txt_sign_in_again, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void setUpAnimations() {
        YoYo.with(Techniques.Landing)
                .duration(2000)
                .playOn(rvSpecialOffers);
    }

    private void loadData() {
        specialOfferReference =
                FirebaseFactory.getDatabase().getReference().child(Constants.NODE_SPECIAL_OFFERS);

        specialOfferReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                SpecialOffer specialOffer = dataSnapshot.getValue(SpecialOffer.class);
                specialOffers.add(specialOffer);
                setUpRecyclerView();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setUpRecyclerView() {
        rvSpecialOffers.setHasFixedSize(true);
        rvSpecialOffers.setLayoutManager(new LinearLayoutManager(this));
        rvSpecialOffers.setAdapter(new SpecialOffersAdapter(specialOffers, this));
    }

    void startAnotherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    void setUpToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setTitle(getString(R.string.txt_special_offer));
        actionBar.setHomeAsUpIndicator(R.drawable.menu_drawer_icon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            mDrawer.toggleMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, SpecialOffersDetailsActivity.class);
        intent.putExtra(Constants.DATA_SPECIAL_OFFER, specialOffers.get(position));

        startActivity(intent);
    }
}
