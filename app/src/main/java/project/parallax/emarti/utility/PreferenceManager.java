package project.parallax.emarti.utility;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by informatic on 17/02/2018.
 */

public class PreferenceManager {

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private Context context;

    private static PreferenceManager instance;
    private static final String PREFS_NAME = "AppPreferences";

    public PreferenceManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = pref.edit();
    }

    public static PreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceManager(context);
        }
        return instance;
    }

    public static void putString(String key, String value){
        editor.putString(key, value);
    }

    public static String getString(String key, String def){
        return pref.getString(key, def);
    }

    public static void putBoolean(String key, boolean value){
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key, boolean def){
        return pref.getBoolean(key, def);
    }

    public static void apply(){
        editor.apply();
    }
}
