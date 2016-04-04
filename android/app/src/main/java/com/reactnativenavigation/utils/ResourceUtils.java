package com.reactnativenavigation.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

/**
 * Created by guyc on 17/03/16.
 */
public class ResourceUtils {

    public static final String TYPE_DRAWABLE = "drawable";

    public static Drawable getDrawable(Context context, String resourceName) {
        Resources resources = context.getResources();
        int id =  resources.getIdentifier(resourceName, TYPE_DRAWABLE, context.getPackageName());
        return id > 0 ? ResourcesCompat.getDrawable(resources, id, context.getTheme()) : null;
    }

}
