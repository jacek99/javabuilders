package org.javabuilders.swing.plugin.glazedlists.compiler;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.codehaus.janino.ClassBodyEvaluator;
import org.codehaus.janino.CompileException;
import org.codehaus.janino.Parser.ParseException;
import org.codehaus.janino.Scanner.ScanException;
import org.javabuilders.BuildException;
import org.javabuilders.util.PropertyUtils;

public class CompilerUtils {

	private static final Random RANDOM = new Random();
	
	/**
	 * Compiles source code into a class that gets loaded in the current classloader
	 * @param fullName
	 * @param classBody
	 * @return
	 * @throws ClassNotFoundException
	 * @throws ScanException 
	 * @throws ParseException 
	 * @throws CompileException 
	 */
	public static Class<?> compile(String className, String classBody, Class<?> type, Class<?>...interfaces) throws Exception {
		ClassBodyEvaluator eval = new ClassBodyEvaluator();
		eval.setParentClassLoader(Thread.currentThread().getContextClassLoader());
		eval.setClassName(className);
		eval.setExtendedType(type);
		eval.setImplementedTypes(interfaces);
		eval.cook(classBody);
		
		return eval.getClazz();
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
		
		bld.___("public int compare(Object ob1, Object ob2) {")
			._____("int compare = 0;")
			._____("%1$s o1 = (%1$s)ob1;",type.getName())
			._____("%1$s o2 = (%1$s)ob2;",type.getName());

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
		
		try {
			Class<?> comparatorClass = compile(name, bld.toString(),Object.class,Comparator.class);
			c = (Comparator<?>)comparatorClass.newInstance();
			return c;
		} catch (Exception e) {
			throw new BuildException("Failed to compile Comparator: {0}\n{1}",e.getMessage(),bld.toString());
		} 
	}
	
	


}
