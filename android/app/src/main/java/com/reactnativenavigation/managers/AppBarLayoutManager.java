package com.reactnativenavigation.managers;

import android.support.design.widget.AppBarLayout;
import android.view.View;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

/**
 * Created by guyc on 08/03/16.
 */
public class AppBarLayoutManager extends ViewGroupManager<AppBarLayout> {
    private static final String REACT_CLASS = "AppbarLayout";
    private static final int COMMAND_SET_CHILDREN_SCROLL_FLAGS = 1;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected AppBarLayout createViewInstance(ThemedReactContext reactContext) {
        AppBarLayout appBarLayout = new AppBarLayout(reactContext);
        appBarLayout.setFitsSystemWindows(true);
        return appBarLayout;
    }

    @Override
    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    @Override
    public Map<String,Integer> getCommandsMap() {
        return MapBuilder.of(
                "setChildrenScrollFlags", COMMAND_SET_CHILDREN_SCROLL_FLAGS);
    }

    @Override
    public void receiveCommand(AppBarLayout view, int commandType, ReadableArray args) {
        Assertions.assertNotNull(view);
        Assertions.assertNotNull(args);

        switch (commandType) {
            case COMMAND_SET_CHILDREN_SCROLL_FLAGS: {
                ReadableArray options = args.getArray(0);
                setChildrenScrollFlags(view, options);
                return;
            }

            default:
                throw new JSApplicationIllegalArgumentException(String.format(
                        "Unsupported command %d received by %s.",
                        commandType,
                        getClass().getSimpleName()));
        }
    }

    @ReactProp(name = "childrenScrollFlags")
    public void setScrollFlags(AppBarLayout view, ReadableArray options) {
        setChildrenScrollFlags(view, options);
    }

    private void setChildrenScrollFlags(AppBarLayout view, ReadableArray options) {
        try {
            int optionSize = options.size();
            for (int i=0; i<optionSize; i++) {

                ReadableMap optionMap = options.getMap(i);
                ReadableArray scrollFlags = optionMap.getArray("scrollFlags");

                int scrollFlagsSize = scrollFlags.size();
                int scrollFlagsInteger = 0;

                for (int j=0; j<scrollFlagsSize; j++) {
                    String scrollFlagString = scrollFlags.getString(j);

                    if ("enterAlways".equals(scrollFlagString)) {
                        scrollFlagsInteger = scrollFlagsInteger | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS;
                    } else if ("enterAlwaysCollapsed".equals(scrollFlagString)) {
                        scrollFlagsInteger = scrollFlagsInteger | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED;
                    } else if ("exitUntilCollapsed".equals(scrollFlagString)) {
                        scrollFlagsInteger = scrollFlagsInteger | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
                    } else if ("scroll".equals(scrollFlagString)) {
                        scrollFlagsInteger = scrollFlagsInteger | AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;
                    } else if ("snap".equals(scrollFlagString)) {
                        scrollFlagsInteger = scrollFlagsInteger | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP;
                    }
                }

                View childView = view.getChildAt(optionMap.getInt("index"));
                AppBarLayout.LayoutParams param = (AppBarLayout.LayoutParams) childView.getLayoutParams();

                //noinspection ResourceType
                param.setScrollFlags(scrollFlagsInteger);
            }
        } catch (Exception e) {
            // TODO: Handle Exception
        }
    }
}
