package project.parallax.emarti.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import project.parallax.emarti.R;
import project.parallax.emarti.logic.presenters.ApartmentSpecificationsPresenter;

public class ApartmentSpecificationsActivity extends AppCompatActivity {

    ImageView apartmentPhotoProfile;
    EditText buildingNameEditText;
    EditText floorNumberEditText;
    EditText apartmentNumberEditText;
    EditText aboutApartmentEditText;
    EditText userName;
    EditText password;
    Button saveButton;
    ApartmentSpecificationsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_specifications);
        apartmentPhotoProfile = findViewById(R.id.apartment_image);
        buildingNameEditText = findViewById(R.id.building_name_edit_text);
        floorNumberEditText = findViewById(R.id.floor_number_edit_text);
        apartmentNumberEditText = findViewById(R.id.apartment_number_edit_text);
        aboutApartmentEditText = findViewById(R.id.about_apartment_edit_text);
        userName = findViewById(R.id.apartment_user_name);
        password = findViewById(R.id.apartment_password);
        saveButton = findViewById(R.id.save_button_apartment_specification);
        presenter = new ApartmentSpecificationsPresenter(this, apartmentPhotoProfile,
                buildingNameEditText, floorNumberEditText,
                apartmentNumberEditText, aboutApartmentEditText, saveButton,userName,password);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }
}
