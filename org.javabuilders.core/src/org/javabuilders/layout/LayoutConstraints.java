package org.javabuilders.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javabuilders.BuilderUtils;
import org.javabuilders.JBStringUtils;
import org.jvyaml.YAML;

/**
 * Class representing parsed layout constraints (from the "layout" node on the container)
 * @author Jacek Furmankiewicz
 */
public class LayoutConstraints {

	private final static Logger logger = Logger.getLogger(LayoutConstraints.class.getSimpleName());
	
	private String layoutConstraints ="";
	private List<String> rowConstraints= new ArrayList<String>();
	private List<String> columnConstraints= new ArrayList<String>();

	private Set<LayoutCell> cells = new LinkedHashSet<LayoutCell>();
	private Map<Integer,Set<String>> sizeGroups = new HashMap<Integer, Set<String>>();
	private Map<String,String> additionalControlConstraints = new HashMap<String, String>(); 
	
	
	/**
	 * Constructor
	 */
	public LayoutConstraints() {}
	
	/**
	 * Constructor
	 * @param layoutConstraints General layout constraints
	 */
	public LayoutConstraints(String layoutConstraints) {
		this.layoutConstraints = layoutConstraints;
	}
	
	/**
	 * @return the layoutConstraints
	 */
	public String getLayoutConstraints() {
		return layoutConstraints;
	}

	/**
	 * @param layoutConstraints the layoutConstraints to set
	 */
	public void setLayoutConstraints(String layoutConstraints) {
		this.layoutConstraints = layoutConstraints;
	}

	/**
	 * @return the rowConstraints
	 */
	public List<String> getRowConstraints() {
		return rowConstraints;
	}

	/**
	 * String representation
	 */
	@Override
	public String toString() {
		return String.format("General: %s\nRow: %s\nColumn: %s\nCells:\n%s",
				layoutConstraints,rowConstraints,columnConstraints,cells);
	}
	
	/**
	 * Hash code
	 */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	
	/**
	 * @return the columnConstraints
	 */
	public List<String> getColumnConstraints() {
		return columnConstraints;
	}

	/**
	 * @return Cells
	 */
	public Set<LayoutCell> getCells() {
		return cells;
	}
	
	/**
	 * Returns the cell at the specified row/column index, or null if not found
	 * @param rowIndex Row index
	 * @param columnIndex Column index
	 * @return Layout cell or null
	 */
	public LayoutCell getCellAt(int rowIndex, int columnIndex) {
		for(LayoutCell cell : getCells()) {
			if (cell.getRowIndex() == rowIndex && cell.getColumnIndex() == columnIndex) {
				return cell;
			}
		}
		return null;
	}
	
	/**
	 * @return The map of size groups (i.e. lists of controls that were flagged to be of the same size)
	 */
	public Map<Integer, Set<String>> getSizeGroups() {
		return sizeGroups;
	}
	
	/**
	 * Returns the number of rows 
	 * @return Row count
	 */
	public int getRowCount() {
		int maxRow = 0;
		for (LayoutCell cell : getCells()) {
			if (cell.getRowIndex() > maxRow) {
				maxRow = cell.getRowIndex();
			}
		}
		return maxRow + 1;
	}

	/**
	 * Returns the number of columns 
	 * @return Column count
	 */
	public int getColumnCount() {
		int maxColumn = 0;
		for (LayoutCell cell : getCells()) {
			if (cell.getColumnIndex() > maxColumn) {
				maxColumn = cell.getColumnIndex();
			}
		}
		return maxColumn + 1;
	}
	
	/**
	 * For any cells flagged to do max spanning across an entire row or column,
	 * updates the actual HSpan/VSpan to reflect the number of rows/columns involved
	 */
	private void updateSpanForMaxValues() {
		int rows = getRowCount();
		int columns = getColumnCount();
		
		for (LayoutCell cell : getCells()) {
			if (cell.getControls().size() > 0) {
				ControlConstraint co = cell.getControls().get(0);
				if (co.isMaxHSpan()) {
					co.setHSpan(columns - cell.getColumnIndex());
				}
				if (co.isMaxVSpan()) {
					co.setVSpan(rows - cell.getRowIndex());
				}
			}
		}
	}
	
	/*
	 * STATIC UTILITY METHODS
	 */
	
