package controllers;

import structures.basic.Unit;

import java.util.HashMap;

public class test {

    public static void main(String[] args) {
        String a = "12";
        String b = "1"+"2";
        Unit unit = new Unit();
        HashMap<String,Unit> map = new HashMap<>();
        map.put(a,unit);
        map.put(b,unit);
        System.out.println(map.get(a) == map.get(b));
    }
}
