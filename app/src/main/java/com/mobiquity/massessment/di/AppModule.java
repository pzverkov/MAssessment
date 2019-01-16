package com.mobiquity.massessment.di;

import android.content.Context;

import com.mobiquity.massessment.network.ConnectivityHelper;
import com.mobiquity.massessment.network.NetworkApi;
import com.mobiquity.massessment.ui.main.MainPresenter;
import com.mobiquity.massessment.ui.main.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public ConnectivityHelper provideConnectivityHelper() {
        return new ConnectivityHelper(context);
    }

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter(NetworkApi api, ConnectivityHelper helper) {
        return new MainPresenterImpl(api, helper);
    }
}
