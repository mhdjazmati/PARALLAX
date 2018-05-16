package project.parallax.emarti.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import project.parallax.emarti.R;
import project.parallax.emarti.logic.presenters.BuildingPresenter;

public class BuildingActivity extends AppCompatActivity {

    RecyclerView buildingsRecyclerView;
    BuildingPresenter presenter;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        buildingsRecyclerView = findViewById(R.id.buildings_recycler_view);
        fab = findViewById(R.id.fab_building);
        presenter = new BuildingPresenter(this,buildingsRecyclerView,fab);
    }
}
