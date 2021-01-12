package com.matt.project.question.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-28 15:45
 */
public class ViewObject {

    private Map<String, Object> objs = new HashMap<String, Object>();
    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
