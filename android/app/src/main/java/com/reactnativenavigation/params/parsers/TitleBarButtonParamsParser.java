package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.ScreenStyleParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.react.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class TitleBarButtonParamsParser extends Parser {
    public static List<TitleBarButtonParams> parse(Bundle params) {
        List<TitleBarButtonParams> result = new ArrayList<>();
        if (params == null) {
            return result;
        }

        for (String key : params.keySet()) {
            result.add(parseItem(params.getBundle(key)));
        }
        return result;
    }

    private static TitleBarButtonParams parseItem(Bundle bundle) {
        TitleBarButtonParams result = new TitleBarButtonParams();
        result.label = bundle.getString("title");
        if (hasKey(bundle,"icon")) {
            result.icon = ImageLoader.loadImage(bundle.getString("icon"));
        }
        result.color = new ScreenStyleParams.Color(ColorParser.parse(bundle.getString("color")));
        result.showAsAction = parseShowAsAction(bundle.getString("showAsAction"));
        result.enabled = bundle.getBoolean("enabled", true);
        return result;
    }

    private static TitleBarButtonParams.ShowAsAction parseShowAsAction(String showAsAction) {
        if (showAsAction == null) {
            return TitleBarButtonParams.ShowAsAction.IfRoom;
        }

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
