package com.reactnativenavigation.core.objects;

/**
 * Created by guyc on 08/04/16.
 */
public class _Button  {
//    private static final long serialVersionUID = -570145217281069067L;
//
//    private static final String KEY_ID = "id";
//    private static final String KEY_TITLE = "title";
//    private static final String KEY_ICON = "tabIcon";
//    private static final String KEY_DISABLED = "disabled";
//    private static final String KEY_SHOW_AS_ACTION = "showAsAction";
//
//    public String id;
//    public String title;
//    private String mIconSource;
//    public boolean disabled;
//    public String showAsAction;
//
//    private static final AtomicInteger sAtomicIdGenerator = new AtomicInteger();
//    private static final Map<String, Integer> sStringToNumericId = new HashMap<>();
//
//    public _Button(ReadableMap button) {
//        id = getString(button, KEY_ID);
//        title = getString(button, KEY_TITLE, "");
//        mIconSource = getString(button, KEY_ICON);
//        disabled = getBoolean(button, KEY_DISABLED);
//        showAsAction = getString(button, KEY_SHOW_AS_ACTION, "");
//    }
//
//    public boolean hasIcon() {
//        return mIconSource != null;
//    }
//
//    /**
//     * @param dimensions The requested tabIcon dimensions
//     */
//    public Drawable getIcon(Context ctx, int dimensions) {
//       return ImageLoader.getIcon(ctx, mIconSource, dimensions);
//    }
//
//    public int getItemId() {
//        if (sStringToNumericId.containsKey(id)) {
//            return sStringToNumericId.get(id);
//        }
//
//        int itemId = sAtomicIdGenerator.addAndGet(1);
//        sStringToNumericId.put(id, itemId);
//        return itemId;
//    }
//
//    /**
//     * Each button has a string id, defined in JS, which is used to identify the button when
//     * handling events.
//     * @param item Toolbar button
//     * @return Returns the event id associated with the given menu item
//     */
//    public static String getButtonEventId(MenuItem item) {
//        for (Map.Entry<String, Integer> entry : sStringToNumericId.entrySet()) {
//            if (entry.getValue() == item.getItemId()) {
//                return entry.getKey();
//            }
//        }
//
//        return null;
//    }
}
