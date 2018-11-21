package com.welson.reader.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static String createFile(File file){
        try{
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
                Log.d(TAG,"==创建文件== : " +file.getAbsolutePath());
            }
            return file.getAbsolutePath();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static void createDir(String path){
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    public static String createRootPath(Context context){
        String cacheRootPath = "";
        if (isSdcardEnable()){
            cacheRootPath = context.getExternalCacheDir().getPath();
        }else {
            cacheRootPath = context.getCacheDir().getPath();
        }
        return cacheRootPath;
    }

    public static boolean isSdcardEnable(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
