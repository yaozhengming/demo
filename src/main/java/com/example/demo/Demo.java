package com.example.demo;

import java.io.Serializable;
import java.util.ArrayList;
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


/**
 * List<T> keyList = Maps.toList(aMap).byKey();
 * List<T> valueList = Maps.toList(aMap).byValue();
 *
 * @author yaozhengming
 * @date 2019/2/15
 */
class Maps implements Serializable {

    private static final long serialVersionUID = 6101605313316864754L;


    private static Maps maps = new Maps();
    private static List<String> listKey = new ArrayList<>();
    private static List<String> listValue = new ArrayList<>();
    private static Map<String, String> mapMaps = new HashMap<>();

    static Maps toList(Map<String, String> map) {
        mapMaps = map;
        return maps;
    }

    static List<String> byKey() {
        for (Map.Entry<String, String> entry : mapMaps.entrySet()) {
            listKey.add(entry.getKey());
        }
        return listKey;
    }

    static List<String> byValue() {
        for (Map.Entry<String, String> entry : mapMaps.entrySet()) {
            listValue.add(entry.getValue());
        }
        return listValue;
    }
}
