package org.javabuilders.handler.type;

import java.util.List;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeAsValueHandler;

/**
 * Handles integer array, e.g. weight=(50,20,30)
 * @author Jacek Furmankiewicz
 */
public class IntegerArrayAsValueHandler implements ITypeAsValueHandler<Integer[]> {

	private static final IntegerArrayAsValueHandler singleton = new IntegerArrayAsValueHandler();
	
	public static final String REGEX_MATCHER = "\\[\\s*(([0-9]+\\s*)|([0-9]+\\s*,\\s*[0-9]+\\s*)+)\\s*\\]";;
	
	/**
	 * @return Singleton
	 */
	public static IntegerArrayAsValueHandler getInstance() {return singleton;}
	
	private IntegerArrayAsValueHandler() {}
	
	public String getInputValueSample() {
		return "weights=(30,40,30)";
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
	 */
	public String getRegex() {
		return REGEX_MATCHER;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public Integer[] getValue(BuildProcess process, Node node, String key,
			Object inputValue) throws BuildException {
		
		List<Long> values = (List<Long>)inputValue;
		Integer[] returnValue = new Integer[values.size()];
		for(int i = 0; i < values.size();i++) {
			returnValue[i] = values.get(i).intValue();
		}
		return returnValue;
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<Integer[]> getApplicableClass() {
		return Integer[].class;
	}

}
