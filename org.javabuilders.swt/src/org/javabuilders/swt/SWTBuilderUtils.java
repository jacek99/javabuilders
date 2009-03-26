package org.javabuilders.swt;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.javabuilders.BuildException;
import org.javabuilders.Node;
import org.javabuilders.TypeDefinition;

/**
 * SWT Builder utils
 * @author Jacek Furmankiewicz
 *
 */
public class SWTBuilderUtils {

	/**
	 * Get the SWT.constant value from a string description
	 * @param style Style
	 * @return SWT constant
	 * @throws BuildException Thrown if unable to get value
	 */
	@SuppressWarnings("unchecked")
	public static int getSWTStyle(Object data) throws BuildException {
		
		int value = SWT.NONE;
		
		List<String> styles;
		if (data instanceof List) {
			styles = (List<String>) data;
		} else {
			styles =  new ArrayList<String>();
			styles.add(String.valueOf(data));
		}
		
		for(String style : styles) {
			int styleValue = SWT.NONE;
			boolean found = false;
			
			Field[] fields = SWT.class.getFields();
			
			for(Field field : fields) {
				
				int mod = field.getModifiers();
				if (Modifier.isPublic(mod) && Modifier.isFinal(mod) && 
						Modifier.isStatic(mod) && field.getType().equals(int.class)) {
					String name = field.getName();
					String camelCase = TypeDefinition.getShortEnumConstant(field.getName());
					
					if (style.equals(name) || style.equals(camelCase)) { //value entered like the constant, e.g. EXIT_ON_CLOSE
						try {
							styleValue = field.getInt(null);
							found = true;
							break;
						} catch (Exception e) {
							throw new BuildException(e);
						}
					}
				}
			}
			
			if (found) {
				value = value | styleValue;
			} else {
				throw new BuildException("\"{0}\" does not correspond to a valid SWT constant",style);
			}
		}
		
		return value;
	}
	
	/**
	 * Gets the SWT constant value from exact name, e.g. "SHIFT"
	 * @param constantName Exact constant name
	 * @return value
	 */
	public static int getSWTConstantFromExactName(String constantName) 
		throws BuildException {
		int value = SWT.NONE;
		
		try {
			Field field = SWT.class.getField(constantName);
			value = field.getInt(null);
		} catch (Exception e) {
			throw new BuildException("{0} is not a valid SWT int constant name",constantName);
		}
		
		return value;
	}
	
	/**
	 * Gets the first shell
	 * @param node Node
	 * @return
	 */
	public static Shell getShell(Node node) {
		Shell shell = null;
		
		if (node != null) {
			Object main = node.getMainObject();
			if (main instanceof Control) {
				Control c = (Control) main;
				shell = c.getShell();
			} else {
				//keep looking
				return getShell(node.getParent());
			}
		}
		
		return shell;
	}
	
	/**
	 * Splits a binding expresion pointing to a nested property into two separate components
	 * for easier parsisng, as a workaround for the limitations of JFace Databinding
	 * @param expression Expression
	 * @return 0 = path to object, 1 = property name on the object
	 */
	public static String[] getNestedBindingExpressionParts(String expression) {
		String[] parts = new String[2];
		
		int pos = expression.lastIndexOf(".");
		if (pos > 0) {
			parts[0] = expression.substring(0,pos);
			parts[1] = expression.substring(pos + 1);
		} else {
			throw new BuildException("{0} is not a nested binding expression", expression);
		}
		
		return parts;
	}
	

}