	/**
	 * General parser that creates the layout constraint definitions
	 */
	public static LayoutConstraints getParsedLayoutConstraints(String layout, String defaultRowContraint, 
			String defaultColumnConstraint) throws LayoutException {
		LayoutConstraints constraints = new LayoutConstraints();
		
		String columnConstraintLine = "";
		
		//does the container have a layout node?
		String[] lines = layout.split("\n");
		
		if (lines.length > 0) {
			int firstControlLine = -1;
		
			for(int row = 0; row < lines.length; row++) {
				String line = lines[row];
				String trimLine = line.trim();
				
				//figure out what type of line are we dealing with here
				if (trimLine.length() > 0) {
					if ('[' == trimLine.charAt(0)) {
						if (trimLine.length() > 1) {
							if (trimLine.startsWith("[[") && trimLine.endsWith("]]")) {
								//general layout constraints [[ ]]
								constraints.setLayoutConstraints(trimLine.substring(2,trimLine.length() - 2));
							} else if (trimLine.startsWith("[") && trimLine.endsWith("]")) {
								//accumulate all the column constraint lines
								columnConstraintLine = line;
							} else {
								throw new LayoutException("Incorrectly formatted constraint line: " + trimLine);
							}
						} else {
							throw new LayoutException("Incorrectly formatted constraint line: " + trimLine);
						}
					} else if ('{' == trimLine.charAt(0)) {
						//additional control constraint line
						handleAdditionalControlConstraintLine(constraints, trimLine);
					} else {
						if (firstControlLine == -1) {
							firstControlLine = row;
						}
						handleControlLine(constraints, row - firstControlLine, line, defaultRowContraint);
					}
				}
			}
		}	
		
		//now that we have processed all the lines, properly run through the column constraints
		if (columnConstraintLine.length() > 0) {
			handleColumnConstraintsLine(constraints, columnConstraintLine, defaultColumnConstraint);
		}
		
		//rearrange all the cell numbers to be properly ordered
		if (constraints.getCells().size() > 0) {
			Set<Integer> columns = new TreeSet<Integer>();
			for(LayoutCell cell : constraints.getCells()) {
				columns.add(cell.getColumnIndex());
			}
			
			int column = 0;
			for(Iterator<Integer> it = columns.iterator(); it.hasNext(); ) {
				Integer originalColumn = it.next();
				
				//find all cells with this original column and update it to the real one
				for(LayoutCell cell : constraints.getCells()) {
					if (cell.getColumnIndex() == originalColumn.intValue()) {
						cell.setColumnIndex(column);
					}
				}
				column++;
			}
		}
		
		constraints.updateSpanForMaxValues();
		
		//handle the special scenario where we are dealing with vertical cell spanning
		//the controls in the cells that are spanned need to be all added into the "first" original cell
		for(LayoutCell cell : constraints.getCells()) {
			if (cell.getHSpan() > 1 || cell.getVSpan() > 1) {
				for (int rowSpan = 0; rowSpan < cell.getVSpan(); rowSpan++) {
					for(int cellSpan = 0; cellSpan < cell.getHSpan(); cellSpan++) {
						if (!(rowSpan == 0 && cellSpan == 0)) {
							LayoutCell lowerCell = constraints.getCellAt(cell.getRowIndex() + rowSpan, cell.getColumnIndex() + cellSpan);
							if (lowerCell != null) {
								//move all the controls from this cell to the proper one above it
								for(ControlConstraint cc : lowerCell.getControls()) {
									cell.getControls().add(cc);
									if (logger.isLoggable(Level.FINE)) {
										logger.fine("Moved control " + cc.getControlName() + " from cell " +
												lowerCell.getColumnIndex() + " " + lowerCell.getRowIndex() +
												" to cell " + cell.getColumnIndex() + " " + cell.getRowIndex());
									}
								}
								//lower cell needs to be removed altogether from the layout constraints, now that it has been cannibalized
								lowerCell.getControls().clear();							
							}
						}
					}
				}
				//change the flow to vertical if it spans 1 column across multiple rows
				if (cell.getHSpan() == 1 && cell.getVSpan() > 1) {
					cell.setFlow(Flow.VERTICAL);
				}
			}
		}
		
		constraints.updateSpanForMaxValues(); //run it once again once all the controls have been moved around
		
		//remove cells that have no controls in them
		List<LayoutCell> tobeRemoved = new ArrayList<LayoutCell>();
		for(LayoutCell cell : constraints.getCells()) {
			if (cell.getControls().size() == 0) {
				tobeRemoved.add(cell);
			}
		}		
		//need to be removed separately in order to avoid ConcurrentModificationException (Issue 23)
		for(LayoutCell cell : tobeRemoved) {
			constraints.getCells().remove(cell);
		}
		
		return constraints;
	}
	
