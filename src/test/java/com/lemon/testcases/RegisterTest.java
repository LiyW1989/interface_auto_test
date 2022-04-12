package com.lemon.testcases;

import com.alibaba.fastjson.JSONObject;
import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.pojo.ExcelPojo;
import com.lemon.util.JDBCUtils;
import com.lemon.util.PhoneRandomUtil;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Project: class_31_restAssured_forWW
 * @Site: http://www.lemonban.com
 * @Author: l
 * @Create: 2021-12-08 21:49
 * @Desc：
 **/

public class RegisterTest extends BaseTest {

    @BeforeClass
    public void setup(){
        //随机生成没有用过的手机号码
        String phone1 = PhoneRandomUtil.getUnregisterPhone();
        String phone2 = PhoneRandomUtil.getUnregisterPhone();
        String phone3 = PhoneRandomUtil.getUnregisterPhone();
        //保存到环境变量中
        Environment.envData.put("phone1",phone1);
        Environment.envData.put("phone2",phone2);
        Environment.envData.put("phone3",phone3);
    }

    @Test(dataProvider = "getRegisterDatas")
    public void testRegister(ExcelPojo excelPojo) throws FileNotFoundException {
       //替换
        excelPojo = casesReplace(excelPojo);
        //发起注册请求
        Response res = request(excelPojo,"注册模块");
        //响应断言
        assertResponse(excelPojo,res);
        //数据库断言
        assertSQL(excelPojo);


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
    public Object[] getRegisterDatas(){
       //调用封装好的readSpecifyExcelData方法
        List<ExcelPojo> listDatas = readSpecifyExcelData(1,0);
        //把集合转化为一个一维数组
        return listDatas.toArray();
    }


    @AfterTest
    public void teardown(){
        //清空掉环境变量
        Environment.envData.clear();
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
