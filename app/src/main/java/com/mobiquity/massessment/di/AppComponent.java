package com.mobiquity.massessment.di;

import com.mobiquity.massessment.ui.main.MainActivity;
import dagger.Component;
import javax.inject.Singleton;

@SuppressWarnings("unused")
@Singleton
@Component(modules={AppModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
}
