package org.javabuilders;

/**
 * Defines any interface that applies to a particular class type
 * @author Jacek Furmankiewicz
 *
 */
public interface IApplicable {

	/**
	 * Return the type that this applies to. Usually it's the class highest
	 * in the ancestry hierarchy that this applies to
	 * @return Applicable class type
	 */
	Class<?> getApplicableClass();
}
