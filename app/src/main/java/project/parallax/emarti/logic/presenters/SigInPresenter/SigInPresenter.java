package project.parallax.emarti.logic.presenters.SigInPresenter;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import project.parallax.emarti.ui.CreateAccountActivity;
import project.parallax.emarti.ui.SignInActivity;

/**
 * Created by MohammadSommakia on 4/2/2018.
 */

public class SigInPresenter {
    SignInActivity activity;
    CircleImageView userImageProfile;
    EditText userNameEditText;
    EditText passwordEditText;
    FloatingActionButton sigInButton;
    TextView createNewAccountButton;

    public SigInPresenter(SignInActivity signInActivity, CircleImageView userImageProfile, EditText userName,
                          EditText password, FloatingActionButton signInActionButton, TextView createAccountButton) {
        activity = signInActivity;
        this.userImageProfile = userImageProfile;
        this.userNameEditText = userName;
        this.passwordEditText = password;
        this.sigInButton = signInActionButton;
        this.createNewAccountButton = createAccountButton;
        PrepareAction();
    }

    private void PrepareAction() {

        createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CreateAccountActivity.class);
                activity.startActivity(intent);

            }
        });

    }
}
