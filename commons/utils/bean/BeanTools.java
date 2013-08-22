package commons.utils.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

 

import org.apache.commons.beanutils.BeanUtils;

public class BeanTools {
	@SuppressWarnings("unchecked")
	public static void println(Object object) {
		System.out.println(BeanTools.bean2Stringln(object));
	}

	@SuppressWarnings("unchecked")
	public static void print(Object object) {
		System.out.println(BeanTools.bean2String(object));
	}

	@SuppressWarnings("unchecked")
	public static String bean2String(Object object) {
		if (object == null) {
			return null;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("class : ");
			sb.append(object.getClass().getName());
			sb.append(" : ");
			try {
				Map m = BeanUtils.describe(object);
				Iterator i = m.entrySet().iterator();
				while (i.hasNext()) {
					Object o = i.next();
					if (!o.toString().startsWith("class")) {
						sb.append("," + o);
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			return sb.toString();
		}
	}

	@SuppressWarnings("unchecked")
	public static String bean2Stringln(Object object) {
		if (object == null) {
			return null;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("class : ");
			sb.append(object.getClass().getName());
			sb.append(" : \n");
			try {
				Map m = BeanUtils.describe(object);
				Iterator i = m.entrySet().iterator();
				while (i.hasNext()) {
					Object o = i.next();
					if (!o.toString().startsWith("class")) {
						sb.append("	" + o + "\n");
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			return sb.toString();
		}
	}

	public static String bean2SQLString(Object object) {
		Class c = object.getClass();
		Field[] f = c.getDeclaredFields();
		for (Field field : f) {
			try {
				System.out.println(field.getName() + "--"
						+ field.getType().getSimpleName() + "="
						+ BeanUtils.getProperty(object, field.getName()));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
