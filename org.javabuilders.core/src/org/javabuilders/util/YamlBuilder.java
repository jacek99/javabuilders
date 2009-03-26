package org.javabuilders.util;

/**
 * Utility class to easily build YAML content from the Java-side, without an external YAML file.
 * Hack for the lack of embedded multi-line strings in Java...what would I give for Groovy's ''' syntax....
 * <p>Call the "_" method in multiple lines of Java code, e.g.
 * <code>
 * String yaml = new YamlBuilder().
 *        _("Yaml: ").
 *        _("    Second: Yaml").toString();
 *        
 * @author Jacek Furmankiewicz
 *
 */
public class YamlBuilder {

	private StringBuilder builder = new StringBuilder();
	
	/**
	 * Main method to add
	 * @param yamlLine
	 * @return
	 */
	public YamlBuilder _(String yamlLine) {
		builder.append(yamlLine).append("\n");
		return this;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return builder.toString();
	}
	
}
