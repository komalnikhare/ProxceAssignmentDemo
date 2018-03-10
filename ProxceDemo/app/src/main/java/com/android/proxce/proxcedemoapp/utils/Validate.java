package com.android.proxce.proxcedemoapp.utils;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.widget.EditText;

/**
 * Created by Komal on 3/10/2018.
 */

public class Validate {

    public static boolean validateEmails(Context mContext, TextInputLayout textInputLayout, EditText editText){
        CharSequence validate = editText.getText().toString().trim();
        if (validate == null || validate.length() == 0){
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("Enter email");
            editText.setFocusable(true);
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(validate).matches()){
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("Enter valid email");
            editText.setFocusable(true);
            return false;
        }

        return true;
    }

    public static boolean validatePassword(Context mContext, TextInputLayout textInputLayout, EditText editText){
        String validate = editText.getText().toString().trim();
        if (validate == null ||  validate.length() == 0){
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("Enter valied mobile number");
            editText.setFocusable(true);
            return false;
        }

        return true;
    }

}
