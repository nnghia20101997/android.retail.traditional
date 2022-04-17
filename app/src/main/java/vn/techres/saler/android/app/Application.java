package vn.techres.saler.android.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.core.app.base.BaseApplication;

/**
 * ================================================
 * Created by HuuThang on 17/5/2021 10:40
 * ================================================
 */

public class Application extends BaseApplication {
    private static Application mInstance;

    public static Bundle bundle;

    public static SharedPreferences sharedPref;

    public static synchronized Application getInstance() {
        return mInstance;
    }

    public static synchronized Bundle getBundle() {
        return bundle;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        bundle = new Bundle();
    }
}
