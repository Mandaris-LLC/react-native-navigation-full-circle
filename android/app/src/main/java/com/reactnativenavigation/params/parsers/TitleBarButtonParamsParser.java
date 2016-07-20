package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.react.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class TitleBarButtonParamsParser {
    public static List<TitleBarButtonParams> parse(Bundle params) {
        List<TitleBarButtonParams> result = new ArrayList<>();
        for (String key : params.keySet()) {
            result.add(parseItem(params.getBundle(key)));
        }
        return result;
    }

    private static TitleBarButtonParams parseItem(Bundle bundle) {
        TitleBarButtonParams result = new TitleBarButtonParams();
        result.label = bundle.getString("label");
        result.icon = ImageLoader.loadImage(bundle.getString("icon"));
        result.color = bundle.getInt("color");
        result.showAsAction = parseShowAsAction(bundle.getString("showAsAction"));
        result.enabled = bundle.getBoolean("enabled");
        return result;
    }

    private static TitleBarButtonParams.ShowAsAction parseShowAsAction(String showAsAction) {
        switch (showAsAction) {
            case "always":
                return TitleBarButtonParams.ShowAsAction.Always;
            case "never":
                return TitleBarButtonParams.ShowAsAction.Never;
            case "withText":
                return TitleBarButtonParams.ShowAsAction.WithText;
            case "ifRoom":
            default:
                return TitleBarButtonParams.ShowAsAction.IfRoom;
        }
    }
}
