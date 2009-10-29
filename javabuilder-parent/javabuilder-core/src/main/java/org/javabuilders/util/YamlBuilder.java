package org.javabuilders.util;

import java.util.ResourceBundle;

import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;

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
	 * @param root Constructor
	 */
	public YamlBuilder(String root) {
		_(root);
	}
	
	/**
	 * Main method to add
	 * @param yamlLine
	 * @return
	 */
	private YamlBuilder _(String yamlLine) {
		builder.append(yamlLine).append("\n");
		return this;
	}
	
	/**
	 * Insert nested
	 * @return This
	 */
	public YamlBuilder ___(String yamlLine) {
		nest();
		return _(yamlLine);
	}

	/**
	 * Insert nested
	 * @return This
	 */
	public YamlBuilder _____(String yamlLine) {
		nest();
		return ___(yamlLine);
	}

	/**
	 * Insert nested
	 * @return This
	 */
	public YamlBuilder _______(String yamlLine) {
		nest();
		return _____(yamlLine);
	}

	/**
	 * Insert nested
	 * @return This
	 */
	public YamlBuilder _________(String yamlLine) {
		nest();
		return _______(yamlLine);
	}

	/**
	 * Insert nested
	 * @return This
	 */
	public YamlBuilder ___________(String yamlLine) {
		nest();
		return _________(yamlLine);
	}

	/**
	 * Insert nested
	 * @return This
	 */
	public YamlBuilder _____________(String yamlLine) {
		nest();
		return ___________(yamlLine);
	}

	/**
	 * Insert nested
	 * @return This
	 */
	public YamlBuilder _______________(String yamlLine) {
		nest();
		return _____________(yamlLine);
	}

	/**
	 * Insert nested
	 * @return This
	 */
	public YamlBuilder _________________(String yamlLine) {
		nest();
		return _______________(yamlLine);
	}

	
	//adds a level of nesting
	private void nest() {
		builder.append("    ");
	}
	
	/**
	 * Starts a databinding node
	 * @return This
	 */
	public YamlBuilder bind() {
		builder.append("bind:\n");
		return this;
	}
	
	/**
	 * Starts a validation node
	 * @return This
	 */
	public YamlBuilder validate() {
		builder.append("validate:\n");
		return this;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return builder.toString();
	}
	
	/**
	 * Clears the text
	 */
	public void clear() {
		builder.setLength(0);
	}
	
	/**
	 * Executes a build
	 * @param config
	 * @param caller
	 * @param bundles
	 * @return
	 */
	public BuildResult build(BuilderConfig config, Object caller, ResourceBundle... bundles) {
		return Builder.buildFromString(config, caller, toString(), bundles);
	}
	
}
