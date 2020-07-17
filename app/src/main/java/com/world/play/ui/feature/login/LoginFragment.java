package com.world.play.ui.feature.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.world.play.R;
import com.world.play.di.ManagerComponent;
import com.world.play.models.LoginModel;
import com.world.play.ui.feature.ParentActivity;
import com.world.play.ui.feature.base.BaseViewModel;
import com.world.play.ui.feature.base.BaseViewModelFragment;
import com.world.play.utils.PreferenceUtils;
import com.world.play.utils.ThemeUtils;
import com.world.play.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends BaseViewModelFragment<LoginView> implements LoginView {

    public static final String TAG = "LoginFragment";

    private Unbinder unbinder;

    @BindView(R.id.darkThemeSwitch)
    SwitchCompat darkThemeSwitch;
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    LoginViewModel viewModel;

    private boolean currentStateDark = false;

    /**
     *
     *
     *
     *
     * Check Credentials in ResponseUtils.java File
     *
     *
     *
     *
     * */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loging_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        unbinder = ButterKnife.bind(this, view);

        currentStateDark = PreferenceUtils.getInstance().getCurrentTheme(getContext()) == ThemeUtils.THEME_DARK;
        darkThemeSwitch.setChecked(currentStateDark);

        darkThemeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentStateDark = !currentStateDark;
                ThemeUtils.updateTheme(getContext(), currentStateDark ? ThemeUtils.THEME_DARK : ThemeUtils.THEME_LIGHT);
                getActivity().recreate();
                darkThemeSwitch.setChecked(currentStateDark);
            }
        });

        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonLogin.setEnabled(isValidateButton());
            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonLogin.setEnabled(isValidateButton());
            }
        });
    }

    private boolean isValidateButton() {
        if (!Utils.emailValidator(editEmail.getText().toString().trim())) {
            return false;
        } else if (!Utils.passwordValidator(editPassword.getText().toString().trim())) {
            return false;
        }
        return true;
    }

    private boolean isValidate() {
        if (!Utils.emailValidator(editEmail.getText().toString().trim())) {
            showMessage(R.string.error_validation_email);
            return false;
        } else if (!Utils.passwordValidator(editPassword.getText().toString().trim())) {
            showMessage(R.string.error_validation_password);
            return false;
        }
        return true;
    }

    @NonNull
    @Override
    public BaseViewModel<LoginView> initializeViewModel(@NonNull ManagerComponent managerComponent) {
        managerComponent.inject(this);
        ViewModel viewModel1 = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        viewModel = (LoginViewModel) viewModel1;
        return viewModel;
    }

    public void invalidateSettings() {
        getActivity().setTheme(ThemeUtils.getThemeResFromPrefValue(PreferenceUtils.getInstance().getCurrentTheme(getContext())));
    }

    @OnClick(R.id.linear_theme_dark)
    public void onThemeChange() {
        darkThemeSwitch.performClick();
    }

    @OnClick(R.id.buttonLogin)
    public void onUserLogin() {
        if (isValidate()) {
            viewModel.onLogin(new LoginModel(editEmail.getText().toString().trim(), editPassword.getText().toString().trim()));
        }
    }

    @Override
    public void onLoginSuccess() {
        ((ParentActivity) getActivity()).setDashBoard();
    }

    @Override
    public void showError(String error) {
        showMessage(error);
    }

    @Override
    public void showLoading(boolean state) {
        if (state) showLoading();
        else hideLoading();
    }

    @Override
    public void showFailure() {
        showMessage(R.string.error_something_went_wrong);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
