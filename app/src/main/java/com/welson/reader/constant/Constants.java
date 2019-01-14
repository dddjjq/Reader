package com.welson.reader.constant;

import com.welson.reader.util.AppUtils;
import com.welson.reader.util.FileUtils;

public class Constants {

    public static final String IMG_BASE_URL = "http://statics.zhuishushenqi.com";
    public static final String API_BASE_URL = "http://api.zhuishushenqi.com";
    public static final String SP_IS_SELECT_GENDER = "is_select_gender";
    public static final String SP_GENDER_IS_MALE = "gender_is_male";
    public static final String PATH_DATA = FileUtils.createRootPath(AppUtils.getApplicationContext());
    public static final String PATH_TXT = PATH_DATA + "/book/";
    public static final String SP_COMM_LEFT = "comm_left";
    public static final String SP_COMM_RIGHT = "comm_right";

    private String ALL = "all"; //全部类型
    private String XHQH = "xhqh"; //玄幻奇幻
    private String WXXX = "wxxx"; //武侠仙侠
    private String DSYN = "dsyn"; //都市异能
    private String LSJS = "lsjs"; //历史军事
    private String YXJJ = "yxjj"; //游戏竞技
    private String KHLY = "khly"; //科技灵异
    private String CYJK = "cyjk"; //穿越架空
    private String HMZC = "hmzc"; //豪门总裁
    private String XDYQ = "xdyq"; //现代言情
    private String GDYQ = "gdyq"; //古代言情
    private String HXYQ = "hxyq"; //幻想小说
    private String DMTR = "dmtr"; //耽美同人

    public enum BookType {
        ALL, XHQH, WXXX, DSYN, LSJS, YXJJ, KHLY, CYJK, HMZC, XDYQ, GDYQ, HXYQ, DMTR
    }
}
