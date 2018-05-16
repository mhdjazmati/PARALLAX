package project.parallax.emarti.logic.presenters.SigInPresenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import project.parallax.emarti.ui.SignInActivity;

/**
 * Created by MohammadSommakia on 4/8/2018.
 */

public class SignInWithFacebookPresenter {
    SignInActivity activity;
    LoginButton signUpWithFacebookButton;
    CallbackManager callbackManager;
    FirebaseAuth firebaseAuth;


    public SignInWithFacebookPresenter(SignInActivity signInActivity, LoginButton signUpWithFacebookButton) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.activity = signInActivity;
        this.signUpWithFacebookButton = signUpWithFacebookButton;
        callbackManager = CallbackManager.Factory.create();
        onStart();
        prepareAction();
    }

    private void onStart() {
//        firebaseAuth.getCurrentUser().delete();
//        if(currentUser!=null)
//        {
//            Toast.makeText(activity, "already sign in", Toast.LENGTH_SHORT).show();
//        }
    }

    private void prepareAction() {
        signUpWithFacebookButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        signUpWithFacebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(activity, "Authentication success.", Toast.LENGTH_SHORT).show();
                            Log.d("successs","successs");
                        } else {
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("failedd",task.getException().getMessage());
                        }
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
