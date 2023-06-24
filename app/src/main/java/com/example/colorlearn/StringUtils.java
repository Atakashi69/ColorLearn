package com.example.colorlearn;

import android.content.Context;

public class StringUtils {
    public static String getStringXML (Context context, String name) {
        return context.getString(context.getResources().getIdentifier(name, "string", context.getPackageName()));
    }
}
