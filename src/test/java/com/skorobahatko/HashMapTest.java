package com.skorobahatko;

public class HashMapTest {

    public static void main(String[] args) {

        // map for testing
        HashMap<Integer, String> map = new HashMap<>();

        // test put
        map.put(1, "Jack");
        map.put(250, "Peter");
        map.put(3320, "Joy");
        map.put(24351, "Steve");
        map.put(154, "Hank");
        System.out.println("Test put:\n" + map + "\n");

        // put null key
        map.put(null, "Max");
        System.out.println("Test put null key:\n" + map + "\n");

        // test putting keys that is already exists
        map.put(null, "John");
        map.put(250, "Bob");
        System.out.println("Test put keys that is already exists:\n" + map + "\n");

        // test remove null key
        map.remove(null);
        System.out.println("Test remove null key mapping:\n" + map + "\n");

        // test remove non null key
        map.remove(250);
        System.out.println("Test remove non null key mapping:\n" + map + "\n");

    }

}
