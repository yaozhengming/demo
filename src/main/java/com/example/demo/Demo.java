package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaozhengming
 * @date 2019/2/18
 */
public class Demo {
    public static void main(String[] args) {
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
}
