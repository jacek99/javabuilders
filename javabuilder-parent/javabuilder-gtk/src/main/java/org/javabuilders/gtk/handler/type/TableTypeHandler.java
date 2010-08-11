package org.javabuilders.gtk.handler.type;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gnome.gtk.Alignment;
import org.gnome.gtk.AttachOptions;
import org.gnome.gtk.Container;
import org.gnome.gtk.HBox;
import org.gnome.gtk.SizeGroup;
import org.gnome.gtk.SizeGroupMode;
import org.gnome.gtk.Table;
import org.gnome.gtk.Widget;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.TypeDefinition;
import org.javabuilders.gtk.GtkConstants;
import org.javabuilders.gtk.layout.RowColumnConstraint;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.javabuilders.layout.ControlConstraint;
import org.javabuilders.layout.DefaultResize;
import org.javabuilders.layout.Flow;
import org.javabuilders.layout.HAlign;
import org.javabuilders.layout.LayoutCell;
import org.javabuilders.layout.LayoutConstraints;
import org.javabuilders.layout.VAlign;
import org.javabuilders.layout.mig.MigLayoutCommon;

/**
 * Table type handler
 * @author Jacek Furmankiewicz
 *
 */
public class TableTypeHandler extends AbstractTypeHandler implements ITypeHandlerFinishProcessor {

	private static final String ATTACH = "attach";
	
