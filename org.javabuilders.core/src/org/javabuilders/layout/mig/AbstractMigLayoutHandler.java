package org.javabuilders.layout.mig;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.TypeDefinition;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.handler.ITypeChildrenHandler;
import org.javabuilders.layout.ControlConstraint;
import org.javabuilders.layout.DefaultResize;
import org.javabuilders.layout.Flow;
import org.javabuilders.layout.HAlign;
import org.javabuilders.layout.LayoutCell;
import org.javabuilders.layout.LayoutConstraints;
import org.javabuilders.layout.VAlign;

/**
 * Abstract MigLayout handler that descendants can customize for Swing or SWT
 * @author Jacek Furmankiewicz
 */
public abstract class AbstractMigLayoutHandler  extends AbstractTypeHandler implements ITypeChildrenHandler {

	private final static Map<DefaultResize,String> resizeConstraints = new HashMap<DefaultResize, String>();
	protected final static Logger logger = Logger.getLogger("MigLayoutHandler");
	/**
	 * Static constructor
	 */
	static {
		resizeConstraints.put(DefaultResize.BOTH, "grow");
		resizeConstraints.put(DefaultResize.X_AXIS, "growx");
		resizeConstraints.put(DefaultResize.Y_AXIS, "growy");	
	}
	
	/**
	 * Constructor
	 */
	protected AbstractMigLayoutHandler() {
		super();
	}
	
