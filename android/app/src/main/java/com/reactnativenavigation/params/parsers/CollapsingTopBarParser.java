package com.reactnativenavigation.params.parsers;

import android.graphics.Color;
import android.os.Bundle;

import com.reactnativenavigation.params.CollapsingTopBarParams;
import com.reactnativenavigation.params.StyleParams;

public class CollapsingTopBarParser extends Parser {
    private Bundle params;

    public CollapsingTopBarParser(Bundle params) {
        this.params = params;
    }

    public CollapsingTopBarParams parse() {
        if (!hasLocalImageResource() && !hasImageUrl()) {
            return null;
        }

        CollapsingTopBarParams result = new CollapsingTopBarParams();
        if (hasLocalImageResource()) {
            result.imageUri = params.getString("collapsingToolBarImage");
        } else {
            result.imageUri = params.getString("collapsingToolBarImage");
        }
        result.scrimColor = getColor(params, "collapsingToolBarCollapsedColor", new StyleParams.Color(Color.WHITE));
        return result;
    }

    private boolean hasLocalImageResource() {
        return params.containsKey("collapsingToolBarImage");
    }

    private boolean hasImageUrl() {
        return params.containsKey("collapsingToolBarImageUrl");
    }
}
