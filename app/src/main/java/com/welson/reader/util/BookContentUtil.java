package com.welson.reader.util;

import android.util.Log;

public class BookContentUtil {
    public static String getSaveString(String title,String content){
        content = "\u2000\u2000" + content.replaceAll("\n","\n\u2000\u2000");
        return title + "\n" + content;
    }
}
