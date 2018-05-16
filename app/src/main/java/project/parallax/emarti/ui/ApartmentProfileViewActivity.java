package project.parallax.emarti.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import project.parallax.emarti.R;
import project.parallax.emarti.logic.presenters.ApartmentProfileViewPresenter;

public class ApartmentProfileViewActivity extends AppCompatActivity {

    CircleImageView apartmentProfilePhotoView;
    TextView buildingNameTextView;
    TextView floorNumberTextView;
    TextView apartmentNumberTextView;
    TextView aboutApartmentTextView;
    TextView userNameApartmentTextView;
    TextView passwordApartmentTextView;
    Button sendMessageButton;
    ApartmentProfileViewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_profile_view);
        apartmentProfilePhotoView = findViewById(R.id.apartment_photo_view);
        buildingNameTextView = findViewById(R.id.building_name_view);
        floorNumberTextView = findViewById(R.id.floor_number_view);
        apartmentNumberTextView = findViewById(R.id.apartment_number_view);
        aboutApartmentTextView = findViewById(R.id.about_apartment_view);
        userNameApartmentTextView = findViewById(R.id.user_name_apartment_view);
        passwordApartmentTextView = findViewById(R.id.password_apartment_view);
        sendMessageButton = findViewById(R.id.send_private_message);

        presenter = new ApartmentProfileViewPresenter(this,apartmentProfilePhotoView,
                buildingNameTextView,floorNumberTextView,apartmentNumberTextView,aboutApartmentTextView
        ,userNameApartmentTextView,passwordApartmentTextView,sendMessageButton);
    }
}
