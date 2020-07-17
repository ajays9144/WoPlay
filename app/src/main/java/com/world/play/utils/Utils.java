package com.world.play.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.format.DateFormat;

import com.world.play.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String TAG = "Utils";

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean passwordValidator(String password) {
        Pattern pattern = Pattern.compile("(?=.*[A-Z])" +  //At least one upper case character (A-Z)
                "(?=.*[a-z])" +     //At least one lower case character (a-z)
                "(?=.*\\d)" +   //At least one digit (0-9)
                "(?=.*\\p{Punct})" +  //At least one special character (Punctuation)
                "^[^\\d]" + // Password should not start with a digit
                ".*" +
                "[a-zA-Z\\d]$" +
                "{8}");   // Password should not end with a special character
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String getDateFromTimeStamp(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp * 1000L);
        return DateFormat.format("dd-MM-yyyy", cal).toString();
    }
}
