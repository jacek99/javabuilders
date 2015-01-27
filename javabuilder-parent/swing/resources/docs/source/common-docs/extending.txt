Extending the JavaBuilders engine
=================================

Overview
--------

The core JavaBuilders engine is domain-agnostic, i.e. there is no logic in it specific to any particular
toolkit such as Swing or SWT. Each of domain-specific builders (such as the Swing JavaBuilder or the
SWT JavaBuilder) are just thin proxies for the common Builder APIs which pass along a
pre-configured instance of a BuilderConfig object, which contains all the component types and
custom handlers for each UI toolkit.

This builder configuration object is usually exposed via the static getConfig() method on the builder, e.g. 
``SwingJavaBuilder.getConfig()``.

By manipulating its properties you can change the default configuration, register new object types,
customized handlers for particular controls or particular properties of a control.

Registering new component types
-------------------------------

All you need to do is call the ``BuilderConfig.addType(Class clazz)`` method, presumably from 
your application's `main()`::

    SwingJavaBuilder.getConfig().addType(MyCustomComponent.class);
    
and then you can start referring to it directly in the YAML file::

    MyCustomClass(property1=value1,property2=value2, etc...)
    
You can also add it with a specific alias to avoid name collision (by default it takes the simple class name)::

    SwingJavaBuilder.getConfig().addType("CustomClassAlias",MyCustomClass.class);
    
    CustomClassAlias(property1=value1,property2=value2, etc...)
    
Customizing object creation : ITypeHandler
------------------------------------------

If you need to write your own custom creation code for a class instance (e.g. for a control that has a
constructor that expects parameters during initialization), you need to implement an instance of
``ITypeHandler``, usually by extending ``AbstractTypeHandler``. It needs to be then registered for the
class-specific ``TypeDefinition`` object within the ``BuilderConfig`` instance.

Customizing initialization logic: ITypeHandlerAfterCreationProcessor
--------------------------------------------------------------------

If your object does not need special constructor logic, but just some post-creation initialization, then you
need to implement the simple ``ITypeHandlerAfterCreationProcessor`` interface and register it with your type's
``TypeDefinition``. It's logic will be executed after the object is created, but before any children get
processed.

Customizing post-processing of children nodes; ITypeHandlerFinishProcessor
--------------------------------------------------------------------------

If you need to inject some logic after a parent's child nodes have been all processed, you need to
implement the ``ITypeHandlerFinishProcessor`` interface and add it to the appropriate ``TypeDefinition`` object.
