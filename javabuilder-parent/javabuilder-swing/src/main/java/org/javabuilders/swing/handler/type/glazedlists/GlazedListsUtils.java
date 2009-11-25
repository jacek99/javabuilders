package org.javabuilders.swing.handler.type.glazedlists;

import static org.javabuilders.util.Preconditions.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.javabuilders.BuildException;
import org.javabuilders.util.BuilderUtils;
import org.javabuilders.util.PropertyUtils;
import org.javabuilders.util.Tuple2;
import org.javabuilders.util.compiler.ClassStringBuilder;
import org.javabuilders.util.compiler.CompilerUtils;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.TransformedList;
import ca.odell.glazedlists.UniqueList;

/**
 * Reusable class to take care of transforming the base collection into whatever
 * the widget requires
 * @author Jacek Furmankiewicz
 */
public class GlazedListsUtils {

	public static final String SOURCE = "source";
	
	/**
	 * Transform the sources into a UniqueList if required
	 * @return Tuple of Event list and its type
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	@SuppressWarnings("unchecked")
	public static Tuple2<EventList,Class<?>> getSource(Object caller, Map<String, Object> typeDefinition) throws BuildException {
		
		try {
			String source = (String) typeDefinition.get(SOURCE);
			if (source == null) {
				throw new BuildException("EventTableModel.source property must be specified: {0}", typeDefinition);
			}
			//is it pointing to the whole collection or just one unique column
			String[] parts = StringUtils.split(source, ".");
			String listName = null, columnName = null;
			switch (parts.length) {
			case 1:
				//the whole collection
				listName = parts[0];
				break;
			case 2:
				//just one column - turn it into a unique collection
				listName = parts[0];
				columnName = parts[1];
				break;
			default:
				throw new BuildException("EventTableModel.source is not in valid format: {0}", source);
			}
			
			Field field = BuilderUtils.getField(caller, listName, EventList.class);
			checkNotNull(field, "EventTableModel.source property does not point to a valid instance of GlazedLists EventList: {0}",typeDefinition);
			Class<?> type = BuilderUtils.getGenericsTypeFromCollectionField(field);
			checkNotNull(type,"Unable to use generics to find type of object stored in source: {0}", listName);
			
			EventList list = (EventList) field.get(caller);
			
			if (columnName != null) {
				//column name specified, wants to create unique list by that column
				String getterName = PropertyUtils.getGetterName(columnName);
				Class<?> fieldType = PropertyUtils.verifyGetter(type, getterName,Object.class);
				Comparator c = CompilerUtils.newComparator(fieldType);

				//create a transformed list to convert from the original type to the field type
				TransformedList transList = transformList(list,type,fieldType,getterName);
				list = new UniqueList(transList, c);
				type = fieldType;
			}
			
			return new Tuple2(list,type);
		} catch (Exception cause) {
			throw new BuildException(cause, "Unable to obtain GlazedLists source: {0}", typeDefinition);
		}
	}
	
	/**
	 * Creates a TransformedList that transforms a list of type A intoa list of type A.oneOfItsFields
	 * @param <S>
	 * @param <D>
	 * @param sourceList
	 * @param source
	 * @param destination
	 * @param getterName
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@SuppressWarnings("unchecked")
	public static <S,D> TransformedList<S,D> transformList(List<S> sourceList, Class<S> source, Class<D> destination,
			String getterName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		String name = CompilerUtils.generateClassName(ReadOnlyTransformedList.class);
		ClassStringBuilder bld = new ClassStringBuilder();
		
		//constructor
		bld._("public class %s extends %s<%s,%s> {", name, ReadOnlyTransformedList.class.getName(), source.getName(),destination.getName())
			.___("public %s(%s<%s> source) {",name, EventList.class.getName(), source.getName())
			._____("super(source);")
			.___("}");
		
		
		bld.___("public %s get(int index) {",destination.getName())
			._____("return source.get(index).%s();", getterName)
			.___("}");
		
		bld._("}");
		
		Class<?> clazz = CompilerUtils.compile(name, bld.toString());
		ReadOnlyTransformedList list = (ReadOnlyTransformedList) clazz.getConstructor(EventList.class).newInstance(sourceList);
		return list;
	}
	
}
