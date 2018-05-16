package project.parallax.emarti.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.parallax.emarti.R;
import project.parallax.emarti.logic.presenters.CreateAccountPresenter;

public class CreateAccountActivity extends AppCompatActivity {

    CreateAccountActivity activity;
    CreateAccountPresenter createAccountPresenter;

    @BindView(R.id.create_account_email_edit_text)
    EditText email;

    @BindView(R.id.create_account_username_edit_text)
    EditText userName;

    @BindView(R.id.create_account_password_edit_text)
    EditText password;

    @BindView(R.id.create_accoynt_confirm_password_edit_text)
    EditText confirmPassword;

    @BindView(R.id.create_account_button)
    Button createNewAccount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
        createAccountPresenter = new CreateAccountPresenter(this, email, userName, password, confirmPassword, createNewAccount);
    }
}
