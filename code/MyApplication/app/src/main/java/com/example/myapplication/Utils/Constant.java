package com.example.myapplication.Utils;

public class Constant {
    public static final String BASE_URL = "http://120.79.88.20:8080/v1/";//服务器
    //public static final String BASE_URL = "http://192.168.2.105:8080/v1/";//本地
    public static final String IMG_LIST = "img_list"; //第几张图片
    public static final String POSITION = "position"; //第几张图片
    public static final String PIC_PATH = "pic_path"; //图片路径
    public static final int MAX_SELECT_PIC_NUM = 9; // 最多上传9张图片
    public static final int REQUEST_CODE_MAIN = 10; //请求码
    public static final int RESULT_CODE_VIEW_IMG = 11; //查看大图页面的结果码
//    public static final String LOGIN_URL="http://192.168.2.105:8080/v1/login";
//    public static final String REGISTER_URL="http://192.168.2.105:8080/v1/register";
//    public static final String QUERYDISEASE_URL="http://192.168.2.105:8080/v1/patient/";
//    public static final String QUESTION_URL="http://192.168.2.105:8080/v1/patient/queryQSOfDiseases";
//    public static final String M70C_URL="http://192.168.2.105:8080/v1/patient/uploadM70CData";
//    public static final String WBP_URL="http://192.168.2.105:8080/v1/patient/uploadWBPData";
//    public static final String LOADREPORT_URL="http://192.168.2.105:8080/v1/patient/getHistoryRecord/";
    public static final String LOGIN_URL=BASE_URL+"login";
    public static final String REGISTER_URL=BASE_URL+"register";
    public static final String QUERYDISEASE_URL=BASE_URL+"patient/";
    public static final String QUESTION_URL=BASE_URL+"patient/queryQSOfDiseases";
    public static final String M70C_URL=BASE_URL+"patient/uploadM70CData";
    public static final String WBP_URL=BASE_URL+"patient/uploadWBPData";
    public static final String LOADREPORT_URL=BASE_URL+"patient/getHistoryRecord/";
    public static final String UPLOAD_URL=BASE_URL+"file/";
}
