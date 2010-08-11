/**
 * 
 */
package org.javabuilders.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.TypeDefinition;

/**
 * Generic handler for int values that handles hard-coded int constants. Allows you to pass the raw int value, the hard-coded 
 * String constant (e.g. EXIT_ON_CLOSE) or its camel case equivalent (e.g. exitOnClose), e.g.<br/>
 * <code>JFrame(defaultCloseOperation=exitOnClose)</code>
 * @author Jacek Furmankiewicz
 *
 */
public class IntegerAsValueHandler implements ITypeAsValueHandler<Integer> {

	private static String regex = "\\d+|[a-zA-Z0-9_]+";
	private static final IntegerAsValueHandler singleton = new IntegerAsValueHandler();
	
	/**
	 * @return Singleton
	 */
	public static IntegerAsValueHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	private IntegerAsValueHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
	 */
	public String getInputValueSample() {
		return "3 | SOME_CONSTANT_VALUE | someConstantValue";
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
	 */
	public String getRegex() {
		return regex;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
	 */
	public Integer getValue(BuildProcess process, Node node, String key,
			Object inputValue) throws BuildException {
		Integer value = null;
		
		if (inputValue instanceof Integer) {
			value = (Integer)inputValue;
		} else if (inputValue instanceof Long) {
			Long lValue = (Long)inputValue;
			value = lValue.intValue();
		} else if (inputValue instanceof String) {

			//may be a public static final CONSTANT 
			Class<?> mainClass = node.getMainObject().getClass();
			
			Class<?> constantsClass = TypeDefinition.getPropertyConstantsClass(process.getConfig(), 
					mainClass, key);
			if (constantsClass == null) {
				constantsClass = mainClass;
			}
			
			Field[] fields = constantsClass.getFields();
			for(Field field : fields) {
				
				int mod = field.getModifiers();
				
				if (Modifier.isPublic(mod) && Modifier.isFinal(mod) && Modifier.isStatic(mod) && field.getType().equals(int.class)) {
					String name = field.getName();
					String camelCase = TypeDefinition.getShortEnumConstant(field.getName());
					
					if (inputValue.equals(name) || inputValue.equals(camelCase)) { //value entered like the constant, e.g. EXIT_ON_CLOSE
						try {
							//value = (Integer)field.get(node.getMainObject());
							value = (Integer)field.get(node.getMainObject());
							break;
						} catch (Exception e) {
							throw new BuildException(e);
						}
					}
				}
			}
		} 
		
		if (value != null) {
			return value;
		} else {
			throw new BuildException("Unable to map value \"{0}\" to Integer",inputValue);
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return int.class;
	}

}
