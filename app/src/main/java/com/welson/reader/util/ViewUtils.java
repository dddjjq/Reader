package com.welson.reader.util;

import android.view.View;

public class ViewUtils {

    public static int getMeasuredSize(int measureSpec,int defSize){
        int result = defSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        if (specMode == View.MeasureSpec.EXACTLY){
            result = specSize;
        }else if (specMode == View.MeasureSpec.AT_MOST){
            result = Math.min(result,specSize);
        }
        return result;
    }
}
