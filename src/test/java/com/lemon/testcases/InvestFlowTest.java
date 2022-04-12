package com.lemon.testcases;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.JSONObject;
import com.lemon.common.BaseTest;
import com.lemon.data.Constants;
import com.lemon.data.Environment;
import com.lemon.pojo.ExcelPojo;
import com.lemon.util.JDBCUtils;
import com.lemon.util.PhoneRandomUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;

/**
 * @Project: class_31_restAssured_forWW
 * @Site: http://www.lemonban.com
 * @Author: l
 * @Create: 2022-02-13 21:52
 * @Desc： 投资人
 **/

public class InvestFlowTest extends BaseTest {
    @BeforeClass
    public void setUp(){
        //生成三个角色的随机手机号码（投资人+借款人+管理员）
        String borrowserPhone = PhoneRandomUtil.getUnregisterPhone();
        String adminPhone = PhoneRandomUtil.getUnregisterPhone();
        String investPhone = PhoneRandomUtil.getUnregisterPhone();
        Environment.envData.put("borrower_phone",borrowserPhone);
        Environment.envData.put("admin_phone",adminPhone);
        Environment.envData.put("invest_phone",investPhone);
        //读取测试用例数据从第一条~第九条
        List<ExcelPojo> list = readSpecifyExcelData(5, 0, 9);
        for(int i = 0; i < list.size(); i++){
            //发送请求   参数化替换
            ExcelPojo excelPojo = list.get(i);
            excelPojo = casesReplace(excelPojo);
            Response res = request(excelPojo,"投资业务流");
            //判断是否要提取响应数据
            if (excelPojo.getExtract() != null) {
                extractToEnvironments(excelPojo,res);
            }
        }
    }

    @Test
    public void testInvest(){
        List<ExcelPojo> list = readSpecifyExcelData(5,9);
        ExcelPojo excelPojo = list.get(0);
        //替换
        excelPojo = casesReplace(excelPojo);
        //发送投资请求
        Response res = request(excelPojo,"投资业务流");
        //响应断言
        assertResponse(excelPojo,res);
        //数据库断言
        assertSQL(excelPojo);
    }


    @Test
    public void teardown(){

    }
}
