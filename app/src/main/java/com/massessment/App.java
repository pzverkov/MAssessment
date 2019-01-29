package com.massessment;

import android.app.Application;
import android.util.Log;
import com.massessment.di.AppComponent;
import com.massessment.di.AppModule;
import com.massessment.di.DaggerAppComponent;
import com.massessment.di.NetworkModule;

import timber.log.Timber;


public class App extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public AppComponent getComponent() {
        return mAppComponent;
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        private static final String TAG = "Product application";

        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            if (t != null) {
                if (priority == Log.ERROR) {
                    Log.e(TAG, t.toString());
                } else if (priority == Log.WARN) {
                    Log.w(TAG, t.toString());
                }
            }
        }
    }
}
