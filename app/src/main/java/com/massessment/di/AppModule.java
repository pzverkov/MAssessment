package com.massessment.di;

import android.content.Context;

import com.massessment.network.ConnectivityHelper;
import com.massessment.network.NetworkApi;
import com.massessment.ui.main.MainPresenter;
import com.massessment.ui.main.MainPresenterImpl;

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
