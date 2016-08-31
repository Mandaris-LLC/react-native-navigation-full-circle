package com.reactnativenavigation.params.parsers;

import android.os.Bundle;
import android.support.design.widget.Snackbar;

import com.reactnativenavigation.params.AppStyle;
import com.reactnativenavigation.params.SnackbarParams;

public class SnackbarParamsParser extends Parser {
    public SnackbarParams parse(Bundle params) {
        SnackbarParams result = new SnackbarParams();
        result.text = params.getString("text");
        result.textColor = getColor(params, "textColor", AppStyle.appStyle.snackbarTextColor);
        result.buttonText = params.getString("buttonText");
        result.buttonColor = getColor(params, "buttonColor", AppStyle.appStyle.snackbarButtonColor);
        result.duration = getDuration(params.getString("duration", "short"));
        return result;
    }

    private int getDuration(String duration) {
        switch (duration) {
            case "short":
                return Snackbar.LENGTH_SHORT;
            case "long":
                return Snackbar.LENGTH_LONG;
            case "indefinite":
                return Snackbar.LENGTH_INDEFINITE;
            default:
                return Snackbar.LENGTH_SHORT;
        }
    }
}
