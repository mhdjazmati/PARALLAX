package project.parallax.emarti.logic.presenters;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import project.parallax.emarti.R;
import project.parallax.emarti.logic.adapters.BuildingAdapter;
import project.parallax.emarti.logic.models.BuildingModel;
import project.parallax.emarti.ui.BuildingActivity;
import project.parallax.emarti.ui.GeneralChatActivity;
import project.parallax.emarti.utility.AppConst;

/**
 * Created by MohammadSommakia on 4/19/2018.
 */

public class BuildingPresenter implements BuildingAdapter.BuildingClickListener {
    private ArrayList<BuildingModel> rowListItems;
    ArrayList<String> buildingNameList;
    ArrayList<String> buildingKeyList;
    private BuildingAdapter rcAdapter;
    RecyclerView recyclerView;
    private FloatingActionButton fab;
    BuildingActivity activity;
    private DatabaseReference buildingDatabase;
    private boolean userSelect;

    public BuildingPresenter(BuildingActivity buildingActivity, RecyclerView buildingsRecyclerView, FloatingActionButton fab) {
        activity = buildingActivity;
        recyclerView = buildingsRecyclerView;
        this.fab = fab;
        rowListItems = new ArrayList<>();
        rcAdapter = new BuildingAdapter(activity, rowListItems);
        buildingDatabase = FirebaseDatabase.getInstance().getReference(AppConst.BUILDINGS);

        rcAdapter.setClickListener(this);
        setUpRecyclerView();
        setUpActions();
        addBuildingsList();
        rcAdapter.setClickListener(this);
    }

    private void setUpActions() {

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buildingNameList = new ArrayList<>();
                buildingKeyList = new ArrayList<>();
                for (int i = 0; i < rcAdapter.getItemCount(); i++) {
                    if (rcAdapter.get(i).getIsChecked()) {
                        userSelect = true;
                        String buildingName = rcAdapter.get(i).getBuildingName();
                        String buildingKey = rcAdapter.get(i).getKey();
                        buildingNameList.add(buildingName);
                        buildingKeyList.add(buildingKey);
                    }

                }
                if (userSelect) {
                    userSelect = false;
                    Intent intent = new Intent(activity, GeneralChatActivity.class);
                    intent.putStringArrayListExtra(AppConst.BUILDING_NAME, buildingNameList);
                    intent.putStringArrayListExtra(AppConst.BUILDING_KEY, buildingKeyList);
                    buildingNameList = null;
                    buildingKeyList = null;
                    activity.startActivity(intent);
                } else
                    Toast.makeText(activity, "select one building at least", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

    }

    private void addBuildingsList() {

        buildingDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    i++;
                    BuildingModel buildingModel = postSnapshot.getValue(BuildingModel.class);
                    String key = postSnapshot.getKey();
                    rcAdapter.add(new BuildingModel(R.mipmap.building, buildingModel.getNumberOfFloors(), buildingModel.getBuildingName(), true, key), i);
                }
                recyclerView.setAdapter(rcAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void itemClicked(View view, int position) {


    }
}
