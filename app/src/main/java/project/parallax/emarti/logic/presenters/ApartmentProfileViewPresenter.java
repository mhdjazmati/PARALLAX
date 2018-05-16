package project.parallax.emarti.logic.presenters;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import project.parallax.emarti.ui.ApartmentProfileViewActivity;
import project.parallax.emarti.ui.PrivateMessageActivity;
import project.parallax.emarti.utility.AppConst;

/**
 * Created by MohammadSommakia on 4/17/2018.
 */

public class ApartmentProfileViewPresenter {
    ApartmentProfileViewActivity activity;
    CircleImageView apartmentProfilePhotoView;
    TextView buildingNameTextView;
    TextView floorNumberTextView;
    TextView apartmentNumberTextView;
    TextView aboutApartmentTextView;
    TextView userNameApartmentTextView;
    TextView passwordApartmentTextView;
    private Button sendMessageButton;

    public ApartmentProfileViewPresenter(ApartmentProfileViewActivity apartmentProfileViewActivity, CircleImageView apartmentProfilePhotoView,
                                         TextView buildingNameTextView, TextView floorNumberTextView,
                                         TextView apartmentNumberTextView, TextView aboutApartmentTextView,
                                         TextView userNameApartmentTextView, TextView passwordApartmentTextView, Button sendMessageButton) {
        this.activity = apartmentProfileViewActivity;
        this.apartmentProfilePhotoView = apartmentProfilePhotoView;
        this.buildingNameTextView = buildingNameTextView;
        this.floorNumberTextView = floorNumberTextView;
        this.apartmentNumberTextView = apartmentNumberTextView;
        this.aboutApartmentTextView = aboutApartmentTextView;
        this.userNameApartmentTextView = userNameApartmentTextView;
        this.passwordApartmentTextView = passwordApartmentTextView;
        this.sendMessageButton = sendMessageButton;
        handleAction();
    }

    private void handleAction() {
        String photoUrl = activity.getIntent().getStringExtra(AppConst.APARTMENT_PHOTO_URL);
        String buildingName = activity.getIntent().getStringExtra(AppConst.BUILDING_NAME);
        String floorNumber = activity.getIntent().getStringExtra(AppConst.FLOOR_NUMBER);
        final String apartmentNumber = activity.getIntent().getStringExtra(AppConst.APARTMENT_NUMBER);
        String aboutApartment = activity.getIntent().getStringExtra(AppConst.ABOUT_APARTMENT);
        String userName = activity.getIntent().getStringExtra(AppConst.APARTMENT_USER_NAME);
        String password = activity.getIntent().getStringExtra(AppConst.APARTMENT_PASSWORD);
        final String apartmentId = activity.getIntent().getStringExtra(AppConst.APARTMENT_ID);

        Glide.with(activity).load(photoUrl).into(apartmentProfilePhotoView);
        buildingNameTextView.setText(buildingName);
        floorNumberTextView.setText(floorNumber);
        apartmentNumberTextView.setText(apartmentNumber);
        aboutApartmentTextView.setText(aboutApartment);
        userNameApartmentTextView.setText(userName);
        passwordApartmentTextView.setText(password);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PrivateMessageActivity.class);
                intent.putExtra(AppConst.ID,apartmentId);
                intent.putExtra(AppConst.USER_NAME,apartmentNumber);
                activity.startActivity(intent);


            }
        });
    }
}
