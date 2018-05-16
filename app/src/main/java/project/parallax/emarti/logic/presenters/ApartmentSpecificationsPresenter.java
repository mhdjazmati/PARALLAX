package project.parallax.emarti.logic.presenters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import project.parallax.emarti.ui.ApartmentActivity;
import project.parallax.emarti.ui.ApartmentSpecificationsActivity;
import project.parallax.emarti.utility.AppConst;

/**
 * Created by MohammadSommakia on 4/14/2018.
 */

public class ApartmentSpecificationsPresenter {
    private static final int PICK_IMAGE_PROFILE_REQUEST = 1;
    ApartmentSpecificationsActivity activity;
    ImageView apartmentPhotoProfile;
    EditText buildingNameEditText;
    EditText floorNumberEditText;
    EditText apartmentNumberEditText;
    EditText userNameApartmentEditText;
    EditText passwordApartmentEditText;
    EditText aboutApartmentEditText;
    Button saveButton;
    Uri uriProfile;
    String downloadImgProfUrl;
    private FirebaseAuth mAuth;
    private FirebaseUser apartmentUser;

    private StorageReference storageRef;
    private Task<Void> databaseReference;
    private DatabaseReference apartmentDatabaseRefence;

    public ApartmentSpecificationsPresenter(ApartmentSpecificationsActivity activity, ImageView apartmentPhotoProfile,
                                            EditText buildingNameEditText,
                                            EditText floorNumberEditText, EditText apartmentNumberEditText,
                                            EditText aboutApartmentEditText, Button saveButton, EditText userName, EditText password) {
        this.activity = activity;
        this.apartmentPhotoProfile = apartmentPhotoProfile;
        this.buildingNameEditText = buildingNameEditText;
        this.floorNumberEditText = floorNumberEditText;
        this.apartmentNumberEditText = apartmentNumberEditText;
        this.aboutApartmentEditText = aboutApartmentEditText;
        this.userNameApartmentEditText = userName;
        this.passwordApartmentEditText = password;
        this.saveButton = saveButton;
        storageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        handleActions();
    }


    public void openGallery() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, PICK_IMAGE_PROFILE_REQUEST);
    }

    private void handleActions() {

        apartmentPhotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameApartmentEditText.getText().toString();
                String password = passwordApartmentEditText.getText().toString();
                Log.e("authentication","before auth");
                registerApartment(userName, password);
                Log.e("authentication","after auth");


//                Map userMessageMap = new HashMap();
//                userMessageMap.put(AppConst.APARTMENTS + "/" + apartmentId, apartmentMap);
//
//                rootRef.updateChildren(userMessageMap, new DatabaseReference.CompletionListener()
//
//                {
//                    @Override
//                    public void onComplete(DatabaseError databaseError, DatabaseReference
//                            databaseReference) {
//
//                        Toast.makeText(activity, "data uploaded successfully", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(activity, ApartmentActivity.class);
//                        activity.startActivity(intent);
//                    }
//                });
            }
        });
    }

    private void uploadPicture(Uri uriProfile, final String apartmentId) {
        StorageReference ProfFilePath = storageRef.child(AppConst.APARTMENT_PHOTO).child(random() + ".jpg");
        ProfFilePath.putFile(uriProfile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    downloadImgProfUrl = task.getResult().getDownloadUrl().toString();
                    databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child(AppConst.APARTMENTS).child(apartmentId).child(AppConst.APARTMENT_PHOTO_URL).setValue(downloadImgProfUrl);
                    Toast.makeText(activity, "image uploaded successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "There was some error in saving image picture", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == activity.RESULT_OK && requestCode == 1) {
            if (data != null && data.getData() != null) {
                uriProfile = data.getData();
                Bitmap userImageBitmap = null;
                try {
                    userImageBitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uriProfile);
//                    PostAndCompressThumb(uriProfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                apartmentPhotoProfile.setImageBitmap(userImageBitmap);
            }
        }
    }


    //    private String PostAndCompressThumb(final Uri result) {
//        File thumpFilePath = new File(result.getPath());
//        Bitmap thumbBitmap = null;
//        try {
//            thumbBitmap = new Compressor(activity).setMaxWidth(200).setMaxHeight(200).setQuality(50).compressToBitmap(thumpFilePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte []thumbByte = byteArrayOutputStream.toByteArray();
//        storageRef       =  FirebaseStorage.getInstance().getReference();
//        StorageReference thumbFilePathRef = storageRef.child("Profile_Images").child("thump").child(random()+".jpg");
//        UploadTask uploadTask = thumbFilePathRef.putBytes(thumbByte);
//        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                 thumbDownloadUrl = task.getResult().getDownloadUrl().toString();
//                Toast.makeText(activity, "thumpCreated", Toast.LENGTH_SHORT).show(
//                );
//            }
//        });
//        return thumbDownloadUrl;
//
//    }
    public String random()    // generate picture name
    {
        Random generator = new Random();
        StringBuilder randStringBuilder = new StringBuilder();
        int randLength = generator.nextInt(50);
        char tempChar;
        for (int i = 0; i < randLength; i++) {
            tempChar = (char) (generator.nextInt() + 32);
            randStringBuilder.append(tempChar);
        }
        return randStringBuilder.toString();
    }

    private void registerApartment(String userName, String password) {
        if (!(userName.isEmpty() && password.isEmpty())) {
            mAuth.createUserWithEmailAndPassword(userName, password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                apartmentUser = FirebaseAuth.getInstance().getCurrentUser();
                                createApartmentUser();
                                Toast.makeText(activity, "Register Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            else {
                                Log.e("authentication", task.getException().getMessage());
                                Toast.makeText(activity, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        else
            Toast.makeText(activity, "complete entry please", Toast.LENGTH_SHORT).show();
    }

    private void createApartmentUser() {
        String apartmentId = apartmentUser.getUid();
        apartmentDatabaseRefence = FirebaseDatabase.getInstance().getReference().child(AppConst.APARTMENTS).child(apartmentId);
        final String buildingName = buildingNameEditText.getText().toString();
        final String floorNumber = floorNumberEditText.getText().toString();
        final String apartmentNumber = apartmentNumberEditText.getText().toString();
        final String aboutApartment = aboutApartmentEditText.getText().toString();
        final String apartmentUserName = userNameApartmentEditText.getText().toString();
        final String apartmentPassword = passwordApartmentEditText.getText().toString();
        HashMap<String, String> apartmentMap = new HashMap();
        apartmentMap.put(AppConst.BUILDING_NAME, buildingName);
        apartmentMap.put(AppConst.FLOOR_NUMBER, floorNumber);
        apartmentMap.put(AppConst.APARTMENT_NUMBER, apartmentNumber);
        apartmentMap.put(AppConst.ABOUT_APARTMENT, aboutApartment);
        apartmentMap.put(AppConst.APARTMENT_USER_NAME, apartmentUserName);
        apartmentMap.put(AppConst.APARTMENT_PASSWORD, apartmentPassword);
        if (uriProfile != null)

        {
            uploadPicture(uriProfile, apartmentId);
            apartmentMap.put(AppConst.APARTMENT_PHOTO_URL, downloadImgProfUrl);
        }
        apartmentDatabaseRefence.setValue(apartmentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent i = new Intent(activity, ApartmentActivity.class);
                    activity.startActivity(i);
                }
            }
        });
    }
}
