package com.lemon.data;

/**
 * @Project: class_31_restAssured_forWW
 * @Site: http://www.lemonban.com
 * @Author: l
 * @Create: 2021-12-20 21:56
 * @Desc： 常量类，文件
 **/

public class Constants {
    //日志输出配置：控制台(false) or Allure日志文件中(true)
    public static final boolean LOG_TO_FILE = true;
    //Excel文件得路径
    public static final String EXCEL_FILE_PATH = "src\\test\\resources\\api_testcases_futureloan_v1.xlsx";
    //接口BaseURL地址
    public static final String BASE_URI = "http://api.lemonban.com/futureloan";
    //数据库baseuri
    public static final String DB_BASE_URI = "api.lemonban.com/";
    //数据库名
    public static final String DB_NAME = "futureloan";
    public static final String DB_USERNAME = "future";
    public static final String DB_PWD = "123456";
}
