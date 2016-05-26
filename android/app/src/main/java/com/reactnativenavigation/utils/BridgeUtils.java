package com.reactnativenavigation.utils;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yedidyak on 26/05/2016.
 */
public class BridgeUtils {

    @SuppressWarnings("unchecked")
    public static Bundle addMapToBundle(HashMap<String, ?> map, Bundle bundle) {
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (value instanceof String) {
                bundle.putString(key, (String) value);
            } else if (value instanceof Integer) {
                bundle.putInt(key, (Integer) value);
            } else if (value instanceof Double) {
                bundle.putDouble(key, ((Double) value));
            } else if (value instanceof Boolean) {
                bundle.putBoolean(key, (Boolean) value);
            } else if (value instanceof HashMap) {
                bundle.putBundle(key, addMapToBundle((HashMap<String, Object>) value, new Bundle()));
            } else if (value instanceof ArrayList) {
                putArray(key, (ArrayList) value, bundle);
            }
        }
        return bundle;
    }

    @SuppressWarnings("unchecked")
    private static void putArray(String key, ArrayList arrayList, Bundle bundle) {
        if (arrayList.size() == 0) {
            bundle.putBooleanArray(key, new boolean[]{});
        } else {
            verifyArrayListIsSingleType(arrayList);
            if (arrayList.get(0) instanceof String) {
                bundle.putStringArray(key, toStringArray((ArrayList<String>) arrayList));
            } else if (arrayList.get(0) instanceof Integer) {
                bundle.putIntArray(key, toIntArray((ArrayList<Integer>) arrayList));
            } else if (arrayList.get(0) instanceof Float) {
                bundle.putFloatArray(key, toFloatArray((ArrayList<Float>) arrayList));
            } else if (arrayList.get(0) instanceof Double) {
                bundle.putDoubleArray(key, toDoubleArray((ArrayList<Double>) arrayList));
            } else if (arrayList.get(0) instanceof Boolean) {
                bundle.putBooleanArray(key, toBooleanArray((ArrayList<Boolean>) arrayList));
            } else if (arrayList.get(0) instanceof HashMap) {
                bundle.putParcelableArray(key, toBundleArray((ArrayList<HashMap>) arrayList));
            } else if (arrayList.get(0) instanceof ArrayList) {
                Log.w("RNNNavigation", "Arrays of arrays passed in props are converted to dictionaries with indexes as keys");
                Bundle innerArray = new Bundle();
                for (int i = 0; i < arrayList.size(); i++) {
                    putArray(String.valueOf(i), (ArrayList) arrayList.get(i), innerArray);
                }
                bundle.putParcelable(key, innerArray);
            }
        }
    }

    private static void verifyArrayListIsSingleType(ArrayList arrayList) {
        int i = 1;
        while (i < arrayList.size()) {
            if (!arrayList.get(i - 1).getClass().isInstance(arrayList.get(i)))
                throw new IllegalArgumentException("Cannot pass array of multiple types via props");
            i++;
        }
    }

    @SuppressWarnings("unchecked")
    private static Bundle[] toBundleArray(ArrayList<HashMap> arrayList) {
        Bundle[] ret = new Bundle[arrayList.size()];
        for (int i=0; i < ret.length; i++)
            ret[i] = addMapToBundle(arrayList.get(i), new Bundle());
        return ret;
    }

    private static int[] toIntArray(ArrayList<Integer> arrayList) {
        int[] ret = new int[arrayList.size()];
        for (int i=0; i < ret.length; i++)
            ret[i] = arrayList.get(i);
        return ret;
    }

    private static float[] toFloatArray(ArrayList<Float> arrayList) {
        float[] ret = new float[arrayList.size()];
        for (int i=0; i < ret.length; i++)
            ret[i] = arrayList.get(i);
        return ret;
    }

    private static double[] toDoubleArray(ArrayList<Double> arrayList) {
        double[] ret = new double[arrayList.size()];
        for (int i=0; i < ret.length; i++)
            ret[i] = arrayList.get(i);
        return ret;
    }

    private static boolean[] toBooleanArray(ArrayList<Boolean> arrayList) {
        boolean[] ret = new boolean[arrayList.size()];
        for (int i=0; i < ret.length; i++)
            ret[i] = arrayList.get(i);
        return ret;
    }

    private static String[] toStringArray(ArrayList<String> arrayList) {
        String[] ret = new String[arrayList.size()];
        for (int i=0; i < ret.length; i++)
            ret[i] = arrayList.get(i);
        return ret;
    }

}
