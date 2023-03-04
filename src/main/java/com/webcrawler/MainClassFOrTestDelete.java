package com.webcrawler;

import java.util.HashSet;
import java.util.Set;

public class MainClassFOrTestDelete {
    public static void main(String[] args){
        MainClassFOrTestDelete test = new MainClassFOrTestDelete();
        test.extractDataWithJsoup();
    }

    public void extractDataWithJsoup(){

        Set<String> a = new HashSet<>();

        a.add("A");
        a.add("B");
        a.add("C");

        Set<String> b = new HashSet<>();

        b.add("D");
        b.add("A");
        b.add("C");

        b.removeAll(a);

        System.out.println(b);

    }
}