	protected abstract void setLayout(BuildProcess result, Node node, Object migLayout) throws BuildException;
	protected abstract Object getComponent(BuildProcess result, Node components, String name) throws BuildException;
	protected abstract void setLayoutConstraints(Object layout, String constraints) throws BuildException;
	protected abstract void setRowConstraints(Object layout, String constraints) throws BuildException;
	protected abstract void setColumnConstraints(Object layout, String constraints) throws BuildException;
	protected abstract void applyControlConstraints(BuildProcess result, Node node, Node components, Map<String,String> layoutConstraints) throws BuildException;

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public final Node useExistingInstance(BuilderConfig config, BuildProcess result,
			Node parent, String key, Map<String, Object> typeDefinition,
			Object instance) throws BuildException {
		
		Node node = new Node(parent,key,typeDefinition);
		node.setMainObject(instance);
		
		//Node components = parent.getChildNode(Builder.CONTENT);
		Node components = parent;

		//set the actual layout manager on the container - overriden in descendants
		setLayout(result, node, node.getMainObject());

		String layoutCo = "", rowCo = "", columnCo = "";
		
		//process "layout" value if defined
		Map<String, String> layoutConstraints = new LinkedHashMap<String, String>();
		if (typeDefinition.containsKey(Builder.LAYOUT)) {
			
			String visualLayout = String.valueOf(typeDefinition.get(Builder.LAYOUT));
			LayoutConstraints lo = LayoutConstraints.getParsedLayoutConstraints(visualLayout, MigLayoutCommon.DEFAULT_ROW_COLUMN_CONSTRAINT, MigLayoutCommon.DEFAULT_ROW_COLUMN_CONSTRAINT);
			
			//get general layout constraints
			layoutCo = lo.getLayoutConstraints();
			for(String row : lo.getRowConstraints()) {
				rowCo += " " + row;
			}
			for(String column : lo.getColumnConstraints()) {
				columnCo += " " + column;
			}
			
			//each cell's/control's constraint			
			StringBuilder builder = new StringBuilder();
			for(LayoutCell cell : lo.getCells()) {
				boolean firstControl = true;
				
				for(ControlConstraint co : cell.getControls()) {
					
					//Object component = SwingBuilderUtils.getComponent(components,String.valueOf(co.getControlName()));
					Object component = getComponent(result, components, co.getControlName());
					if (component == null) {
						throw new BuildException("MigLayout unable to find control named \"{0}\" in layout:\n{1}", 
								co.getControlName(), visualLayout);
					}
					
					builder.setLength(0);
					builder.append("cell ");
					builder.append(cell.getColumnIndex());
					builder.append(" ");
					builder.append(cell.getRowIndex());
					
					//handle span/flow info - put it on first control only
					if (firstControl) {
						if (co.getHSpan() > 1 || co.getVSpan() > 1) {
							builder.append(" ").append(co.getHSpan())
							 .append(" ").append(co.getVSpan());
						}
						
						if (cell.getFlow() == Flow.VERTICAL) {
							builder.append(", flowy, top");
						}
						
						firstControl = false;
					}
					
					//RESIZE logic
					String resize = resizeConstraints.get(TypeDefinition.getDefaultResize(config, component.getClass()));
					if (resize != null) {
						
						//only add it there aren't some additional "grow" constraints that override it
						String additional = null;
						if (lo.getAdditionalControlConstraints().containsKey(co.getControlName())) {
							additional = lo.getAdditionalControlConstraints().get(co.getControlName());
						}
						
						if (additional == null || additional.indexOf("grow") < 0) {
							//no resize related additional constraints
							builder.append(", ").append(resize);
						}
					} 
					
					//handle control alignment
					if (co.getHAlign() == HAlign.CENTER) {
						builder.append(", alignx center");
					} else if (co.getHAlign() == HAlign.RIGHT) {
						builder.append(", alignx right");
					} else if (co.getHAlign() == HAlign.LEFT) {
						builder.append(", alignx left");
					}
					
					if (co.getVAlign() == VAlign.MIDDLE) {
						builder.append(", aligny center");
					} else if (co.getVAlign() == VAlign.BOTTOM) {
						builder.append(", aligny bottom");
					} else if (co.getVAlign() == VAlign.TOP) {
						builder.append(", aligny top");
					}
					
					//size group logic
					if (co.getSizeGroup() != null) {
						if (co.isSizeGroupX()) {
							//horizontal size group
							builder.append(", sgx ").append(co.getSizeGroup());
						} else if (co.isSizeGroupY()) {
							//vertical size group
							builder.append(", sgy ").append(co.getSizeGroup());
						} else {
							//regular size group
							builder.append(", sg ").append(co.getSizeGroup());
						}
					}
					
					//additional constraints
					if (lo.getAdditionalControlConstraints().containsKey(co.getControlName())) {
						String constraints = lo.getAdditionalControlConstraints().get(co.getControlName());
						//could point to a global String
						if (constraints.matches(BuilderConfig.GLOBAL_VARIABLE_REGEX)) {
							constraints = (String) config.getGlobalVariable(constraints, String.class);
						}
						builder.append(", ").append(constraints);
					}
					
					layoutConstraints.put(co.getControlName(), builder.toString());					
				}
			}
		}
		
		//process the actual layout controls
		if (typeDefinition.containsKey(MigLayoutCommon.LAYOUT_CONSTRAINTS)) {
			layoutCo += " " + typeDefinition.get(MigLayoutCommon.LAYOUT_CONSTRAINTS);
		}
		if (typeDefinition.containsKey(MigLayoutCommon.ROW_CONSTRAINTS)) {
			rowCo += " " + typeDefinition.get(MigLayoutCommon.ROW_CONSTRAINTS);
		}
		if (typeDefinition.containsKey(MigLayoutCommon.COLUMN_CONSTRAINTS)) {
			columnCo += " " + typeDefinition.get(MigLayoutCommon.COLUMN_CONSTRAINTS);
		}
		
		if (layoutCo.length() > 0) {
			setLayoutConstraints(instance, layoutCo);
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("MigLayout constraints: " + layoutCo);
			}
		}
		if (rowCo.length() > 0) {
			setRowConstraints(instance,rowCo);
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("MigLayout row constraints: " + rowCo);
			}
		}
		if (columnCo.length() > 0) {
			setColumnConstraints(instance, columnCo);
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("MigLayout column constraints: " + columnCo);
			}
		}
		
		//process each child control
		if (typeDefinition.containsKey(Builder.CONSTRAINTS)) {
			List<? extends Object> constraints = (List<? extends Object>)typeDefinition.get(Builder.CONSTRAINTS);
			for(Object constraint : constraints) {
				if (constraint instanceof String) {
					//constraint = control name (e.g. just "- buttonCancel"
					if (!layoutConstraints.containsKey(constraint)) {
						layoutConstraints.put(String.valueOf(constraint), "");
					}
					
				} else if (constraint instanceof Map){
					
					Map<String,Object> map = (Map<String,Object>)constraint;
					for(String componentName : map.keySet()) {
						Object componentConstraint = map.get(componentName);
						String existingConstraint = layoutConstraints.get(componentName);
						if (existingConstraint == null) {
							existingConstraint = String.valueOf(componentConstraint);
						} else {
							existingConstraint += ", " + String.valueOf(componentConstraint);
						}
						layoutConstraints.put(componentName, existingConstraint);
					}
				}
			}
		}
		
		//process
		applyControlConstraints(result, node, components, layoutConstraints);
		
		return node;
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.AbstractTypeHandler#getCollectionPropertyName()
	 */
	@Override
	public String getCollectionPropertyName() {
		//if the MigLayout node is a list, automatically "move" the list to the "constraints" node
		return Builder.CONSTRAINTS;
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.AbstractTypeHandler#getSimpleValuePropertyName()
	 */
	@Override
	public String getSimpleValuePropertyName() {
		//if the MigLayout node is a simple String property "move" the value to the "layout" node
		return Builder.LAYOUT;
	}
	
}

