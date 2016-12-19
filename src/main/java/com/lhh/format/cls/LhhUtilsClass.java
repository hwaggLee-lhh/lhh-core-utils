package com.lhh.format.cls;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 类工具类
 * 
 * @author huage
 *
 */
public class LhhUtilsClass {

	/**
	 * 获取类中的所有属性明名称和值（因涉及到可能会是继承关系的父类，所以从f中去属性名称，从f2中取值，两个可以一样，也可以使父类）
	 * @param f:读取属性类(如果取父类的，则这里为父类)
	 * @param o:取值类(如果取父类的，则这里为子类)
	 * @return
	 */
	public static Map<String, Object> readClassFileAllComponentsName(Object f,Object f2) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (f == null)
			return map;
		Field[] fields = f.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			try {
				// 对于每个属性，获取属性名
				String varName = fields[i].getName();
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(f2);
				map.put(varName, o);
				//System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;
	}
	
	
	
	

	/**
	 * 循环向上转型, 获取对象的 DeclaredMethod
	 * 
	 * @param object
	 *            : 子类对象
	 * @param methodName
	 *            : 父类中的方法名
	 * @param parameterTypes
	 *            : 父类中的方法参数类型
	 * @return 父类中的方法对象
	 */

	public static Method getDeclaredMethod(Object object, String methodName,
			Class<?>... parameterTypes) {
		Method method = null;

		for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			try {
				method = clazz.getDeclaredMethod(methodName, parameterTypes);
				return method;
			} catch (Exception e) {
				// 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
				// 如果这里的异常打印或者往外抛，则就不会执行clazz =
				// clazz.getSuperclass(),最后就不会进入到父类中了

			}
		}

		return null;
	}

	/**
	 * 直接调用对象方法, 而忽略修饰符(private, protected, default)
	 * 
	 * @param object
	 *            : 子类对象
	 * @param methodName
	 *            : 父类中的方法名
	 * @param parameterTypes
	 *            : 父类中的方法参数类型
	 * @param parameters
	 *            : 父类中的方法参数
	 * @return 父类中方法的执行结果
	 */

	public static Object invokeMethod(Object object, String methodName,
			Class<?>[] parameterTypes, Object[] parameters) {
		// 根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
		Method method = getDeclaredMethod(object, methodName, parameterTypes);

		// 抑制Java对方法进行检查,主要是针对私有方法而言
		method.setAccessible(true);

		try {
			if (null != method) {

				// 调用object 的 method 所代表的方法，其方法的参数是 parameters
				return method.invoke(object, parameters);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 循环向上转型, 获取对象的 DeclaredField
	 * 
	 * @param object
	 *            : 子类对象
	 * @param fieldName
	 *            : 父类中的属性名
	 * @return 父类中的属性对象
	 */

	public static Field getDeclaredField(Object object, String fieldName) {
		Field field = null;

		Class<?> clazz = object.getClass();

		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				return field;
			} catch (Exception e) {
				// 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
				// 如果这里的异常打印或者往外抛，则就不会执行clazz =
				// clazz.getSuperclass(),最后就不会进入到父类中了

			}
		}

		return null;
	}

	/**
	 * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
	 * @param object
	 *            : 子类对象
	 * @param fieldName
	 *            : 父类中的属性名
	 * @param value
	 *            : 将要设置的值
	 */

	public static void setFieldValue(Object object, String fieldName,
			Object value) {

		// 根据 对象和属性名通过反射 调用上面的方法获取 Field对象
		Field field = getDeclaredField(object, fieldName);
		// 抑制Java对其的检查
		field.setAccessible(true);
		try {
			// 将 object 中 field 所代表的值 设置为 value
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
	 * 
	 * @param object
	 *            : 子类对象
	 * @param fieldName
	 *            : 父类中的属性名
	 * @return : 父类中的属性值
	 */

	public static Object getFieldValue(Object object, String fieldName) {

		// 根据 对象和属性名通过反射 调用上面的方法获取 Field对象
		Field field = getDeclaredField(object, fieldName);
		// 抑制Java对其的检查
		field.setAccessible(true);
		try {
			// 获取 object 中 field 所代表的属性值
			return field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	

    private static String getPrefixPrivate() {
        StackTraceElement ste = new Throwable().getStackTrace()[2];
        return ste.getClassName() + "." + ste.getMethodName() + ".";
    }
    
    public static String getPrefix() {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        return ste.getClassName() + "." + ste.getMethodName() + ".";
    }
    
    
    /**
     * 得到堆栈中第三个调用的类名，效果如下：
     * public class UserCtrl {
     *     public List findList() {
     *         System.out.println(Ctrl.getMethodName());
     *     };
     * }
     * 打印输出“UserCtrl”
     * @return
     */
    public static String getClassName() {
        return new Throwable().getStackTrace()[2].getClassName();
    }
    
    /**
     * 得到堆栈中第三个调用的方法名，效果如下：
     * public class UserCtrl {
     *     public List findList() {
     *         System.out.println(Ctrl.getMethodName());
     *     };
     * }
     * 打印输出“findList”
     * @return
     */
    public static String getMethodName() {
        return new Throwable().getStackTrace()[2].getMethodName();
    }
    
	public static boolean isFromMethod(String mothodName) {
        String methodName = new Throwable().getStackTrace()[2].getMethodName();
        return methodName.equals(mothodName);
    }
    
    public static boolean isNotFromMethod(String mothodName) {
        String methodName = new Throwable().getStackTrace()[2].getMethodName();
        return !methodName.equals(mothodName);
    }
    
    public static String testGetPrefix() {
        return getPrefixPrivate();
    }
    
    public static String testGetClassName() {
        return getClassName();
    }
    
    public static String testGetMethodName() {
        return getMethodName();
    }
    
    public static void main(String[] args) {
        System.out.println(testGetPrefix());
        System.out.println(testGetClassName());
        System.out.println(testGetMethodName());
    }
}
