package org.javabuilders.util;

/**
 * Poor man's tuple
 * @author Jacek Furmankiewicz
 *
 */
public class Tuple2<T0,T1> {
	private T0 obj0;
	private T1 obj1;
	
	public Tuple2(T0 obj0, T1 obj1) {
		this.obj0 = obj0;
		this.obj1 = obj1;
	}
	
	public T0 get0() {
		return obj0;
	}
	
	public T1 get1() {
		return obj1;
	}
}
