package com.reactnativenavigation.core.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.facebook.react.bridge.ReadableMap;
import com.reactnativenavigation.BuildConfig;
import com.reactnativenavigation.utils.ResourceDrawableIdHelper;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by guyc on 08/04/16.
 */
public class Button extends JsonObject implements Serializable {
    private static final long serialVersionUID = -570145217281069067L;

    private static ResourceDrawableIdHelper sResDrawableIdHelper = new ResourceDrawableIdHelper();

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ICON = "icon";

    public String id;
    public String title;
    private String mIconSource;

    private static AtomicInteger sAtomicIdGenerator = new AtomicInteger();
    private static Map<String, Integer> sStringToNumericId = new HashMap<>();

    public Button(ReadableMap button) {
        id = getString(button, KEY_ID);
        title = getString(button, KEY_TITLE, "");
        mIconSource = getString(button, KEY_ICON);
    }

    public boolean hasIcon() {
        return mIconSource != null;
    }

    public Drawable getIcon(Context ctx) {
        if (mIconSource == null) {
            return null;
        }

        try {
            Uri icon = getIconUri(ctx);
            URL url = new URL(icon.toString());
            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
            return new BitmapDrawable(bitmap);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Uri getIconUri(Context context) {
        Uri ret = null;
        if (mIconSource != null) {
            try {
                ret = Uri.parse(mIconSource);
                // Verify scheme is set, so that relative uri (used by static resources) are not handled.
                if (ret.getScheme() == null) {
                    ret = null;
                }
            } catch (Exception e) {
                // ignore malformed uri, then attempt to extract resource ID.
            }
            if (ret == null) {
                ret = sResDrawableIdHelper.getResourceDrawableUri(context, mIconSource);
            }
        }
        return ret;
    }

    public int getItemId() {
        if (sStringToNumericId.containsKey(id)) {
            return sStringToNumericId.get(id);
        }

        int itemId = sAtomicIdGenerator.addAndGet(1);
        sStringToNumericId.put(id, itemId);
        return itemId;
    }
}
