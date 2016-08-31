package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.AppStyle;
import com.reactnativenavigation.params.SnackbarParams;
import com.reactnativenavigation.params.StyleParams;

public class SnackbarParamsParser extends Parser {
    public SnackbarParams parse(Bundle params) {
        SnackbarParams result = new SnackbarParams();
        result.text = params.getString("text");
        result.textColor = getColor(params, "textColor", AppStyle.appStyle.snackbarTextColor);
        result.buttonText = params.getString("buttonText");
        result.buttonColor = getColor(params, "buttonColor", AppStyle.appStyle.snackbarButtonColor);
        return result;
    }
}
