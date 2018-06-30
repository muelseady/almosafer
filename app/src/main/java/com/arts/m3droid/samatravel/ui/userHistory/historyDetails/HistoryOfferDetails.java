package com.arts.m3droid.samatravel.ui.userHistory.historyDetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arts.m3droid.samatravel.Constants;
import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.model.Message;
import com.arts.m3droid.samatravel.model.RequestingOfferDetails;
import com.arts.m3droid.samatravel.model.User;
import com.arts.m3droid.samatravel.utils.FirebaseFactory;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.arts.m3droid.samatravel.Constants.RC_PHOTO_PICKER;

public class HistoryOfferDetails extends AppCompatActivity {

    @BindView(R.id.folding_cell)
    FoldingCell foldingCell;
    @BindView(R.id.tv_date_from)
    TextView tvDateFrom;
    @BindView(R.id.tv_date_to)
    TextView tvDateTo;
    @BindView(R.id.et_place_from)
    TextView etPlaceFrom;
    @BindView(R.id.et_place_to)
    TextView etPlaceTo;
    @BindView(R.id.tv_client_name)
    TextView tvClientName;
    @BindView(R.id.tv_client_number)
    TextView tvClientNumber;
    @BindView(R.id.ppl_count)
    TextView tvPplCount;
    @BindView(R.id.rv_messages)
    RecyclerView messagesRv;
    @BindView(R.id.btn_send_msg)
    ImageView btnSendMessage;
    @BindView(R.id.btn_select_image)
    ImageButton btnSelectImage;
    @BindView(R.id.et_user_input)
    EditText etMessageInput;
    @BindView(R.id.tv_youll_be_answered)
    TextView emptyView;

    private DatabaseReference mUsersChatDbRef;

    private RequestingOfferDetails offerDetails;

    private User user;

    private List<Message> messages = new ArrayList<>();

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        ButterKnife.bind(this);

        handleComingIntent();

        btnSendMessage.setOnClickListener(v -> sendButtonClicked());
        btnSelectImage.setOnClickListener(v -> onPhotoPickerClicked());

        DatabaseReference goinOfferReference =
                FirebaseFactory.getDatabase().getReference(Constants.NODE_USERS)
                        .child(user.getUid())
                        .child(Constants.NODE_GOINGON_OFFERS)
                        .child(offerDetails.getOfferKey())
                        .child("Employee");
        goinOfferReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Timber.d("employee token " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fetchMessages();
        editTextWatcher();

    }

    private void handleComingIntent() {
        Intent intent = getIntent();
        offerDetails = intent.getParcelableExtra(Constants.NODE_GOINGON_OFFERS);
        user = intent.getParcelableExtra(Constants.NODE_USERS);
        setUpViews();
    }

    private void fetchMessages() {
        String offerId = offerDetails.getOfferKey();
        String userUID = user.getUid();
        mUsersChatDbRef = FirebaseFactory.getDatabase().getReference().
                child(Constants.NODE_USERS).
                child(userUID).child(Constants.NODE_GOINGON_OFFERS).
                child(offerId).
                child(Constants.MESSAGES_NODE_DB);
        mUsersChatDbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                messages.add(message);
                setUpMessagesRecyclerView();
            }

            //region unused
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                //Todo called if the data already in the db has been changed
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //Todo called when the db data has been deleted
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //Todo called when the data has been moved to another node (json object in db)
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Todo Called when there's an error most of the time there's no authentication
            }
            //endregion
        });
    }

    private void setUpViews() {
        tvClientName.setText(offerDetails.getUserName());
        tvClientNumber.setText(offerDetails.getUserNumber());
        tvDateFrom.setText(offerDetails.getDateFrom());
        tvDateTo.setText(offerDetails.getDateTo());
        tvPplCount.setText(String.valueOf(pplCount()));
        if (offerDetails.getPlaceFrom() == null) {
            String placeFrom = "بلدك";
            etPlaceFrom.setText(placeFrom);
            etPlaceTo.setText(offerDetails.getOfferName());
        } else {
            etPlaceFrom.setText(offerDetails.getPlaceFrom());
            etPlaceTo.setText(offerDetails.getPlaceTo());
        }

        // attach click listener to folding cell
        foldingCell.setOnClickListener(v -> foldingCell.toggle(false));
    }

    private int pplCount() {
        return offerDetails.getChildren() + offerDetails.getAdults() +
                offerDetails.getInfants() + offerDetails.getOver65();
    }


    public void sendButtonClicked() {
        String messageBody = etMessageInput.getText().toString().trim();
        Message message = new Message(user.getUid(), messageBody, null,
                Constants.CAT_USER, user.getName());
        etMessageInput.setText("");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(etMessageInput.getWindowToken(), 0);
        mUsersChatDbRef.push().setValue(message);
    }

    private void setUpMessagesRecyclerView() {
        if (messages.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            return;
        } else {
            emptyView.setVisibility(View.GONE);
        }
        messagesRv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messagesRv.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setStackFromEnd(true);
        MessagesAdapter messageAdapter = new MessagesAdapter(messages);
        messagesRv.setAdapter(messageAdapter);
    }

    //region photoHandling
    private void onPhotoPickerClicked() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(getString(R.string.img));
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser
                (intent, getString(R.string.image_picker)), RC_PHOTO_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                handleSelectedImage(selectedImage);
            }
        }
    }

    private void handleSelectedImage(Uri selectedImage) {
        if (selectedImage == null) return;
        FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();

        //Todo create a valid name by avoid using these chars #, [, ], *, or ?

        // Create a storage reference from our app
        StorageReference storageRef = mFirebaseStorage.getReference().child(Constants.IMAGES_REF);
        StorageReference photoRef = storageRef.child(selectedImage.getLastPathSegment());
        UploadTask uploadTask = photoRef.putFile(selectedImage);


        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
        }).addOnSuccessListener(taskSnapshot -> Toast.makeText(this, R.string.txt_image_done, Toast.LENGTH_SHORT).show());

        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }

            // Continue with the task to get the download URL
            return photoRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) { // Successful upload retrieve download string
                String imageUrl = task.getResult().toString();
                Message message = new Message(user.getName(),
                        null,
                        imageUrl, Constants.CAT_USER, user.getName());
                mUsersChatDbRef.push().setValue(message);
                btnSelectImage.setEnabled(false);
            } else { // Error happen this's how gonna handle it
                Toast.makeText(this, R.string.txt_error_adding_image, Toast.LENGTH_LONG).show();
                btnSelectImage.setEnabled(true);
            }
        });

    }
    //endregion

    private void editTextWatcher() {
        // Only unable the send button when there's text to send
        etMessageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etMessageInput.getText().length() > 0) {
                    btnSendMessage.setEnabled(true);
                } else {
                    btnSendMessage.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        // Set maximum length for a single message not exceed 1000.
        etMessageInput.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(1000)});
    }
}
