package com.xproject.util;

/**
 * Created by Administrator on 2017/1/9.
 */
public class Test {
    public static void main(String[] arge){
         String a="11|2B570D16AEF33F7E0001015983A74FC9|1|0|1";
         String[] arr=a.split("\\|");
         System.out.println(arr.length);

        arr=a.split("|");
        System.out.println(arr.length);

        a="11#2B570D16AEF33F7E0001015983A74FC9#1#0#1";
        arr=a.split("#");
        System.out.println(arr.length);
    }

}
