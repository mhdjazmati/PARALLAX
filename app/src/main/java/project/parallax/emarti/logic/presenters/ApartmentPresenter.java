package project.parallax.emarti.logic.presenters;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import project.parallax.emarti.logic.adapters.ApartmentAdapter;
import project.parallax.emarti.logic.models.ApartmentModel;
import project.parallax.emarti.ui.ApartmentActivity;
import project.parallax.emarti.ui.ApartmentProfileViewActivity;
import project.parallax.emarti.ui.ApartmentSpecificationsActivity;
import project.parallax.emarti.ui.BuildingActivity;
import project.parallax.emarti.utility.AppConst;

/**
 * Created by MohammadSommakia on 4/12/2018.
 */

public class ApartmentPresenter implements ApartmentAdapter.ApartmentClickListener {

    private ApartmentActivity activity;
    private RecyclerView recyclerView;
    private ArrayList<ApartmentModel> rowListItems;
    private ApartmentAdapter rcAdapter;
    private FloatingActionButton fab;
    private FloatingActionButton chatFab;
    private DatabaseReference apartmentDatabase;


    public ApartmentPresenter(ApartmentActivity homeActivity, RecyclerView recyclerView,
                              FloatingActionButton chatFab, FloatingActionButton fab) {
        this.activity = homeActivity;
        this.recyclerView = recyclerView;
        this.fab = fab;
        this.chatFab = chatFab;

        apartmentDatabase = FirebaseDatabase.getInstance().getReference(AppConst.APARTMENTS);
        rowListItems = new ArrayList<>();
        rcAdapter = new ApartmentAdapter(activity, rowListItems);
        rcAdapter.setClickListener(this);
        setUpAction();
        setUpRecyclerView();
        callApi();
    }

    private void callApi() {
        apartmentDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    i++;
                    String apartmentId = postSnapshot.getKey();
                    ApartmentModel apartmentModel = postSnapshot.getValue(ApartmentModel.class);
                    rcAdapter.add(new ApartmentModel(apartmentId,apartmentModel.getApartmentPhotoUrl(), apartmentModel.getFloorNumber(),
                            apartmentModel.getBuildingName(), apartmentModel.getApartmentNumber(),
                            apartmentModel.getAboutApartment(),apartmentModel.getUserName(),apartmentModel.getPassword()), i);
                }
                recyclerView.setAdapter(rcAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUpAction() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ApartmentSpecificationsActivity.class);
                activity.startActivity(intent);
            }
        });

        chatFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BuildingActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
    }

    @Override
    public void itemClicked(View view, int position) {
        String uriPhoto = rcAdapter.get(position).getApartmentPhotoUrl();
        String BuildingName = rcAdapter.get(position).getBuildingName();
        String floorNumber = rcAdapter.get(position).getFloorNumber();
        String apartmentNumber = rcAdapter.get(position).getApartmentNumber();
        String aboutApartment = rcAdapter.get(position).getAboutApartment();
        String apartmentUserName = rcAdapter.get(position).getUserName();
        String apartmentPassword = rcAdapter.get(position).getPassword();
        String apartmentId = rcAdapter.get(position).getApartmentId();

        Intent intent = new Intent(activity, ApartmentProfileViewActivity.class);
        intent.putExtra(AppConst.APARTMENT_ID, apartmentId);
        intent.putExtra(AppConst.APARTMENT_PHOTO_URL, uriPhoto);
        intent.putExtra(AppConst.BUILDING_NAME, BuildingName);
        intent.putExtra(AppConst.FLOOR_NUMBER, floorNumber);
        intent.putExtra(AppConst.APARTMENT_NUMBER, apartmentNumber);
        intent.putExtra(AppConst.ABOUT_APARTMENT, aboutApartment);
        intent.putExtra(AppConst.APARTMENT_USER_NAME,apartmentUserName );
        intent.putExtra(AppConst.APARTMENT_PASSWORD, apartmentPassword);
        activity.startActivity(intent);
    }


//    @Override
//    public void itemClicked(View view, int position) {
//        if (rowListItems != null) {
//            String buildingName = rowListItems.get(position).getBuildingName();
//            String floorNumber = rowListItems.get(position).getFloorNumber();
//            String imageUri = rowListItems.get(position).getApartmentPhotoUrl();
//        }
//    }
}
