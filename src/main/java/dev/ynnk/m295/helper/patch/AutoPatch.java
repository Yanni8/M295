package dev.ynnk.m295.helper.patch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public final class AutoPatch {
    private final static Logger LOG = LoggerFactory.getLogger(AutoPatch.class);

    private static String fieldToSetter(String name) {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static String fieldToGetter(String name, boolean isBool) {
        if (isBool) {
            return "is" + name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static <T extends Object> T objectPatch(T providedObj, T dbObject)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {

        Class<?> cls = dbObject.getClass();
        for (Field field : cls.getDeclaredFields()) {

            String getterName = fieldToGetter(field.getName(), field.getType() == Boolean.class);
            Method getMethod = cls.getMethod(getterName);

            Object providedValue = getMethod.invoke(providedObj);

            if (providedValue == null || field.getAnnotation(DBPrefer.class) != null) {
                continue;
            }

            if (providedValue.getClass().isAssignableFrom(Collection.class)
                    && ((Collection) providedValue).size() == 0) {
                continue;
            }

            String setterName = fieldToSetter(field.getName());

            Method setterMethod = cls.getMethod(setterName, field.getType());

            setterMethod.invoke(dbObject, providedValue);
        }
        return dbObject;
    }

    public static <T extends Object> T patch(T providedObj, T dbObject) {
        try {
            return objectPatch(providedObj, dbObject);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return null;
        }
    }
}
