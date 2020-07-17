package com.world.play.utils;

import com.world.play.models.LoginModel;
import com.world.play.models.StandardResponse;

public class ResponseUtils {

    private final String validEmail = "test@worldofplay.in";
    private final String validPassword = "Worldofplay@2020";
    private final String successToken = "this is success Token";

    public StandardResponse emailAndPasswordMatcher(LoginModel loginModel) {
        StandardResponse response = new StandardResponse();
        if (!loginModel.getUsername().equalsIgnoreCase(validEmail) || !loginModel.getPassword().equalsIgnoreCase(validPassword)) {
            response.setStatus("401");
            response.setErrorResponse("Email address and password is not a valid combination.");
            response.setResponse(successToken);
        } else if (loginModel.getUsername().equalsIgnoreCase(validEmail) && loginModel.getPassword().equalsIgnoreCase(validPassword)) {
            response.setStatus("200");
            response.setErrorResponse("User Login");
            response.setResponse(successToken);
        }

        return response;
    }
}
