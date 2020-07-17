package com.world.play;

import android.app.Application;

import com.world.play.di.AppModule;
import com.world.play.di.DaggerManagerComponent;
import com.world.play.di.ManagerComponent;
import com.world.play.di.UtilsModule;

public class AppController extends Application
{
    public static final int API_STATUS_CODE_LOCAL_ERROR = 4012;

    ManagerComponent managerComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        managerComponent = createComponent();
        managerComponent.inject(this);
    }

    public ManagerComponent getManagerComponent() {
        return managerComponent;
    }

    private ManagerComponent createComponent() {
        return DaggerManagerComponent.builder()
                .appModule(new AppModule(this))
                .utilsModule(new UtilsModule()).build();
    }
}
