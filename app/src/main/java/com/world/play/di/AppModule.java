package com.world.play.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule
{
    private Application application;

    public AppModule() {

    }

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Application application() {
        return this.application;
    }
}
