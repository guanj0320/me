/**
 * @(#)ReflectSupport.java			
 * @update		10/03/08
 */
package pf.tools.reflect;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 类作用：反射支持类，其中可以实例化类对象，并获取对象中的属性，并对其设置，亦可调用对象中的方法
 * 
 * 
 * @author 殷云龙
 * @version 4.0.0 
 * */
public class ReflectSupport {
	public ReflectSupport() {
	}
	/**
	 * 使用给定的类加载器，返回与带有给定字符串名的类或接口相关联的 Class 对象。（以 getName 所返回的格式）
	 * 给定一个类或接口的完全限定名，此方法会试图定位、加载和链接该类或接口。指定的类加载器用于加载该类或接口。
	 * 如果参数 loader 为 null，则该类通过引导类加载器加载。只有 initialize 参数为 true 且以前未被初始化时，
	 * 才初始化该类。
	 * 
	 * @param	className		所需类的完全限定名
	 * @param	loader			指定的类加载器
	 * @return	Object			返回初始化后的类
	 * */
	public static Object newObject(String className, ClassLoader loader) {
		try {
			if ("".equals(className) || className == null) {
				return null;
			} else {
				Class cls = Class.forName(className, true, loader);
				return cls.newInstance();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 作用：newObject方法的重载，其中的loader获取的是ReflectSupport的类加载器
	 * 		并重新调用 newObject(className, loader)方法
	 * 
	 * @param	className		所需类的完全限定名
	 * @return	Object			返回初始化后的类
	 * */
	public static Object newObject(String className) {
		ClassLoader loader = ReflectSupport.class
				.getClassLoader();
		return newObject(className, loader);
	}
	/**
	 * 作用：获取类后，通过构造函数实例化该类
	 * 
	 * @param	className		所需类的完全限定名
	 * @param	args[]			所需创建类的参数
	 * @param	loader			指定的类加载器
	 * @return	Object			返回初始化后的类
	 * */
	public static Object newObject(String className, Object args[],
			ClassLoader loader) {
		try {
			Class cls = Class.forName(className, true, loader);
			return ConstructorUtils.invokeConstructor(cls, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 作用：通过构造函数实例化该类
	 * 
	 * @param	cls				指定类
	 * @param	args[]			构造方法的参数
	 * @return	Object			返回初始化后的类
	 * */
	public static Object newObject(Class cls, Object args[]) {
		try {
			return ConstructorUtils.invokeConstructor(cls, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 作用：该方法中的loader是获取与当前线程
	 * 
	 * @param	cls				指定类
	 * @param	args[]			构造方法的参数
	 * @return	Object			返回初始化后的类
	 * */
	public static Object newObject(String className, Object args[]) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		return newObject(className, args, loader);
	}
	/**
	 * 作用：获取对象中的属性
	 * 
	 * @param	target			传入的对象名称
	 * @param	property		对象中的属性名称
	 * */
	public static Object getProperty(Object target, String property) {
		try {
			return PropertyUtils.getProperty(target, property);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 作用：设置传入对象中的属性
	 * 
	 * @param	target			传入的对象名称
	 * @param	property		对象中的属性名称
	 * @param	value			属性对应的值
	 * */
	public static void setProperty(Object target, String property, Object value) {
		try {
			BeanUtils.setProperty(target, property, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 作用：该方法通过反射调用传入对象中的方法
	 * 
	 * @param	target			传入的对象名称	
	 * @param	methodName		调用的方法名称
	 * @param	args[]			方法中涉及的参数
	 * @return	Object			调用方法后的返回值
	 * */
	public static Object invokeMethod(Object target, String methodName,
			Object args[]) throws Exception {
		return MethodUtils.invokeMethod(target, methodName, args);
	}
	/**
	 * 作用：通过该方法可以判断对应的方法在对象中是否可以访问
	 * 
	 * @param	target			传入的对象名称	
	 * @param	methodName		调用的方法名称
	 * @param	argTypes[]		方法中涉及的参数
	 * @return	Object			当对应的方法可以访问时，返回true，反之返回false
	 * */
	public static boolean exist(Object target, String methodName,
			Class argTypes[]) {
		return MethodUtils.getAccessibleMethod(target.getClass(), methodName,
				argTypes) != null;
	}
	/**
	 * 作用：exist重载
	 * 
	 * @param	target			传入的类名称	
	 * @param	methodName		调用的方法名称
	 * @param	argTypes[]		方法中涉及的参数
	 * @return	Object			当对应的方法可以访问时，返回true，反之返回false
	 * */
	public static boolean exist(Class target, String methodName,
			Class argTypes[]) {
		return MethodUtils.getAccessibleMethod(target, methodName, argTypes) != null;
	}
	/**
	 * 作用：通过类的完全限定名，获取类
	 * 
	 * @param	clsName			所需类的完全限定名
	 * @param	Class			返回反射后获得的类名称
	 * */
	public static Class forClassName(String clsName) {
		try {
			return Class.forName(clsName);
		} catch (Throwable throwable) {
			return null;
		}
	}
}
