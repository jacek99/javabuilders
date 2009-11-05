package org.javabuilders.util.compiler;

import java.lang.StringBuilder;

/**
 * Build's a class's code
 * @author jacek
 *
 */
public class ClassStringBuilder  {

	private StringBuilder bld = new StringBuilder();
	
	/**
	 * Adds a line at 1 level of indentation
	 */
	public ClassStringBuilder _(String template, Object...params) {
		return add(0,template,params);
	}
	
	public ClassStringBuilder ___(String template, Object...params) {
		return add(1,template,params);
	}
	
	public ClassStringBuilder _____(String template, Object...params) {
		return add(2,template,params);
	}
	
	public ClassStringBuilder _______(String template, Object...params) {
		return add(3,template,params);
	}
	
	public ClassStringBuilder _________(String template, Object...params) {
		return add(4,template,params);
	}
	
	public ClassStringBuilder ___________(String template, Object...params) {
		return add(5,template,params);
	}
	
	private ClassStringBuilder add(int level, String template, Object...params) {
		for(int i = 0; i < level;i++) {
			bld.append("\t");
		}
		bld.append(String.format(template, params)).append("\n");
		return this;
	}
	
	@Override
	public String toString() {
		return bld.toString();
	}
}
