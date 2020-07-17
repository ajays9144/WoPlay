package com.world.play.ui.feature;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.world.play.R;
import com.world.play.di.ManagerComponent;
import com.world.play.ui.feature.base.BaseActivityViewModel;
import com.world.play.ui.feature.dashboard.DashBoardFragment;
import com.world.play.ui.feature.login.LoginFragment;
import com.world.play.utils.PreferenceUtils;
import com.world.play.utils.ThemeUtils;

import javax.inject.Inject;

public class ParentActivity extends BaseActivityViewModel<ParentContract.Presenter> implements ParentContract.View {

    @Inject
    ParentContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferenceUtils.getInstance().getCurrentTheme(ParentActivity.this) == ThemeUtils.THEME_LIGHT) {
            setTheme(R.style.Theme_Light);
        } else {
            setTheme(R.style.Theme_Dark);
        }

        setContentView(R.layout.parent_activity_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new LoginFragment()).commit();
        } else {
            LoginFragment frag = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout);
            if (frag != null) frag.invalidateSettings();
        }
    }

    @Override
    public ParentContract.Presenter injectPresenter(ManagerComponent managerComponent) {
        managerComponent.inject(this);
        return presenter;
    }

    public void setDashBoard() {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_layout, new DashBoardFragment()).commit();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean state) {

    }

    @Override
    public void showFailure() {

    }
}
