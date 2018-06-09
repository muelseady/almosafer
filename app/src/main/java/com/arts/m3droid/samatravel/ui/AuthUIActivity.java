package com.arts.m3droid.samatravel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.arts.m3droid.samatravel.R;
import com.arts.m3droid.samatravel.ui.mainOffers.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.Objects;

import static com.arts.m3droid.samatravel.Constants.RC_SIGN_IN;

public class AuthUIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signIn();

    }

    private void signIn() {

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.reg_app_logo)
                        .setIsSmartLockEnabled(false)
                        .setTheme(R.style.auth_theme)

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
            handleSignInResponse(resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startSignedInActivity();
            finish();
        }
    }

    private void handleSignInResponse(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        // Successfully signed in
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, R.string.txt_welcom, Toast.LENGTH_SHORT).show();
            startSignedInActivity();
            finish();
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                Toast.makeText(this, R.string.txt_sign_in_again, Toast.LENGTH_SHORT).show();

                return;
            }

            if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                Toast.makeText(this, R.string.txt_require_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startSignedInActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

}
