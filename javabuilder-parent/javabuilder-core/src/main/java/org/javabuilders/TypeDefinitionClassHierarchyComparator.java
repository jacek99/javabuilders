/**
 * 
 */
package org.javabuilders;

import java.util.Comparator;

/**
 * Comparator class used for sorting classes by 
 * @author jacek
 *
 */
public class TypeDefinitionClassHierarchyComparator implements Comparator<Object> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		if (arg0 instanceof TypeDefinition && arg1 instanceof TypeDefinition) {
			TypeDefinition def0 = (TypeDefinition)arg0;
			TypeDefinition def1 = (TypeDefinition)arg1;
			
			if (def0.getApplicableClass().equals(def1.getApplicableClass())) {
				return 0;
			} else if (def0.getApplicableClass().isAssignableFrom(def1.getApplicableClass())) {
				return 1;
			} else {
				return -1;
			}
			
		} else {
			throw new BuildException("Unexpected type");
		}
	}

}
