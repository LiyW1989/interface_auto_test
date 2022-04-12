package com.lemon.test.day02;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;

/**
 * @Project: class_31_restAssured_forWW
 * @Site: http://www.lemonban.com
 * @Author: l
 * @Create: 2021-12-02 21:48
 * @Desc： 数据驱动
 **/

public class DataDrivenDemo {

    @Test(dataProvider = "getLoginDatas2")
    public void testLogin(ExcelPojo excelPojo) {
        excelPojo.getCaseId();
        excelPojo.getExpected();
        //RestAssured全局配置
        //json小数返回类型是BigDecimal
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
        //BaseUrl全局配置
        RestAssured.baseURI = "http://api.lemonban.com/futureloan";
        //接口入参
        String inputParams = excelPojo.getInputParams();
        //接口地址
        String url = excelPojo.getUrl();
        //请求头
        String requestHeader = excelPojo.getRequestHeader();
        //把请求头转换成Map
        Map requestHeaderMap = (Map) JSON.parse(requestHeader);
        //期望的响应结果
        String expected = excelPojo.getExpected();
        //把响应结果转成map
        Map<String, Object> expectedMap = (Map) JSON.parse(expected);
        Response res =
                given().
                        body(inputParams).
                        headers(requestHeaderMap).
                when().
                        post(url).
                then().
                        log().all().
                        extract().response();
        //断言？？？
        //读取响应map里面的每一个key
        //作业：完成响应断言
        //思路：1、循环遍历响应Map，取到里面每一个Key(实际上就是我们设计的jsonpath表达式)
        //2、通过res.jsonPath.get(key)取到实际的结果，再跟期望的结果做对比（key对应的Value）
        for (String key : expectedMap.keySet() ){
            //获取map里面的key
            System.out.println(key);
            //获取map里面的value
            //获取期望结果
            Object exceptedValue = expectedMap.get(key);
            //获取接口返回的实际结果（jsonPath表达式）
            Object actualValue = res.jsonPath().get(key);
            Assert.assertEquals(actualValue,exceptedValue);
        }
    }

    @DataProvider
    public Object[][] getLoginDatas(){
        Object[][] datas = {{"13323230011","123456"},
                {"1332323111","123456"},
                {"13323230011","12345678"}};

        return datas;
    }

    @DataProvider
    public Object[] getLoginDatas2(){
        File file = new File("F:\\柠檬班\\4_接口\\api_testcases_futureloan_v1.xls");
        //导入的参数对象
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(1);
        //读取Excel
        List<ExcelPojo> listdatas = ExcelImportUtil.importExcel(file,ExcelPojo.class,importParams);
        return listdatas.toArray();
    }

    public static void main(String[] args) {
        File file = new File("F:\\柠檬班\\4_接口\\api_testcases_futureloan_v1.xls");
        //导入的参数对象
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(1);
        //读取Excel
        List<Object> listdatas = ExcelImportUtil.importExcel(file,ExcelPojo.class,importParams);
        for (Object object : listdatas) {
            System.out.println(object);
        }
    }















}
