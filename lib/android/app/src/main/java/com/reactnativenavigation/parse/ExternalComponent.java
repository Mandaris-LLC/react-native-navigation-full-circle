package com.reactnativenavigation.parse;

import com.reactnativenavigation.parse.params.NullText;
import com.reactnativenavigation.parse.params.Text;
import com.reactnativenavigation.parse.parsers.TextParser;

import org.json.JSONObject;

public class ExternalComponent {

    public Text classCreator = new NullText();

    public static ExternalComponent parse(JSONObject json) {
        ExternalComponent options = new ExternalComponent();
        if (json == null) {
            return options;
        }

        options.classCreator = TextParser.parse(json, "classCreator");
        if (!options.classCreator.hasValue()) {
            throw new RuntimeException("ExternalClass must declare classCreator - a fully qualified method name");
        }

        return options;
    }

    public void mergeWith(ExternalComponent other) {
        if (other.classCreator.hasValue()) {
            classCreator = other.classCreator;
        }
    }

    public void mergeWithDefault(ExternalComponent defaultOptions) {
        if (!classCreator.hasValue()) {
            classCreator = defaultOptions.classCreator;
        }
    }
}
