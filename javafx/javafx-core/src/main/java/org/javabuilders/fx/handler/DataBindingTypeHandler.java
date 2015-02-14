/**
 * 
 */
package org.javabuilders.fx.handler;

import javafx.beans.property.Property;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javabuilders.*;
import org.javabuilders.handler.binding.AbstractBuilderBindingsHandler;
import org.javabuilders.handler.binding.BindingSourceDefinition;
import org.javabuilders.handler.binding.BuilderBindings;
import org.javabuilders.handler.binding.BidirectionalBuilderBindings;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * FX data binding handler
 *
 * @author Jacek Furmankiewicz
 */
@Slf4j
@RequiredArgsConstructor
public class DataBindingTypeHandler extends AbstractBuilderBindingsHandler {

    private MethodHandles.Lookup lookup = MethodHandles.lookup();

    // tells us if we are supposed to use bind() or unidirectional()
    private final boolean unidirectional;

    @Override
    public Class<?> getApplicableClass() {
        if (unidirectional) {
            return BuilderBindings.class;
        } else {
            return BidirectionalBuilderBindings.class;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders
     * .BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node,
     * java.lang.String, java.util.Map, java.lang.Object)
     */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> typeDefinition, Object instance) throws BuildException {

		Node node = new Node(parent, key, typeDefinition);
		node.setMainObject(instance);

		Map<NamedObjectProperty, BindingSourceDefinition> defs = getBindingDefinitions(node, process);
		for (NamedObjectProperty targetProperty : defs.keySet()) {
			BindingSourceDefinition sourceDef = defs.get(targetProperty);

			Object source = sourceDef.getSource();
			String sourceProp = sourceDef.getBindingExpression();
			Object target = process.getByName(targetProperty.getName());
			String targetProp = targetProperty.getPropertyExpression();

            Method sourcePropertyMethod = getPropertyMethod(source,sourceProp).orElseThrow(
                    () -> new BuildException("Unable to find property {0}.{1}",source.getClass().getSimpleName(),sourceProp));
            Method targetPropertyMethod = getPropertyMethod(target,targetProp).orElseThrow(
                    () -> new BuildException("Unable to find property {0}.{1}",target.getClass().getSimpleName(),targetProp));

            // bind() or bindDirectional() depending on the context
            try {
                Property srcPropertyInstance = (Property) sourcePropertyMethod.invoke(source);
                Property targetPropertyInstance = (Property) targetPropertyMethod.invoke(target);

                if (unidirectional) {
                    targetPropertyInstance.bind(srcPropertyInstance);
                } else {
                    targetPropertyInstance.bindBidirectional(srcPropertyInstance);
                }

            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new BuildException(e, "Unable to invoke bind() / bindBidirectional() methods {0}.{1}",
                        source.getClass().getSimpleName(),sourceProp);
            }
		}

		return node;
	}

    private Optional<Method> getPropertyMethod(Object source, String propertyName) {
        Method propertyMethod = null;

        String propertyMethodName = String.format("%sProperty", propertyName);

        Class<?> srcClazz = source.getClass();

        // search through entire class hierarchy
        while (srcClazz != null) {
            for (Method method : srcClazz.getDeclaredMethods()) {
                if (method.getName().equals(propertyMethodName)) {
                    return Optional.of(method);
                }
            }
            // move on up the hierarchy
            srcClazz = srcClazz.getSuperclass();
         }

        return Optional.empty();
    }


}
