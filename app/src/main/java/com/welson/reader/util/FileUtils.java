package com.welson.reader.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.welson.reader.constant.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public static void writeFile(File file,String content,boolean isAppend){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static File getChapterFile(String bookId,int chapter){
        File file = new File(getChapterPath(bookId,chapter));
        if (!file.exists()){
            createFile(file);
        }
        return file;
    }

    public static String getChapterPath(String bookId,int chapter){
        return Constants.PATH_TXT + bookId + File.separator + chapter + ".txt";
    }
}
