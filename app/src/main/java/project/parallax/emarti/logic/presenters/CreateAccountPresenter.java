package project.parallax.emarti.logic.presenters;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import project.parallax.emarti.ui.CreateAccountActivity;
import project.parallax.emarti.utility.IC;

/**
 * Created by MohammadSommakia on 4/5/2018.
 */

public class CreateAccountPresenter {

    CreateAccountActivity activity;
    EditText email;
    EditText userName;
    EditText password;
    EditText confirmPassword;
    Button createNewAccount;

    public CreateAccountPresenter(CreateAccountActivity activity, EditText email, EditText userName,
                                  EditText password, EditText confirmPassword, Button createNewAccount) {

        this.activity = activity;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.createNewAccount = createNewAccount;

        PrepareActions();

    }

    private void PrepareActions() {
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkUserCoordinate()) {

                }

            }
        });
    }

    private boolean checkUserCoordinate() {
        String emailString = email.getText().toString();
        String userNameString = userName.getText().toString();
        String passwordString = password.getText().toString();
        String confirmPasswordString = confirmPassword.getText().toString();

        if (!IC.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userNameString.isEmpty()) {
            Toast.makeText(activity, "Invalid user name", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (!IC.isValidEmail(emailString)) {
            Toast.makeText(activity, "Invalid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (passwordString.isEmpty()) {
            Toast.makeText(activity, "Enter password please", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (passwordString.toCharArray().length < 6) {
            Toast.makeText(activity, "{Password must contain at least six character", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!passwordString.equals(confirmPasswordString)) {
            Toast.makeText(activity, "Make sure that you typed password correctly", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}