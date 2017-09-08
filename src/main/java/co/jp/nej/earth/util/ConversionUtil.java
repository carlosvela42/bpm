package co.jp.nej.earth.util;

import java.util.ArrayList;
import java.util.List;

public class ConversionUtil {
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj != null) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
        }
        return result;
    }

    /*
    Use this method only when casting object like Path<?>
     */
    public static <T> T castObject(Object obj, Class<T> clazz) {
        if (obj != null) {
            return clazz.cast(obj);
        }
        return null;
    }
}
