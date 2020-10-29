package com.wyp.maven;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Main
 * PackageName com.wyp.maven
 * DATE 2019/10/24 10:50
 * Describe
 */
public class Main {

    public static void main(String[] args) {
        Demo d = new Demo();
        d.setName("Demo1");
        System.out.println(d);
        d.setName("Demo2");
        System.out.println(d);
        List<String> list = new ArrayList<String>();
    }

}
