package org.javabuilders.util;

import java.util.Map;
import java.util.Set;

import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.ChildrenCardinalityException;
import org.javabuilders.Node;
import org.javabuilders.TypeDefinition;

/**
 * Util methods for validating children cardinality
 * @author Jacek Furmankiewicz
 *
 */
public class ChildrenCardinalityUtils {

	/**
	 * Checks if the child cardinality is correct in the current node
	 * @param config Config
	 * @param current Current node
	 */
	public static void checkChildrenCardinality(BuilderConfig config, Node current) {
		
		if (!current.getKey().equals(Builder.CONTENT)) {
			Object obj = current.getMainObject();
			Map<Class<?>,int[]> childrenCardinalities = TypeDefinition.getChildrenCardinality(config, obj.getClass());
	
			//check that no unexpected types are found
			checkChildrenForUnexpectedTypes(current, current.getChildNodes(), childrenCardinalities.keySet());
			checkChildrenForUnexpectedTypes(current, current.getContentNodes(), childrenCardinalities.keySet());
			
			//check that the expected types are of the correct cardinality
			for(Class<?> type : childrenCardinalities.keySet()) {
				int[] limits = childrenCardinalities.get(type);
				//if 1 type defined only and can be only one instance, it should not be a list, but a single value
				if (childrenCardinalities.size() == 1 && limits[1] == 1) {
					
					//single value
					
					//check that only no other types are there
					Set<Node> set = current.getChildNodes();
					if (set.size() < limits[0]) {
						throw new ChildrenCardinalityException("At least {0} child of type {1} is required: {2}",limits[0],type, current.getProperties());
					} else if (set.size() > limits[1]) {
						throw new ChildrenCardinalityException("No more than {0} child of type {1} is allowed: {2}",limits[1],type, current.getProperties());
					} 
					
					//make sure no list either
					set = current.getContentNodes(Object.class);
					if (set.size() > 0) {
						throw new ChildrenCardinalityException("No child nodes are allowed: {0}", current.getProperties());
					}
				} else {
					
					//make sure none as child nodes
					Set<Node> set  = current.getChildNodes(type);
					if (set != null && set.size() > 0) {
						
						for(Node node : set) {
							//the Content node comes back, but we should ignore it
							if (!node.getKey().equals(Builder.CONTENT)) {
								throw new ChildrenCardinalityException("{0}: expected a list of type {1}, but found it as a child node instead.\n{1}", 
										current.getMainObject().getClass().getSimpleName(),
										type, current.getProperties());
							}
						}
					}
					
					//list
					set  = current.getContentNodes(type);
					if (set.size() < limits[0]) {
						throw new ChildrenCardinalityException("At least {0} children of type {1} are required: {2}",limits[0],type, current.getProperties());
					} else if (set.size() > limits[1]) {
						if (limits[1] == 0 && Object.class.equals(type)) {
							throw new ChildrenCardinalityException("No children are allowed under {0}: {1}", obj.getClass().getSimpleName(), current.getProperties());
						} else {
							throw new ChildrenCardinalityException("No more than {0} children of type {1} are allowed under {2}: {3}",limits[1],type,
									obj.getClass().getSimpleName(), current.getProperties());
						}
					}
	
				}
			}
		}
	}
	
	//checks if any unexpected types are present
	private static void checkChildrenForUnexpectedTypes(Node current, Set<Node> nodes, Set<Class<?>> allowedTypes) {
		
		if (nodes != null) {
			for(Node node : nodes) {
				if (!node.getKey().equals(Builder.CONTENT)) {
					boolean assignable = false;
					for(Class<?> type : allowedTypes) {
						if (type.isAssignableFrom(node.getMainObject().getClass())) {
							assignable = true;
							break;
						}
					}
					if (!assignable) {
						throw new ChildrenCardinalityException("{0}: Found unexpected type {1}.\nNot in list of allowed types {2}.\n{3}",
								current.getMainObject().getClass().getSimpleName(),
								node.getMainObject().getClass().getSimpleName(),
								allowedTypes, current.getProperties());
					}
				}
			}
		}
	}

}
