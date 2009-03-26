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
public class IntArrayAsValueHandler implements ITypeAsValueHandler<int[]> {

	private static final IntArrayAsValueHandler singleton = new IntArrayAsValueHandler();
	
	/**
	 * @return Singleton
	 */
	public static IntArrayAsValueHandler getInstance() {return singleton;}
	
	private IntArrayAsValueHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getInputValueSample()
	 */
	public String getInputValueSample() {
		return "weights=(30,40,30)";
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getRegex()
	 */
	public String getRegex() {
		return IntegerArrayAsValueHandler.REGEX_MATCHER;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeAsValueHandler#getValue(org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public int[] getValue(BuildProcess process, Node node, String key,
			Object inputValue) throws BuildException {
		
		List<Long> values = (List<Long>)inputValue;
		int[] returnValue = new int[values.size()];
		for(int i = 0; i < values.size();i++) {
			returnValue[i] = values.get(i).intValue();
		}
		return returnValue;
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<int[]> getApplicableClass() {
		return int[].class;
	}

}
