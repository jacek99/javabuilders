/**
 * 
 */
package org.javabuilders.layout;

import java.util.ArrayList;
import java.util.List;



/**
 * Describes a particular cell within a layout and the controls in it
 * @author Jacek Furmankiewicz
 */
public class LayoutCell {

	/**
	 * Standard character used for spanning information
	 */
	public static final char SPAN_INDICATOR='+';
	
	/**
	 * Standard character used to indicate spanning across an entire row or column
	 */
	public static final char MAX_SPAN_VALUE = '*';
	
	private int columnIndex = 0;
	private int rowIndex = 0;
	private Flow flow = Flow.HORIZONTAL;
	
	private List<ControlConstraint> controls = new ArrayList<ControlConstraint>();
	
	/**
	 * Constructor
	 * @param rowIndex Initial row index
	 * @param cellIndex Initial cell index
	 */
	public LayoutCell(int rowIndex, int cellIndex) {
		this.rowIndex = rowIndex;
		this.columnIndex = cellIndex;
	}

	/**
	 * @return the controls
	 */
	public List<ControlConstraint> getControls() {
		return controls;
	}

	/**
	 * @return the cellIndex
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

	/**
	 * @return the rowIndex
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @param cellIndex the cellIndex to set
	 */
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	/**
	 * @param rowIndex the rowIndex to set
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * Horizontal span of the cell is the same as the one defined on the first control
	 * @return Horizontal span
	 */
	public int getHSpan() {
		if (getControls().size() == 0) {
			return 1;
		} else {
			return getControls().get(0).getHSpan();
		}
	}
	
	/**
	 * @return Vertical span
	 */
	public int getVSpan() {
		if (getControls().size() == 0) {
			return 1;
		} else {
			return getControls().get(0).getVSpan();
		}
	}

	/**
	 * @return Returns the flow direction
	 */
	public Flow getFlow() {
		return flow;
	}

	/**
	 * @param flow Flow direction
	 */
	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Row: %s / Cell: %s / Controls: %s", rowIndex, columnIndex, controls);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (rowIndex*1000)+columnIndex;
	}

	
}
