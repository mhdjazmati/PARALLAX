package project.parallax.emarti.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import project.parallax.emarti.R;
import project.parallax.emarti.logic.presenters.BuildingCommitteePresenter;

public class BuildingCommitteeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BuildingCommitteePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_committee);
        recyclerView = findViewById(R.id.buildings_committe_recycler_view);
        presenter = new BuildingCommitteePresenter(this,recyclerView);
    }
}
