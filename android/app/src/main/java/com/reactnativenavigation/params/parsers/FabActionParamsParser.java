package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.FabActionParams;
import com.reactnativenavigation.params.StyleParams;
import com.reactnativenavigation.react.ImageLoader;

public class FabActionParamsParser extends Parser {
    public FabActionParams parse(Bundle params) {
        FabActionParams fabActionParams = new FabActionParams();
        fabActionParams.id = params.getString("id");
        fabActionParams.icon = ImageLoader.loadImage(params.getString("icon"));
        fabActionParams.backgroundColor = StyleParams.Color.parse(params.getString("backgroundColor"));
        return fabActionParams;
    }
}
