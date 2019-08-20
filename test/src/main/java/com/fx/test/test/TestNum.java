package com.fx.test.test;

/**
 * @auther: mabaofeng
 * @date: 2019/8/19 17:25
 * @description:
 */
public class TestNum {

/*    public static void main(String[] args) {
        int i = 0;
        change(i);
        System.out.println(i);
    }

    private static void change(int i2) {
        i2 = 1;
        System.out.println(i2);
    }*/

    /*public static void main(String[] args) {
        Integer i = 0;
        change(i);
        System.out.println(i);
    }

    private static void change(Integer i2) {
        i2 = 1;
        System.out.println(i2);
    }*/

    public static void main(String[] args) {
        StringBuilder s = new StringBuilder("0");
        change(s);
        System.out.println(s);
    }

    private static void change(StringBuilder s2) {
        s2 = new StringBuilder("1");
        System.out.println(s2.toString());
    }
}
