package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaozhengming
 * @date 2019.2.19
 */
public class Maps {

    //创建 Maps 的一个对象
    private static Maps maps = new Maps();


    private static List<String> listKey = new ArrayList<>();
    private static List<String> listValue = new ArrayList<>();
    private static Map<String, String> mapMaps = new HashMap<>();


    private Maps() {
    }

    static Maps toList(Map<String, String> map) {
        mapMaps = map;
        return maps;
    }

    List<String> byKey() {
        for (Map.Entry<String, String> entry : mapMaps.entrySet()) {
            listKey.add(entry.getKey());
        }
        return listKey;
    }

    List<String> byValue() {
        for (Map.Entry<String, String> entry : mapMaps.entrySet()) {
            listValue.add(entry.getValue());
        }
        return listValue;
    }
}
