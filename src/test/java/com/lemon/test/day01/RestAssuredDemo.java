package com.lemon.test.day01;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

/**
 * @Project: class_31_restAssured_forWW
 * @Site: http://www.lemonban.com
 * @Author: l
 * @Create: 2021-10-26 22:13
 * @Desc： waiwailaoshi
 **/

public class RestAssuredDemo {
    @Test
        public void firstGetRequest(){
        given().
                //设置请求： 请求头，请求体·····
        when().
                get("https://www.baidu.com").
        then().
                log().body();
    }

    @Test
    public void getDemo01(){
        given().
                //设置请求： 请求头，请求体·····
                queryParam("mobilephone","13323234545").
                queryParam("pwd","123456").
        when().
        get("http://www.httpbin.org/get").
        then().
        log().body();
    }

    @Test
    public void postDemo01(){   //form表单 参数类型
        given().
                //设置请求： 请求头，请求体·····
                formParam("mobile_phone","13323234545").
                formParam("pwd","123456").
                contentType("application/x-www-form-urlencoded").
            when().
                post("http://www.httpbin.org/post").
            then().
                log().body();
    }

    @Test
    public void postDemo02(){      //json参数类型
        String jsonData = "{\"mobilephone\":\"13323234545\",\"pwd\":\"123456\"}";
        given().
            body(jsonData).
            contentType("application/json").
            when().
            post("http://www.httpbin.org/post").
            then().
            log().body();
    }

    @Test
    public void postDemo03(){   // xml参数类型
        String xmlData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<suite>\n" +
                "\t<class>测试xml</class>\n" +
                "</suite>";
        given().
                body(xmlData).
                contentType("application/xml").
                when().
                post("http://www.httpbin.org/post").
                then().
                log().body();
    }

    @Test
    public void postDemo04(){   // multiPart 多参数表单类型

        given().
                multiPart(new File("D:\\hah.txt")).
        when().
                post("http://www.httpbin.org/post").
        then().
                log().body();
    }
}
