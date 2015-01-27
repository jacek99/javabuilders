package org.javabuilders.swing.plugin.glazedlists.compiler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.javabuilders.BuildException;
import org.javabuilders.util.PropertyUtils;

/**
 * This class is unused. It relies on the JavaCC compiler, which is only in the JDK, not the JRE
 * Hence. we cannot use it (unless Jigsaw makes it possible in Java 7)
 * @author Jacek Furmankiewicz
 *
 */
@Deprecated
public class JavaCCUtils {

	private static final Random RANDOM = new Random();
	
	private static JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	private static JavaFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));
	
	/**
	 * Compiles source code into a class that gets loaded in the current classloaded
	 * @param fullName
	 * @param src
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> compile(String fullName, String src) throws ClassNotFoundException {
		List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
		jfiles.add(new CharSequenceJavaFileObject(fullName, src));

		// We specify a task to the compiler. Compiler should use our file
		// manager and our list of "files".
		// Then we run the compilation with call()
		compiler.getTask(null, fileManager, null, null, null, jfiles).call();

		// Creating an instance of our compiled class and
		// running its toString() method
		Class<?> type = fileManager.getClassLoader(null).loadClass(fullName);
		return type;
	}
	
	/**
	 * Based on code from:
	 * http://www.javablogging.com/dynamic-in-memory-compilation/
	 * 
	 * @param fullName
	 *            Class name
	 * @param src
	 *            Class body
	 * @return Instantiated class
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object compileAndInstantiate(String fullName, String src) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> type = compile(fullName, src);
		return type.newInstance();
	}
	
	/**
	 * Generates a guaranteed unique class name
	 * @param baseClass
	 * @return
	 */
	public static String generateClassName(Class<?> baseClass) {
		//return PACKAGE_NAME + baseClass.getSimpleName() + "_" + Math.random() + "_" + Calendar.getInstance().getTimeInMillis();
		return baseClass.getSimpleName() + "_" + Math.abs(RANDOM.nextInt()) + "_" + Calendar.getInstance().getTimeInMillis();
	}
	
	/**
	 * Generates a class signature for a superclass or interface
	 * @param baseClassOrInterface
	 * @return
	 */
	public static String generateClassSignature(String name, Class<?> baseClassOrInterface) {
		if (baseClassOrInterface.isInterface()) {
			return "public class " + name + " implements " + baseClassOrInterface.getName() + " {\n";
		} else {
			return "public class " + name + " extends " + baseClassOrInterface.getName() + " {\n";
		}
	}

	/**
	 * Compiles a new comparator
	 * @param type Type
	 * @param fields Fields to be included in comparator
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Comparator newComparator(Class<?> type, List<String> fields) {
		return newComparator(type, fields.toArray(new String[fields.size()]));
	}
	
	/**
	 * Compiles a new comparator
	 * @param rawType Type
	 * @param fields Fields to be included in comparator
	 * @return
	 */
	public static Comparator<?> newComparator(Class<?> rawType, String... fields) {
		Comparator<?> c = null;
		
		ClassStringBuilder bld = new ClassStringBuilder();
		String name = generateClassName(Comparator.class);
		
		Class<?> type = rawType;
		if (type.isPrimitive()) {
			if (int.class.equals(type)) {
				type = Integer.class;
			} else if (long.class.equals(type)) {
				type = Long.class;
			} else if (short.class.equals(type)) {
				type = Short.class;
			} else if (double.class.equals(type)) {
				type = Double.class;
			} else if (float.class.equals(type)) {
				type = Float.class;
			} else if (boolean.class.equals(type)) {
				type = Boolean.class;
			}
		}
		
		bld._("public class %s implements %s<%s> {",name,Comparator.class.getName(),type.getName())
			.___("public int compare(%1$s o1, %1$s o2) {",type.getName())
			._____("int compare = 0;");

		//create the comparison for each column
		if (fields.length == 0) {
			//build a Comparator against the class itself
			bld._____("compare = o1.compareTo(o2);");
		} else {
		
			//build a Comparator against the class's fields
			for(String col : fields) {
				bld._____("if (compare == 0) {");
	
				String getter = PropertyUtils.getGetterName(col);
				Class<?> returnType = PropertyUtils.verifyGetter(type, getter,short.class,Short.class,int.class,Integer.class,long.class,Long.class,double.class,
						Double.class,String.class,char.class,Character.class,Comparable.class);
				if (returnType.isPrimitive()) {
					bld._______("compare = o1.%1$s() - o2.%1$s();",getter);
				} else {
					bld._______("compare = o1.%1$s().compareTo(o2.%1$s());",getter);
				}
				bld._____("}");
			}
			
		}
		bld._____("return compare;");
		bld.___("}");
		bld._("}");
		
		try {
			c = (Comparator<?>)compileAndInstantiate(name, bld.toString());
			return c;
		} catch (Exception e) {
			throw new BuildException("Failed to compile Comparator: {0}\n{1}",e.getMessage(),bld.toString());
		} 
	}
	
	


}
