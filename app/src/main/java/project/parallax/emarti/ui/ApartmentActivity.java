package project.parallax.emarti.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import project.parallax.emarti.R;
import project.parallax.emarti.logic.presenters.ApartmentPresenter;

public class ApartmentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ApartmentPresenter apartmentPresenter;
    FloatingActionButton fab;
    FloatingActionButton chatFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.home_recycler_view);
        chatFab = findViewById(R.id.chat_fab);
        fab = findViewById(R.id.fab);
        apartmentPresenter = new ApartmentPresenter(this, recyclerView, fab,chatFab);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
