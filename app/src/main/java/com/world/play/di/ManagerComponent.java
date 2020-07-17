package com.world.play.di;

import com.world.play.AppController;
import com.world.play.ui.feature.ParentActivity;
import com.world.play.ui.feature.dashboard.DashBoardFragment;
import com.world.play.ui.feature.login.LoginFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, UtilsModule.class, PresenterModule.class, ViewModelFactoryModule.class, ViewModelModule.class})
public interface ManagerComponent {
    void inject(AppController appController);

    void inject(ParentActivity parentActivity);

    void inject(LoginFragment loginFragment);

    void inject(DashBoardFragment dashBoardFragment);
}
