package project.parallax.emarti.logic.presenters.SigInPresenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import project.parallax.emarti.R;
import project.parallax.emarti.ui.ApartmentActivity;
import project.parallax.emarti.ui.SignInActivity;
import project.parallax.emarti.utility.AppConst;
import project.parallax.emarti.utility.PreferenceManager;

/**
 * Created by MohammadSommakia on 4/7/2018.
 */

public class SigInWithGmailPresenter {

    FirebaseAuth firebaseAuth;
    final static int RC_SIGN_IN = 2;
    GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient.Builder googleApiClient;
    FirebaseAuth.AuthStateListener authStateListener;
    SignInActivity activity;
    Button gmailButton;
    DatabaseReference databaseReference;


    public SigInWithGmailPresenter(SignInActivity activity, Button gmailButton) {
        this.activity = activity;
        firebaseAuth = FirebaseAuth.getInstance();
        prepareSigInProcess();
        firebaseAuth.addAuthStateListener(authStateListener);
        this.gmailButton = gmailButton;
        prepareAction();
    }

    private void prepareAction() {
        gmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void prepareSigInProcess() {
        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
//                    Toast.makeText(activity, "already logged", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(activity, ApartmentActivity.class);
//                    activity.startActivity(i);

                }
            }
        };


        googleApiClient = new GoogleApiClient.Builder(activity).enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(activity, "fail", Toast.LENGTH_SHORT).show();
            }
        });


        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Toast.makeText(activity, "success with Gmail", Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {


        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity, "success with Firebase", Toast.LENGTH_SHORT).show();
                            final FirebaseUser user = firebaseAuth.getCurrentUser();
                            PreferenceManager preferenceManager = new PreferenceManager(activity);
                            preferenceManager.putString(AppConst.ID, user.getUid());
                            preferenceManager.apply();
                            databaseReference = FirebaseDatabase.getInstance().getReference().child(AppConst.USERS).child(user.getUid());
                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("userName", user.getDisplayName());
                            userMap.put("imageProfile", user.getPhotoUrl().getPath().toString());
                            userMap.put("phone", user.getPhoneNumber());
                            userMap.put("email", user.getEmail());
                            databaseReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        String deviceToken = FirebaseInstanceId.getInstance().getToken();
                                        databaseReference = FirebaseDatabase.getInstance().getReference();
                                        databaseReference.child(AppConst.USERS)
                                                .child(user.getUid()).child(AppConst.TOKEN_ID)
                                                .setValue(deviceToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Intent i = new Intent(activity, ApartmentActivity.class);
                                                activity.startActivity(i);
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
    }
}