	/**
	 * Constructor
	 */
	public TableTypeHandler() {
		super(ATTACH);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		LayoutConstraints c = getLayoutConstraints(typeDefinition);
		
		boolean homogenous = false;
		if (typeDefinition.containsKey(GtkConstants.HOMOGENOUS)) {
			homogenous = (Boolean) typeDefinition.get(GtkConstants.HOMOGENOUS);
		}
		
		Table instance = new Table(c.getRowCount(), c.getColumnCount(), homogenous);
		return useExistingInstance(config, process, parent, key, typeDefinition, instance);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition,
			Object instance) throws BuildException {
		
		Node node = new Node(parent,key,typeDefinition, instance);
		return node;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		LayoutConstraints c = getLayoutConstraints(typeDefinition);
		
		Set<Node> widgets = current.getContentNodes(Widget.class);
		Table table = (Table) current.getMainObject();
		Map<String,Widget> widgetNames = new HashMap<String, Widget>();
		
		for(Node node : widgets) {
			Widget w = (Widget) node.getMainObject();
			String name = node.getStringProperty(Builder.NAME);
			if (name != null) {
				widgetNames.put(name, w);
			}
		}
		
		//parse the constraints
		int rows = c.getRowCount();
		int columns = c.getColumnCount();
		
		List<RowColumnConstraint> rowConstraints = new LinkedList<RowColumnConstraint>();
		if (c.getRowConstraints().size() > 0) {
			for(int i = 0; i < c.getRowConstraints().size();i++) {
				RowColumnConstraint con = new RowColumnConstraint(i, c.getRowConstraints().get(i));
				rowConstraints.add(con);
				if (con.getSpacerWidth() > 0) {
					table.setRowSpacing(i, con.getSpacerWidth());	
				}
			}
		}
		
		List<RowColumnConstraint> columnConstraints = new LinkedList<RowColumnConstraint>();
		if (c.getColumnConstraints().size() > 0) {
			for(int i = 0; i < c.getColumnConstraints().size();i++) {
				RowColumnConstraint con = new RowColumnConstraint(i, c.getColumnConstraints().get(i)); 
				columnConstraints.add(con);
				if (con.getSpacerWidth() > 0) {
					table.setColumnSpacing(i, con.getSpacerWidth());	
				}
			}
		}
		
		Map<Integer,SizeGroup> sizeGroups = new HashMap<Integer, SizeGroup>();
		
		for(int col = 0; col < columns; col++) {
			for(int row = 0; row < rows; row++)  {
				LayoutCell cell = c.getCellAt(row,col);
				
				if (cell != null && cell.getControls() != null && cell.getControls().size() > 0) {
					//alignment on first control drive all of them
					ControlConstraint first = cell.getControls().get(0);
					Widget w = widgetNames.get(first.getControlName());
					
					RowColumnConstraint columnConstraint = null;
					AttachOptions colAttachOption = AttachOptions.FILL;
					if (columnConstraints.size() > cell.getColumnIndex()) {
						columnConstraint = columnConstraints.get(cell.getColumnIndex());
						if (columnConstraint.isGrowing()) {
							colAttachOption = AttachOptions.EXPAND;
						}
					} 
					RowColumnConstraint rowConstraint = null;
					AttachOptions rowAttachOption = AttachOptions.FILL;
					if (rowConstraints.size() > cell.getRowIndex()) {
						rowConstraint = rowConstraints.get(cell.getRowIndex());
						if (rowConstraint.isGrowing()) {
							rowAttachOption = AttachOptions.EXPAND;
						}
					}
					
					//handle multiple widgets defined
					Widget widgetToAdd = w;
					if (cell.getControls().size() > 1) {
						Container box = null;
						if (cell.getFlow() == Flow.HORIZONTAL) {
							box = new HBox(false,4); //TODO: change 4
							for(ControlConstraint cc : cell.getControls()) {
								box.add(widgetNames.get(cc.getControlName()));
							}
						} else {
							box = new HBox(false,4); //TODO: change 4
							for(ControlConstraint cc : cell.getControls()) {
								box.add(widgetNames.get(cc.getControlName()));
							}
						}
						widgetToAdd = box;
					}
					
					//alignment
					float xAlign = 0f;
					if (columnConstraint != null) {
						if (columnConstraint.getHAlign() == HAlign.RIGHT) {
							xAlign = 1.0f;
						} else if (columnConstraint.getHAlign() == HAlign.CENTER) {
							xAlign = 0.5f;
						}
					}
					if (first.getHAlign() == HAlign.RIGHT) {
						xAlign = 1.0f;
					} else if (first.getHAlign() == HAlign.CENTER) {
						xAlign = 0.5f;
					}
					
					float yAlign = 0f;
					if (rowConstraint != null) {
						if (rowConstraint.getVAlign() == VAlign.BOTTOM) {
							yAlign = 1.0f;
						} else if (rowConstraint.getVAlign() == VAlign.MIDDLE || rowConstraint.getVAlign() == VAlign.DEFAULT) {
							yAlign = 0.5f;
						}
					}
					if (first.getVAlign() == VAlign.BOTTOM) {
						yAlign = 1.0f;
					} else if (first.getVAlign() == VAlign.MIDDLE || first.getVAlign() == VAlign.DEFAULT) {
						yAlign = 0.5f;
					}
					
					float xScale = 0f, yScale = 0f;
					TypeDefinition widgetTypeDef = process.getConfig().getTypeDefinition(widgetToAdd.getClass());
					if (widgetTypeDef != null) {
						if (widgetTypeDef.getDefaultResize() == DefaultResize.X_AXIS || widgetTypeDef.getDefaultResize() == DefaultResize.BOTH) {
							xScale = 1f;
						}
						if (widgetTypeDef.getDefaultResize() == DefaultResize.Y_AXIS || widgetTypeDef.getDefaultResize() == DefaultResize.BOTH) {
							yScale = 1f;
						}
					}
					
					Alignment alignment = new Alignment(xAlign,yAlign,xScale,yScale,widgetToAdd);
					if (columnConstraint != null && rowConstraint != null) {
						alignment.setSizeRequest(columnConstraint.getWidth(), rowConstraint.getWidth());
					}
					table.attach(alignment, col, col + first.getHSpan(), row, row + first.getVSpan(), colAttachOption, rowAttachOption,0,0);
					
					//size groups
					for(ControlConstraint cc : cell.getControls()) {
						Integer group = cc.getSizeGroup();
						if (group != null) {
							SizeGroup sg = sizeGroups.get(group);
							if (sg == null) {
								SizeGroupMode mode = SizeGroupMode.BOTH;
								if (cc.isSizeGroupX() || !cc.isSizeGroupY()) {
									mode = SizeGroupMode.HORIZONTAL;
								} else if (!cc.isSizeGroupX() || cc.isSizeGroupY()) {
									mode = SizeGroupMode.VERTICAL;
								}
								sg = new SizeGroup(mode);
								sizeGroups.put(group,sg);
							}
							sg.add(widgetNames.get(cc.getControlName()));
						}
					}
					
				}
			}
		}
		
		current.getCustomProperties().put(GtkConstants.INTERNAL_LAYOUT_HANDLED, true); //tell the Container handler to skip its logic
		
	}
	
	//gets the layout constraints
	@SuppressWarnings("unchecked")
	private LayoutConstraints getLayoutConstraints(Map<String, Object> typeDefinition) {
		LayoutConstraints c = null;
		
		Object content = typeDefinition.get(Builder.CONTENT);
		if (content instanceof List) {
			List<Object> contents = (List<Object>) content;
			for(Object value : contents) {
				if (value instanceof Map) {
					Map<String,Object> parts = (Map<String, Object>) value;
					if (parts.containsKey(ATTACH)) {
						String attach = (String) parts.get(ATTACH);
						c = LayoutConstraints.getParsedLayoutConstraints(attach, MigLayoutCommon.DEFAULT_ROW_COLUMN_CONSTRAINT,
								MigLayoutCommon.DEFAULT_ROW_COLUMN_CONSTRAINT);
					}
				}
			}
		}
		//String attach = current.getContentNode().getStringProperty(ATTACH);
		//c.setLayoutConstraints(attach);
		return c;
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<Table> getApplicableClass() {
		return Table.class;
	}


}
