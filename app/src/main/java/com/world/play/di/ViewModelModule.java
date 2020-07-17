package com.world.play.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.world.play.ui.feature.dashboard.DashBoardViewModel;
import com.world.play.ui.feature.login.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @ViewModelKey(LoginViewModel.class)
    @Binds
    @IntoMap
    @NonNull
    public abstract ViewModel bindLoginViewModel(@NonNull LoginViewModel loginViewModel);

    @ViewModelKey(DashBoardViewModel.class)
    @Binds
    @IntoMap
    @NonNull
    public abstract ViewModel bindDashBoardViewModel(@NonNull DashBoardViewModel dashBoardViewModel);
}
