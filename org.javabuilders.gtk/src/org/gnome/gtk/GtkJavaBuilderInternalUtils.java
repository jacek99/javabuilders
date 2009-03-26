package org.gnome.gtk;


/**
 * Hack class for getting around some permissions in java-gnome
 * @author Jacek Furmankiewicz
 *
 */
public class GtkJavaBuilderInternalUtils {

	/**
	 * @param stockId Stock ID
	 * @return Stock from internal java-gnome cache
	 */
	public static Stock getStock(String stockId) {
		return Stock.instanceFor(stockId);
	}

}
