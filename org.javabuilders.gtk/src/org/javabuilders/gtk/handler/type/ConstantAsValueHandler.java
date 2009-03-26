package org.javabuilders.gtk.handler.type;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.beanutils.PropertyUtils;
import org.freedesktop.bindings.Constant;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.TypeDefinition;
import org.javabuilders.handler.ITypeAsValueHandler;

public class ConstantAsValueHandler implements ITypeAsValueHandler<Constant> {

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
	 */
	public String getInputValueSample() {
		return "tabPosition=left | tabPosition=LEFT";
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
	 */
	public String getRegex() {
		return ".+";
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
	 */
	public Constant getValue(BuildProcess process, Node node, String key,
			Object inputValue) throws BuildException {
		
		Constant c = null;
		try {
			Class<?> type = PropertyUtils.getPropertyDescriptor(node.getMainObject(), key).getPropertyType();
			Field[] fields = type.getFields();
			for(Field field : fields) {
				
				int mod = field.getModifiers();
				if (Modifier.isPublic(mod) && Modifier.isFinal(mod) && Modifier.isStatic(mod) && Constant.class.isAssignableFrom(field.getType())) {
					
					String name = field.getName();
					String camelCase = TypeDefinition.getShortEnumConstant(field.getName());
					
					if (inputValue.equals(name) || inputValue.equals(camelCase)) { //value entered like the constant, e.g. EXIT_ON_CLOSE
						try {
							c = (Constant)field.get(null);
							break;
						} catch (Exception e) {
							throw new BuildException(e);
						}
					}
				}
			}
			
		} catch (Exception e) {
			throw new BuildException(e,"Failed to process Constant property {0}", key);
		}
		
		return c;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<Constant> getApplicableClass() {
		return Constant.class;
	}

}
