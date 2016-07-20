package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.react.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class TitleBarButtonParamsParser {
    public List<TitleBarButtonParams> parse(Bundle params) {
        List<TitleBarButtonParams> result = new ArrayList<>();
        for (String key : params.keySet()) {
            result.add(parseItem(params.getBundle(key)));
        }
        return result;
    }

    private TitleBarButtonParams parseItem(Bundle bundle) {
        TitleBarButtonParams result = new TitleBarButtonParams();
        result.label = bundle.getString("label");
        result.icon = ImageLoader.loadImage(bundle.getString("icon"));
        result.color = bundle.getInt("color");
        result.showAsAction = parseShowAsAction(bundle.getString("showAsAction"));
        result.enabled = bundle.getBoolean("enabled");
        return result;
    }

    private TitleBarButtonParams.ShowAsAction parseShowAsAction(String showAsAction) {
        switch (showAsAction) {
            case "Always":
                return TitleBarButtonParams.ShowAsAction.Always;
            case "Never":
                return TitleBarButtonParams.ShowAsAction.Never;
            case "WithText":
                return TitleBarButtonParams.ShowAsAction.WithText;
            case "IfRoom":
            default:
                return TitleBarButtonParams.ShowAsAction.IfRoom;
        }
    }
}
