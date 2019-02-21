package com.example.demo;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaozhengming
 * @date 2019/2/18
 */
public class DemoTest {
    @Test
    public void test1() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");
        List<String> listKey = Maps.toList(map).byKey();
        List<String> listValue = Maps.toList(map).byValue();
        for (int i = 0; i < listKey.size(); i++) {
            System.out.println("key:" + listKey.get(i) + "      value:" + listValue.get(i));
        }
    }


    @Test
    public void test2() {
        System.out.println("OP_READ:" + (1 << 0));
        System.out.println("OP_WRITE:" + (1 << 2));
        System.out.println("OP_CONNECT:" + (1 << 3));
        System.out.println("OP_ACCEPT:" + (1 << 4));
    }

    @Test
    public void test3() {

    }

}

