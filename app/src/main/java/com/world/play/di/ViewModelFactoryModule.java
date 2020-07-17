package com.world.play.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule
{
    @NonNull
    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(@NonNull ViewModelProviderFactory viewModelFactory);
}
