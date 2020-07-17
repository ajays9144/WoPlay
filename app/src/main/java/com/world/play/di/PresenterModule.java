package com.world.play.di;

import com.world.play.ui.feature.ParentContract;
import com.world.play.ui.feature.ParentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule
{
    @Provides
    public ParentContract.Presenter providerParentContract() {
        ParentViewModel parentViewModel = new ParentViewModel();
        return parentViewModel;
    }
}
