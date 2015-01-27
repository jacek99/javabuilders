/**
 * 
 */
package org.javabuilders.swing.handler.type;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFormattedTextField;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;

/**
 * Handler for JFormattedTextField filds
 * @author Jacek Furmankiewicz
 */
public class JFormattedTextFieldHandler extends AbstractTypeHandler{

	public static final String FORMAT = "format";
	public static final String FORMAT_LOCALE = "locale";
	public static final String FORMAT_STYLE = "formatStyle";
	
	private static final Map<String,Integer> formatStyles = new HashMap<String, Integer>();

	public static final String FORMAT_VALUE_DATE = "date";
	public static final String FORMAT_VALUE_TIME = "time";
	public static final String FORMAT_VALUE_NUMBER = "number";
	public static final String FORMAT_VALUE_CURRENCY = "currency";	
	public static final String FORMAT_VALUE_INTEGER = "integer";
	public static final String FORMAT_VALUE_PERCENT = "percent";
	
	public static final String FORMAT_STYLE_VALUE_SHORT = "short";
	public static final String FORMAT_STYLE_VALUE_MEDIUM = "medium";
	public static final String FORMAT_STYLE_VALUE_LONG = "long";
	public static final String FORMAT_STYLE_VALUE_FULL = "full";
	
	private static final JFormattedTextFieldHandler singleton = new JFormattedTextFieldHandler();

	static {
		formatStyles.put(FORMAT_STYLE_VALUE_SHORT, DateFormat.SHORT);
		formatStyles.put(FORMAT_STYLE_VALUE_MEDIUM, DateFormat.MEDIUM);
		formatStyles.put(FORMAT_STYLE_VALUE_LONG, DateFormat.LONG);
		formatStyles.put(FORMAT_STYLE_VALUE_FULL, DateFormat.FULL);
	}
	
	/**
	 * @return Singleton
	 */
	public static JFormattedTextFieldHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	private JFormattedTextFieldHandler() {
		super(FORMAT,FORMAT_LOCALE);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		JFormattedTextField instance =  null;
		
		String formatValue = (String) typeDefinition.get(FORMAT);
		@SuppressWarnings("unused")
		String localeValue = (String)typeDefinition.get(FORMAT_LOCALE);
		if (formatValue == null) {
			instance = new JFormattedTextField();
		} else {
			@SuppressWarnings("unused")
			Format format = null;
			if (FORMAT_VALUE_DATE.equals(formatValue)) {
				Integer style = (Integer) typeDefinition.get(FORMAT_STYLE);
				if (style == null) {
					format = DateFormat.getDateInstance();
				} else {
					format = DateFormat.getDateInstance(style);
				}
			} else if (FORMAT_VALUE_TIME.equals(formatValue)) {
				Integer style = (Integer) typeDefinition.get(FORMAT_STYLE);
				if (style == null) {
					format = DateFormat.getTimeInstance();
				} else {
					format = DateFormat.getTimeInstance(style);
				}
			} else if (FORMAT_VALUE_NUMBER.equals(formatValue)) {
				format = NumberFormat.getNumberInstance();
			} else if (FORMAT_VALUE_CURRENCY.equals(formatValue)) {
				format = NumberFormat.getCurrencyInstance();
			} else if (FORMAT_VALUE_INTEGER.equals(formatValue)) {
				format = NumberFormat.getIntegerInstance();
			} else if (FORMAT_VALUE_PERCENT.equals(formatValue)) {
				format = NumberFormat.getPercentInstance();
			} else {
				throw new BuildException("Unrecognized JFormattedTextField format: {0}",formatValue);
			}
		}
		
		
		return useExistingInstance(config, process, parent, key, typeDefinition, instance);
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition,
			Object instance) throws BuildException {
		
		Node node = new Node(parent, key, typeDefinition);
		node.setMainObject(instance);
		
		return node;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<JFormattedTextField> getApplicableClass() {
		return JFormattedTextField.class;
	}

}
