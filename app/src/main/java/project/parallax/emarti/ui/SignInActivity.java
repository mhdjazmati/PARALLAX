package project.parallax.emarti.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import project.parallax.emarti.R;
import project.parallax.emarti.logic.presenters.SigInPresenter.SigInPresenter;
import project.parallax.emarti.logic.presenters.SigInPresenter.SigInWithGmailPresenter;
import project.parallax.emarti.logic.presenters.SigInPresenter.SignInWithFacebookPresenter;
import project.parallax.emarti.utility.AppConst;
import project.parallax.emarti.utility.PreferenceManager;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.user_image)
    CircleImageView userImageProfile;

    @BindView(R.id.user_name)
    EditText userName;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.email_sign_in_button)
    FloatingActionButton signinActionButton;

    @BindView(R.id.create_account_link)
    TextView createAccountButton;

    @BindView(R.id.sign_up_with_gmail_button)
    Button signUpWithGmailButton;

    @BindView(R.id.sign_up_with_facebook_button)
    LoginButton signUpWithFacebookButton;

    SigInPresenter sigInPresenter;
    SigInWithGmailPresenter gmailPresenter;
    SignInWithFacebookPresenter facebookPresenter;
    CallbackManager callbackManager;
    SignInActivity activity;
    FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        ButterKnife.bind(this);
        signinActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(userName.getText().toString(), password.getText().toString());
            }
        });

//        facebookPresenter = new SignInWithFacebookPresenter(this, signUpWithFacebookButton);

        gmailPresenter = new SigInWithGmailPresenter(this, signUpWithGmailButton);

//            if(firebaseAuth.getCurrentUser()!=null)
//            {
//                firebaseAuth.getCurrentUser().delete();
//            }
//        signUpWithFacebookButton.setReadPermissions(Arrays.asList("public_profile", "email"));
//        signUpWithFacebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Toast.makeText(SignInActivity.this, "success", Toast.LENGTH_SHORT).show();
//                handleFacebookAccessToken(loginResult.getAccessToken());

//        prepareAction();
    }

//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });


    private void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
                    preferenceManager.putString(AppConst.ID, task.getResult().getUser().getUid());
                    preferenceManager.apply();
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child(AppConst.APARTMENTS)
                            .child(task.getResult().getUser().getUid()).child(AppConst.TOKEN_ID)
                            .setValue(deviceToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent homeIntent = new Intent(SignInActivity.this, ChatTypeActivity.class);
                            startActivity(homeIntent);
                            finish();
                            Toast.makeText(SignInActivity.this, "Sign in Successfully",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    Toast.makeText(SignInActivity.this, "Cannot sign in.Please check your password or your email and try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gmailPresenter.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(SignInActivity.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("failedd", task.getException().getMessage());
                        }
                    }
                });
    }

}
