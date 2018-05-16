package project.parallax.emarti.logic.presenters;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import project.parallax.emarti.logic.adapters.BuildingCommitteeAdapter;
import project.parallax.emarti.logic.models.BuildingCommitteeModel;
import project.parallax.emarti.ui.BuildingCommitteeActivity;
import project.parallax.emarti.ui.GeneralChatActivity;
import project.parallax.emarti.ui.PrivateMessageActivity;
import project.parallax.emarti.utility.AppConst;

/**
 * Created by MohammadSommakia on 4/23/2018.
 */

public class BuildingCommitteePresenter implements BuildingCommitteeAdapter.BuildingCommitteeClickListener {

    private BuildingCommitteeActivity activity;
    private RecyclerView recyclerView;
    private ArrayList<BuildingCommitteeModel> rowListItems;
    private BuildingCommitteeAdapter rcAdapter;
    private DatabaseReference userDatabase;


    public BuildingCommitteePresenter(BuildingCommitteeActivity buildingCommitteeActivity, RecyclerView recyclerView) {
        this.activity = buildingCommitteeActivity;
        this.recyclerView = recyclerView;
        rowListItems = new ArrayList<>();
        rcAdapter = new BuildingCommitteeAdapter(activity, rowListItems);
        userDatabase = FirebaseDatabase.getInstance().getReference(AppConst.USERS);
        rcAdapter.setClickListener(this);
        setUpRecyclerView();
        callApi();
    }

    private void callApi() {
        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    i++;
                    BuildingCommitteeModel committeeModel = postSnapshot.getValue(BuildingCommitteeModel.class);
                    String id = postSnapshot.getKey();
                    rcAdapter.add(new BuildingCommitteeModel(committeeModel.getUserName(), committeeModel.getImageProfile(),
                            committeeModel.getEmail(), id), i);
                }
                recyclerView.setAdapter(rcAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    @Override
    public void itemClicked(View view, int position) {
        String id = rcAdapter.get(position).getId();
        String userName = rcAdapter.get(position).getUserName();
        String profilePicture = rcAdapter.get(position).getImageProfile();
        String email = rcAdapter.get(position).getEmail();
        Intent intent;
        if (activity.getIntent().getBooleanExtra(AppConst.PRIVATE_MESSAGE, false))
            intent = new Intent(activity, PrivateMessageActivity.class);
        else
            intent = new Intent(activity, GeneralChatActivity.class);
        intent.putExtra(AppConst.ID, id);
        intent.putExtra(AppConst.USER_PHOTO_URL, profilePicture);
        intent.putExtra(AppConst.USER_NAME, userName);
        intent.putExtra(AppConst.EMAIL, email);
        intent.putExtra(AppConst.IS_SENDER, true);
        activity.startActivity(intent);
    }
}
