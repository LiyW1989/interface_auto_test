package com.lemon.testcases;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
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
 * @Create: 2021-12-08 21:49
 * @Desc：
 **/

public class LoginTest extends BaseTest {

    @BeforeClass
    public void setup(){
        //生成一个没有被注册过的手机号码
        String phone = PhoneRandomUtil.getUnregisterPhone();
        Environment.envData.put("phone",phone);
        //1、前置条件
        List<ExcelPojo> listDatas = readSpecifyExcelData(2,0,1);
        ExcelPojo excelPojo = listDatas.get(0);
        //替换
        excelPojo = casesReplace(excelPojo);
        //执行{注册}的接口请求
        Response res = request(excelPojo,"登录模块");
        //提取注册返回的手机号码保存到环境变量中
        extractToEnvironments(listDatas.get(0),res);
    }

    @Test(dataProvider = "getLoginDatas")
    public void testLogin(ExcelPojo excelPojo) {
        //替换用例数据
        excelPojo = casesReplace(excelPojo);
        //发起登录请求
        Response res = request(excelPojo,"登录模块");
        //断言
        assertResponse(excelPojo,res);
       /* Map<String,Object> expectedMap = JSONObject.parseObject(excelPojo.getExpected(),Map.class);
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
        }*/
    }


    @DataProvider
    public Object[] getLoginDatas(){
       //调用封装好的readSpecifyExcelData方法
        List<ExcelPojo> listDatas = readSpecifyExcelData(2,1);
        //把集合转化为一个一维数组
        return listDatas.toArray();
    }

    /*public static void main(String[] args) {
        File file = new File("F:\\柠檬班\\4_接口\\api_testcases_futureloan_v1.xls");
        //导入的参数对象
        ImportParams importParams = new ImportParams();
        //读取第二个Sheet
        importParams.setStartSheetIndex(1);
        //设置读取的起始行
        importParams.setStartRows(0);
        //设置读取的行数
        importParams.setReadRows(1);
        //读取Excel
        List<ExcelPojo> listdatas = ExcelImportUtil.importExcel(file, ExcelPojo.class,importParams);
        for (ExcelPojo exp : listdatas) {
            System.out.println(exp);
        }
    }*/




}
