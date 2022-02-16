package com.vilson.animals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Utils {

    final static String name = "name";
    final static String favoriteFood = "favoriteFood";
    final static String breed = "breed";
    final static String wingspreadInMeters = "wingspreadInMeters";
    final static String laysEggs = "laysEggs";
    final static String canSpeak = "canSpeak";

    static List<Field> getAllInstanceFieldsFromTheSamePackage(Class clazz) {
        String packageName = clazz.getPackageName();
        List<Field> fields = new ArrayList<>();
        includeInstanceFieldsFromClassInTheSamePackage(clazz, packageName, fields);
        return fields;
    }

    private static void includeInstanceFieldsFromClassInTheSamePackage(Class clazz, String packageName,
                                                                       List<Field> fields) {
        if (clazz != null && packageName.equals(clazz.getPackageName())) {
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(Utils::isInstanceField)
                    .forEach(fields::add);

            includeInstanceFieldsFromClassInTheSamePackage(clazz.getSuperclass(), packageName, fields);
        }
    }

    private static boolean isInstanceField(Field field) {
        return !Modifier.isStatic(field.getModifiers());
    }
}
