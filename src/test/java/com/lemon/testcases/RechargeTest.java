package com.lemon.testcases;

import com.alibaba.fastjson.JSONObject;
import com.lemon.common.BaseTest;
import com.lemon.data.Constants;
import com.lemon.data.Environment;
import com.lemon.pojo.ExcelPojo;
import com.lemon.util.PhoneRandomUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;

/**
 * @Project: class_31_restAssured_forWW
 * @Site: http://www.lemonban.com
 * @Author: l
 * @Create: 2021-12-16 21:08
 * @Desc： 充值
 **/

public class RechargeTest extends BaseTest {

    int memberId;
    String token;

    @BeforeClass
    public void setup(){
        //生成一个没有被注册过的手机号码
        String phone = PhoneRandomUtil.getUnregisterPhone();
        Environment.envData.put("phone",phone);
        //前置条件
        List<ExcelPojo> listDatas = readSpecifyExcelData(3,0,2);
        //发起接口请求 并实现替换数据
        ExcelPojo registerExcelPojo = listDatas.get(0);
        //注册请求
        registerExcelPojo = casesReplace(registerExcelPojo);
        Response resRegister = request(registerExcelPojo,"充值模块");
        //获取【提取返回数据（extract）】
        //提取接口返回对应的字段保存到环境变量中
        extractToEnvironments(registerExcelPojo,resRegister);
        //登录
        ExcelPojo loginExcelPojo = listDatas.get(1);
        //参数替换，替换{{phone}}
        loginExcelPojo = casesReplace(loginExcelPojo);
        //登录请求
        Response resLogin = request(loginExcelPojo,"充值模块");
        //member_id 的获取步骤,得到【提取返回数据】这列
        extractToEnvironments(loginExcelPojo,resLogin);
        //得到的值就是GPath路径表达式
       /* Object memberIdPath = extractMap.get("member_id");
        memberId = resLogin.jsonPath().get(memberIdPath.toString());
        //获取到的memberId存到环境变量中
        Environment.memberId = memberId;

        //获取token ,存到环境变量中
        Object tokenPath = extractMap.get("token");
        token = resLogin.jsonPath().get(tokenPath.toString());
        Environment.token = token;
*/
    }
    @Test(dataProvider = "getRechargeDatas")
    public void testRecharge(ExcelPojo excelPojo){
        //用例执行之前替换{{member_id}} 为环境变量中保存得对应的值
        excelPojo = casesReplace(excelPojo);
        //String params = regexReplace(excelPojo.getInputParams(), Environment.envData.get("member_id") + "");
        //System.out.println("替换前得效果：:" + excelPojo);
        //System.out.println("替换后得效果：:" + excelPojo);
        Response res = request(excelPojo,"充值模块");
        //断言
        assertResponse(excelPojo,res);
    }

    @DataProvider
    public Object[]  getRechargeDatas(){
        List<ExcelPojo> listDatas = readSpecifyExcelData( 3, 2);
        return listDatas.toArray();
    }



    /**
    public List<ExcelPojo> readAllExcelData (int sheetNum){
        //导入的参数对象
        ImportParams importParams = new ImportParams();
        //读取第二个Sheet
        importParams.setStartSheetIndex(sheetNum-1);
        List<com.lemon.test.day02.ExcelPojo> listdatas = ExcelImportUtil.importExcel(file, com.lemon.test.day02.ExcelPojo.class,importParams);
        return  listdatas;
    }

    //封装一个方法
    public List<ExcelPojo> readSpecifyExcelData (File file, int sheetNum, int startRow, int readRow){
        //导入的参数对象
        ImportParams importParams = new ImportParams();
        //读取第二个Sheet
        importParams.setStartSheetIndex(sheetNum-1);
        //设置读取的起始行
        importParams.setStartRows(startRow);
        //设置读取的行数
        importParams.setReadRows(readRow);
        List<com.lemon.test.day02.ExcelPojo> listdatas = ExcelImportUtil.importExcel(file, ExcelPojo.class,importParams);
        return  listdatas;
    }*/

    /*public static void main(String[] args) {
        //正则表达式测试 -- Java实现
       *//* String str = "ABCD0101{{memberID}}XXXXX{{token}}\n" +
                "SSSSSGSDGSDGSGS{{phone}}";
        int memberID = 101;
        String token = "ABCDSSADAD";
        String phone = "18100001414";*//*
        //pattern 正则表达式的匹配器
        Pattern pattern = Pattern.compile("\\{\\{(.*?)}}");
        //matcher 去匹配那一个原始的字符串,得到匹配对象
        Matcher matcher = pattern.matcher(str);
        //遍历循环查找
        while (matcher.find()){
            //group(0) 获取到整个匹配的内容
            //System.out.println(matcher.group(0));
            String outerStr = matcher.group(0);
            //group(1) 表示获取{{}}包裹着的内容
            //System.out.println(matcher.group(1));
            String innerStr = matcher.group(1);
            //replace
            System.out.println(str.replace(outerStr,phone));
        }

        System.out.println(regexReplace("SDFSDFFS{{token}}adfasgrg{{phone}}CCCCC{{lemon}}"));

    }*/










}
