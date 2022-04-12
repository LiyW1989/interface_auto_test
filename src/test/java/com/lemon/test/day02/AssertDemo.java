package com.lemon.test.day02;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;

/**
 * @Project: class_31_restAssured_forWW
 * @Site: http://www.lemonban.com
 * @Author: l
 * @Create: 2021-11-24 22:47
 * @Desc： 注册，登录，充值，断言   BIG_DECIMAL数据转换 处理精度丢失的问题
 *          添加全局变量设置：RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
 * @Desc: BaseURL全局配置
 **/

public class AssertDemo {


    int memberId;
    String token;

    @Test
    public void testLogin(){
        //RestAssured全局配置
        //json小数返回类型是BigDecimal
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
        //BaseUrl全局配置
        RestAssured.baseURI =  "http://api.lemonban.com/futureloan";
        String json = "{\"mobile_phone\":\"18978978900\",\"pwd\":\"12345678\"}";
            Response res =
                    given().
                            body(json).
                            header("Content-Type","application/json").
                            header("X-Lemonban-Media-Type","lemonban.v2").
                     when().
                            post("/member/login/").
                     then().
                            log().all().
                            extract().response();
            //1、响应结果断言
        //整数类型
        int code = res.jsonPath().get("code");
        //字符串类型
        String msg = res.jsonPath().get("msg");
        Assert.assertEquals(code,0);
        Assert.assertEquals(msg,"OK");
        //小数类型
        //注意：restassured里面 如果返回的是json小数，那么类型应该是float，不是double
        //如果是丢失精度问题，解决方案：声明：restassured返回json小数的其类型是：BigDecimal
        BigDecimal actual = res.jsonPath().get("data.leave_amount");
        BigDecimal expected = BigDecimal.valueOf(140000.01);
        Assert.assertEquals(actual,expected);
        //java.lang.AssertionError: expected [10000.01] but found [10000.01]
        //因为数据类型不匹配

        //2、数据库断言

        //发起 “充值” 接口请求
        memberId = res.jsonPath().get("data.id");
        token = res.jsonPath().get("data.token_info.token");
    }
    @Test(dependsOnMethods = "testLogin")
    public void testRecharge(){
        String jsonData = "{\"member_id\":"+memberId+",\"amount\":10000.00}";
        Response res2 =
                given().
                        body(jsonData).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v2").
                        header("Authorization","Bearer " +token).
                when().
                        post("/member/recharge").
                then().
                        log().all().extract().response();
        BigDecimal actual2 = res2.jsonPath().get("data.leave_amount");
        BigDecimal expected2 = BigDecimal.valueOf(150000.01);
        Assert.assertEquals(actual2,expected2);
    }
}
