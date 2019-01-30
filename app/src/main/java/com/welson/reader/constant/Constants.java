package com.welson.reader.constant;

import com.welson.reader.util.AppUtils;
import com.welson.reader.util.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {

    public static final String IMG_BASE_URL = "http://statics.zhuishushenqi.com";
    public static final String API_BASE_URL = "http://api.zhuishushenqi.com";
    public static final String SP_IS_SELECT_GENDER = "is_select_gender";
    public static final String SP_GENDER_IS_MALE = "gender_is_male";
    public static final String PATH_DATA = FileUtils.createRootPath(AppUtils.getApplicationContext());
    public static final String PATH_TXT = PATH_DATA + "/book/";
    public static final String SP_COMM_LEFT = "comm_left";
    public static final String SP_COMM_RIGHT = "comm_right";

    private static String ALL = "all"; //全部类型
    private static String XHQH = "xhqh"; //玄幻奇幻
    private static String WXXX = "wxxx"; //武侠仙侠
    private static String DSYN = "dsyn"; //都市异能
    private static String LSJS = "lsjs"; //历史军事
    private static String YXJJ = "yxjj"; //游戏竞技
    private static String KHLY = "khly"; //科技灵异
    private static String CYJK = "cyjk"; //穿越架空
    private static String HMZC = "hmzc"; //豪门总裁
    private static String XDYQ = "xdyq"; //现代言情
    private static String GDYQ = "gdyq"; //古代言情
    private static String HXYQ = "hxyq"; //幻想小说
    private static String DMTR = "dmtr"; //耽美同人
    public static ArrayList<String> bookTypes = new ArrayList<>(Arrays.asList(ALL, XHQH, WXXX, DSYN
            , LSJS, YXJJ, KHLY, CYJK, HMZC, XDYQ, GDYQ, HXYQ, DMTR));

}
