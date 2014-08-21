/**
 * @(#)ObjectBuilder.java			
 * @update		10/03/08
 */
package pf.tools.reflect;
/**
 * 类作用：创建类对象
 * 
 * @author 殷云龙
 * @version 4.0.0 
 * */
public class ObjectBuilder {

	private Object target;

	private ClassLoader loader;
	/**
	 * 作用：需要创建的类对象
	 * 
	 * @param	className			所需类的完全限定名
	 * @param	args[]				实例化化类，构造函数需要的参数
	 * @param	loader				指定的类加载器
	 * */
	public ObjectBuilder(String className, Object args[], ClassLoader loader) {
		this.loader = loader;
		buildTarget(className, args);
	}
	/**
	 * 作用：构造方法重载
	 * 
	 * @param	className			指定的类加载器
	 * @param	constructArgs[]		实例化化类，构造函数需要的参数
	 * */
	public ObjectBuilder(String className, Object constructArgs[]) {
		this(className, constructArgs, null);
	}
	/**
	 * 作用：构造方法重载
	 * 
	 * @param	className			所需类的完全限定名
	 * @param	loader				指定的类加载器
	 * */
	public ObjectBuilder(String className, ClassLoader loader) {
		this(className, null, loader);
	}
	/**
	 * 作用：构造方法重载
	 * 
	 * @param	className			所需类的完全限定名
	 * */
	public ObjectBuilder(String className) {
		this(className, null, null);
	}
	/**
	 * 作用：设置target对象中的属性
	 * 
	 * @param	property			target对象中的属性名称
	 * @param	value				属性值
	 * */
	public void setProperty(String property, Object value) {
		ReflectSupport.setProperty(target, property, value);
	}
	/**
	 * 作用：实例化类
	 * 
	 * @param	className			所需类的完全限定名
	 * @param	args[]				实例化化类，构造函数需要的参数
	 * */
	protected void buildTarget(String className, Object args[]) {
		try {
			if (args != null && args.length > 0)
				target = ReflectSupport.newObject(className, args, loader);
			else
				target = ReflectSupport.newObject(className, loader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object getTarget() {
		return target;
	}

}
