package app.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CoreReflectUtils {

	public static Field getAnnotatedField(final Class<?> type, final Class<? extends Annotation> annotation) {

		Class<?> klass = type;
		while (klass != Object.class) {
			for (final Field field : klass.getDeclaredFields()) {
				if (field.isAnnotationPresent(annotation)) {
					return field;
				}
			}
			klass = klass.getSuperclass();
		}
		return null;
	}

	public static Object getAnnotatedFieldVal(Object obj, final Class<? extends Annotation> annotation) throws IllegalArgumentException, IllegalAccessException {

		Field field = getAnnotatedField(obj.getClass(), annotation);
		
		return field.get(obj);
	}

	public static Method getAnnotatedMethod(final Class<?> type, final Class<? extends Annotation> annotation) {

		Class<?> klass = type;
		while (klass != Object.class) {
			for (final Method method : klass.getDeclaredMethods()) {
				if (method.isAnnotationPresent(annotation)) {
					return method;
				}
			}

			klass = klass.getSuperclass();
		}
		return null;
	}

	public static Object getAnnotatedMethodValue(Object obj, final Class<? extends Annotation> annotation) throws IllegalAccessException, InvocationTargetException   {

		Method method = getAnnotatedMethod(obj.getClass(), annotation);

		return method.invoke(obj);
	}

}