	//handles a layout lines with controls on it
	private static void handleControlLine(LayoutConstraints constraints, int row, String line,
			String defaultRowConstraint) throws LayoutException {
		
		int nextSpace = -1;
		String controlData = null;
		char character = ' ';
		List<String> controls = null;
		
		line = line + " "; //always need one space at end to make parsing simpler
		boolean rowConstraintFound = false;
		
		for(int column = 0; column < line.length(); column++) {
			character = line.charAt(column);
			if (character == '[') {
				//beginning of row constraints...skip rest of line
				constraints.getRowConstraints().add(line.substring(column).trim());
				rowConstraintFound = true;
				break;
			} else if (character !=' ' || character == '"') {
				
				//not a space - we're in a control constraint
				nextSpace = getNextSpacePosition(line, column);
				controlData = line.substring(column,nextSpace);

				//multiple controls may be in the same cell, comma separated
				//but commas may be allowed if embedded in quotes (string literal controls)...hell to parse

				LayoutCell cell = new LayoutCell(row,column);

				controls = JBStringUtils.split(controlData, ',');
				for(String control : controls) {
					ControlConstraint constraint = new ControlConstraint(control);
					cell.getControls().add(constraint);
				}
				
				constraints.getCells().add(cell);
				column = nextSpace;
			} 
		}
        if (!rowConstraintFound) {
        	constraints.getRowConstraints().add(defaultRowConstraint);
        }
	}
	
	//figures out where the next real space is 
	private static int getNextSpacePosition(String line, int column) {
		int pos = -1;
		
		boolean inSpaceLiteral = false;
		for(int i = column; i < line.length();i++) {
			if (line.charAt(i) == '"') {
				inSpaceLiteral = !inSpaceLiteral;
			} else if (line.charAt(i) == ' ' && !inSpaceLiteral) {
				pos = i;
				break;
			}
		}
		return pos;
	}
	
	private static void handleColumnConstraintsLine(LayoutConstraints co, String line, String defaultColumnConstraint) {
		Map<Integer,String> columnConstraints = new TreeMap<Integer, String>();
		//find all unique column indexes
		for(LayoutCell cell : co.getCells()) {
			//if (!columnConstraints.containsKey(cell.getColumnIndex()) && line.length() > cell.getColumnIndex()) {
			if (line.length() > cell.getColumnIndex()) {			
				char charForCol = line.charAt(cell.getColumnIndex());
				if (charForCol == '[') {
					//OK, we have a column constraint defined
					int nextColCo = line.indexOf("[", cell.getColumnIndex() + 1);
					if (nextColCo == -1) {
						columnConstraints.put(cell.getColumnIndex(), line.substring(cell.getColumnIndex()).trim());
					} else {
						columnConstraints.put(cell.getColumnIndex(), line.substring(cell.getColumnIndex(),nextColCo));
					}
				} else {
					//no column constraint defined, put in the default one
					columnConstraints.put(cell.getColumnIndex(), defaultColumnConstraint);
				}
			}
		}
		//apply all
		for(String colCo : columnConstraints.values()) {
			co.getColumnConstraints().add(colCo);
		}
	}
	
	//handles the optional lines at the end in { name : constraint, name2: constraint2 } format
	@SuppressWarnings("unchecked")
	private static void handleAdditionalControlConstraintLine(LayoutConstraints co, String line) {
		Map<String,String> constraints = (Map<String,String>)YAML.load(line);
		for(String name : constraints.keySet()) {
			List<Object> list = BuilderUtils.convertToList(constraints.get(name));
			String value = BuilderUtils.convertListToString(list, ',', line.length());
			co.getAdditionalControlConstraints().put(name, value);
		}
	}

	/**
	 * @return Additional, layout manager-specific control constraints
	 */
	public Map<String, String> getAdditionalControlConstraints() {
		return additionalControlConstraints;
	}
	
}
