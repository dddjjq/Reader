package com.welson.reader.util;


public class BookContentUtil {
    public static String getSaveString(String title,String content){
        content = "\u3000\u3000" + content.replaceAll("\n","@");
        return title + "\n" + content;
    }
}
