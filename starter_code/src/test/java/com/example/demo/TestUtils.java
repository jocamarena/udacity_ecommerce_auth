package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {
    public static void inject(){

    }
    public static void inject(Object targer, String fieldName, Object toInject){
        boolean wasPrivate = false;
        try {
            Field f = targer.getClass().getDeclaredField(fieldName);
            if (!f.isAccessible()){
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(targer, toInject);
        if (wasPrivate){
            f.setAccessible(false);
        }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
