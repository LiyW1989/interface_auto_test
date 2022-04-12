package com.lemon;

import java.util.ArrayList;
import java.util.List;

/**
 * @Project: class_31_restAssured_forWW
 * @Site: http://www.lemonban.com
 * @Author: l
 * @Create: 2021-12-09 17:54
 * @Desc：
 **/

public class JiSuanOneTwoHhundrondIsSeven {
    public static void main(String[] args) {
        //用于存放包含7，或是能被7整除的所有数字的集合
        List<Integer> seven=new ArrayList<Integer>();

        for (int i = 1; i <= 100; i++) {
            //将数字i转化为字符串
            String istr=i+"";

            if(i%7==0){
                //被7整除（余数为0）
                seven.add(i);
            }else if(istr.contains("7")){
                //包含7
                seven.add(i);
            }
        }
        System.out.println("包含7，或是能被7整除的所有数字为："+seven);

        // 编写程序，打印1到100之内的整数，但数字中包含7的要跳过，例如：17、27、71、72
        //字符串实现
        for (int i = 1; i < 100; i++) {
            if ((i + "").indexOf("7") < 0) {
                System.out.print(i + "\t");
            }
        }
        //非字符串实现
        for (int i = 1; i < 100; i++) {
            if (i%10!=7 &&(i/10)%10!=7){
                System.out.print(i + "\t");
            }
        }
    }
}
