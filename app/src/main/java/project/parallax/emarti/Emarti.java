package project.parallax.emarti;

/**
 * Created by MohammadSommakia on 4/26/2018.
 */
import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Emarti extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
