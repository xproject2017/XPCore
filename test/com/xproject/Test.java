package com.xproject;

/**
 * Created by Fiona on 2017/2/27.
 */
public class Test {

    @org.junit.Test
    public void testSave() throws Exception {
        Integer x=31;
        Integer y=31;
        System.out.println(x.intValue() == y.intValue());
        System.out.println(x.equals(y));
        System.out.println(!x.equals(y));
    }
}
