package com.world.play.ui.feature.login;

import com.world.play.models.LoginModel;
import com.world.play.models.StandardResponse;
import com.world.play.ui.feature.base.BaseViewModel;
import com.world.play.utils.ResponseUtils;

import javax.inject.Inject;

public class LoginViewModel extends BaseViewModel<LoginView> {

    @Inject
    public LoginViewModel() {

    }

    public void onLogin(LoginModel loginModel) {
        ResponseUtils responseUtils = new ResponseUtils();
        StandardResponse standardResponse = responseUtils.emailAndPasswordMatcher(loginModel);
        if (standardResponse.getStatus().equalsIgnoreCase("200")) {
            getView().onLoginSuccess();
        } else {
            getView().showError(standardResponse.getErrorResponse());
        }
    }
}
