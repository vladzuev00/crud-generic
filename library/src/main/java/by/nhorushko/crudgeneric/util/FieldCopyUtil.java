package by.nhorushko.crudgeneric.util;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Set;

public class FieldCopyUtil {

    public static Object copy(Object source, Object target, Set<String> ignore) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (ignore.contains(field.getName())) {
                continue;
            }
            copyField(field, source, target);
        }
        return target;
    }

    private static void copyField(Field field, Object source, Object target) {
        try {
            Object o = FieldUtils.readField(field, source, true);
            FieldUtils.writeField(field, target, o, true);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
