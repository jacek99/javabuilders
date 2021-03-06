.. highlight:: yaml

Swing Features
==============

Now that we've seen the core JavaBuilders's features, let's explore what the Swing JavaBuilder provides
on top of that for building actual Swing user interfaces.

Overview
--------

The Swing JavaBuilder is an instance of the JavaBuilders engine, pre-configured for use with the Swing
UI toolkit. It is represented by the main class ``org.javabuilders.swing.SwingJavaBuilder`` and in
most typical cases that is the only class you will be dealing with.:

.. code-block:: java

        public class MyFrame extends JFrame {
             private BuildResult result = SwingJavaBuilder.build(this);
             public MyFrame() {}
        }
    
The returned ``BuildResult`` obtain contains a reference to the various objects that were created during
the build process, but it is often not necessary to interact with it at all (unless you are doing something
more complex or custom).

Component properties
----------------------------------------

In most cases there is a simple one-to-one mapping between the properties of Swing components and
how they are set in the YAML file, e.g. a ``JTextField.text`` property in YAML is simply::

    JTextField(text=Some Text)

However, some components have been enhanced in the Swing JavaBuilder to make instantiating and
using them even easier.

Actions and menus
-----------------

Creating actions and menus for any application is one of the most cumbersome and time consuming
tasks in Swing development. Fortunately enough, the Swing JavaBuilder delivers a whole slew of
productivity enhancements in this area that makes creating menus a breeze.

Text, accelerators and mnemonics
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
Whether you are dealing with an Action or a JMenuItem , you can handle defining all these 3
properties in one simple text value, where the mnemonic is indicated via a "&" prefix and the accelerator
is typed in manually after a "\t" tab indicator (similar to the way it is done in SWT), e.g.::

    JMenuItem(text="&Save\tCtrl+S")
    
The sample above sets the text to "Save", the mnemonic on the "S" character and the accelerator to "Ctrl+S".

Valid accelerators are:

1. ``Ctrl``
2. ``Alt``
3. ``Shift``
4. ``Meta``

followed by the appropriate character. They can be mixed together, e.g. `"Ctrl+Alt+N"`. Due to the
embedded ``\t``, such menu definitions have to be escaped into quoted text, as per the example above.

JButton accelerators
^^^^^^^^^^^^^^^^^^^^

By default Swing does not support defining accelerators on JButton(s). However, we worked around it
to make that possible. You can use the same format as for Action(s) or JMenuItem(s). This will only
be active if the window (or tab) that contains the button has current focus.

The accelerator value will be shown in the JButton tooltip text (unless it's already used for something else).

Actions
^^^^^^^

The regular Swing Action API has been modified separately to separate the concept of "name" vs "text"
(which are the same in the Action API, but we treat them separately so that the text can be easily
internationalized, without affecting the name). It provides name, text, toolTipText, icon
properties and the name of the Java method to be invoked is defined in the onAction handler.
 
*YAML*::

        Action(name=newAction, text=menu.file.new, icon=images/document-new.png, onAction=onFileNew)
        
*Java*:
    
.. code-block:: java    
    
       private void onFileNew() {
           System.out.print("onFileNew was invoked!");
       }
       
Any descendant of AbstractButton (such as JMenuItem or JButton can then refer to it in its
action property, e.g.::

     JFrame(title=frame.title, state=max, defaultCloseOperation=exitOnClose):
          - Action(name=newAction, text=menu.file.new, toolTipText=menu.file.new.tooltip, icon=images/document-new.png, onAction=onFileNew)
          - Action(name=openAction, text=menu.file.open, toolTipText=menu.file.open.tooltip, icon=images/document-open.png, onAction=onFileOpen)
          - Action(name=saveAction, text=menu.file.save, toolTipText=menu.file.save.tooltip, icon=images/document-save.png, onAction=onSave)
          - Action(name=exitAction, text=menu.file.exit, icon=images/process-stop.png,  onAction=[$confirm,exit])
          - Action(name=option1Action, text=menu.option1, onAction=option1)
          - Action(name=helpAboutAction,text=menu.help.about,onAction=onHelpAbout)
          - JMenuBar:
               - JMenu(name=fileMenu,text=menu.file):
                    - JMenuItem(action=newAction)
                    - JMenuItem(action=openAction)
                    - JSeparator()
                    - JMenuItem(action=saveAction)
                    - JSeparator()
                    - JMenuItem(action=exitAction)
               - JMenu(name=optionsMenu, text=menu.options):
                    - JRadioButtonMenuItem(name=radio1Menu, action=option1Action)
                    - JRadioButtonMenuItem(name=radio2Menu, text=menu.option2)
                    - JRadioButtonMenuItem(name=radio3Menu, text=menu.option3)
                    - ButtonGroup: [radio1Menu, radio2Menu, radio3Menu]
                    - JSeparator()
                    - JCheckBoxMenuItem(text=menu.option1, onAction=option1)
                    - JCheckBoxMenuItem(text=menu.option2)
                    - JCheckBoxMenuItem(text=menu.option3)
               - JMenu(name=helpMenu,text=menu.help):
                    - JMenuItem(action=helpAboutAction)
 
JMenuBar and JMenuItem
^^^^^^^^^^^^^^^^^^^^^^
 
If you do not wish to use Actions, you can create menus by directly specifying the relevant properties on
JMenuBar and JMenuItem instances::
 
     JFrame(title=frame.title, iconImage=images/system-lock-screen.png):
          - JMenuBar:
               - JMenu(name=fileMenu,text=menu.file):
                    - JMenuItem(name=newMenu, text=menu.file.new, onAction=onFileNew)
                    - JMenuItem(name=openMenu, text=menu.file.open, onAction=onFileOpen)
                    - JSeparator()
                    - JMenuItem(name=exitMenu, text=menu.file.exit, onAction=exit)
               - JMenu(name=optionsMenu, text=menu.options):
                    - JRadioButtonMenuItem(name=radio1Menu, text=menu.option1, onAction=option1)
                    - JRadioButtonMenuItem(name=radio2Menu, text=menu.option2)
                    - JRadioButtonMenuItem(name=radio3Menu, text=menu.option3)
                    - ButtonGroup: [radio1Menu, radio2Menu, radio3Menu]
                    - JSeparator()
                    - JCheckBoxMenuItem(text=menu.option1, onAction=option1)
                    - JCheckBoxMenuItem(text=menu.option2)
                    - JCheckBoxMenuItem(text=menu.option3)
               - JMenu(name=helpMenu,text=menu.help):
                    - JMenuItem(name=helpAboutMenu,text=menu.help.about,onAction=onHelpAbout)
     
However, we recommend you always use Actions instead.
 
JPopupMenu
^^^^^^^^^^
 
Popup menus can easily be added to any Swing component by simply specifying the `"popupMenu"`
property to point to an existing JPopupMenu instance by name. The Swing JavaBuilder takes care of all
the mouse event wiring to popup the menu upon right-click.

With actions::

     - Action(name=copyAction, text=menu.edit.copy, onAction=copy)
     - Action(name=pasteAction, text=menu.edit.paste, onAction=paste)
     - JPopupMenu(name=popup):
           - JMenuItem(action=copyAction)
           - JMenuItem(action=pasteAction)
     - JTabbedPane(name=tabs, onChange=onTabChanged):
            - JPanel(name=frameYamlSource, tabTitle=tab.frameYamlSource):
                - JScrollPane(name=scroll1):
                         JTextArea(name=frameSourceArea, popupMenu=popup)

Without actions::

     - JPopupMenu(name=popup):
           - JMenuItem(name=popupCopy, text=Copy, onAction=copy)
           - JMenuItem(name=popupPaste, text=Paste, onAction=paste)
     - JTabbedPane(name=tabs, onChange=onTabChanged):
            - JPanel(name=frameYamlSource, tabTitle=tab.frameYamlSource):
                - JScrollPane(name=scroll1):
                         JTextArea(name=frameSourceArea, popupMenu=popup)

Borders
-------

Regular Borders
^^^^^^^^^^^^^^^

Any Swing component that allows setting of borders can do it by using a set of pre-defined constants:

* ``loweredBevel``
* ``raisedBevel``
* ``loweredEtched``
* ``raisedEtched``

Example::

    JPanel(name=panel1, border=raisedBevel)

Color and Line borders
^^^^^^^^^^^^^^^^^^^^^^
Borders can also be specified using just a line width or a Color / line width combination::

     - JPanel(name=panel1, border=3)
     - JPanel(name=panel1, border=olive 3)
     - JPanel(name=panel1, border=ff00ee 3)

Titled Border
^^^^^^^^^^^^^

A titled border is a special case, since it has a text associated with it. In this case, there is a special
property that will automatically create a TitledBorder and put the proper text in it, namely ``groupTitle``::

     JPanel(name=groupBox1, groupTitle=Customer Data):
           - JLabel(name=nameLabel, text="Customer name:")
           - JText(name=nameField)
           
.. note::

    ``groupTitle`` is internationalizable, so you can pass a resource key to it, instead of a hard-coded String.

Button Group
------------

In order to create a ButtonGroup you just need to define it as a collection and pass it the names of
buttons that define a group. This works for both regular radio buttons as well as radio button menu items.

Radio buttons
^^^^^^^^^^^^^
::

 - JPanel(name=controls):
       - JRadioButton(name=rb1,text=Radio button 1)
       - JRadioButton(name=rb2,text=Radio button 2,selected=true)
       - ButtonGroup: [rb1,rb2]
       
Radio button menu items
^^^^^^^^^^^^^^^^^^^^^^^
::

 - JMenu(name=optionsMenu, text=menu.options):
      - JRadioButtonMenuItem(name=radio1Menu, text=menu.option1, onAction=option1)
      - JRadioButtonMenuItem(name=radio2Menu, text=menu.option2)
      - JRadioButtonMenuItem(name=radio3Menu, text=menu.option3)
      - ButtonGroup: [radio1Menu, radio2Menu, radio3Menu]

Colors
------

Colors can be specified using a standard HTML/CSS style syntax. Valid values are:
 
* Hex Color::

    JTextArea(name=textArea, background=ff00ee)
    
* Short hex color (e.g. f0e gets interpreted as ff00ee)::

    JTextArea(name=textArea, background=f0e)
    
* HTML color name (case-insensitive)::

    JTextArea(name=textArea, background=olive)
    
.. note::

    HTML color names: http://www.w3schools.com/html/html_colornames.asp
 
Dimensions
----------

Dimension (``java.awt.Dimension``) values can be specified using a ``width x height`` syntax, e.g::

    MyCustomPanel(size=800x400)
 
Fonts
-----
Fonts can be specified using a CSS-like syntax: ``bold|italic size name``::

     JButton(font=italic)
     JButton(font=italic bold)
     JButton(font=italic 14pt)
     JButton(font=Arial)
     JButton(font=italic bold 14pt Arial)

Icons and images
----------------

Any Swing API that expects an Icon or Image can be expressed by simply putting in the image path,
relative to the caller class that initiated the build process.::

    JMenuItem(text=menu.save, icon=images/document-save.png)
        
Alternatively, if you initialized the builder with a ResourceBundle to activate internationalization, you
can pass a resource key instead. The builder will look for the path to the image via the key in the bundle
instead, e.g.:

    *YAML*::
    
        JMenuItem(text=menu.save, icon=images.saveDocument)
        
    *Properties file*::
    
        images.saveDocument=/myapp/resources/images/document-save.png

JComboBox
---------
 
Databinding
^^^^^^^^^^^

In order to bind a List to a ``JComboBox``, you need to bind it to its ``model`` property, e.g.::

     bind:
          - jComboBox.model: this.books

An alternate (and arguably more powerful) databinding method involves using the GlazedLists library,
please refer to the relevant chapter for more details.

JDesktopPane
------------
 
JInternalFrame integration
^^^^^^^^^^^^^^^^^^^^^^^^^^

A ``JDesktopPane`` can be placed in a ``JFrame`` or a regular ``JPanel``, followed by one or more instances of a ``JInternalFrame``, e.g.::
 
     JPanel:
         - JDesktopPane(name=desktop,dragMode=outlineDragMode,visible=true):
              - JInternalFrame(name=frame1,title=Frame 1,visible=true,selected=true):
                    - JButton(name=button1,text=Button 1)
                    - JLabel(name=label1,text=Label 1)
                    - MigLayout: |
                        [grow] [pref]
                        label1 button1
              - JInternalFrame(name=frame2,title=Frame 2,visible=true):
                    - JButton(name=button2,text=Button 2)
                    - JLabel(name=label2,text=Label 2)
                    - MigLayout: |
                        [grow] [pref]
                        label2 button2
         - MigLayout: |
              [grow]
              desktop         [grow]
        
JFrame
------
JFrame support in the Swing JavaBuilder adds custom processing for the following properties:

* **size**

Can be in ``width_x_height`` format (e.g. ``800x400``) or ``packed`` to indicate the ``JFrame.pack()`` method
should be called at the end (after all the child components have been added), e.g.::
 
     JFrame(size=800x400)
     JFrame(size=packed)
     
* **state**

Allows setting the extended state of a frame, valid values are:
    * ``max``
    * ``maxh``
    * ``maxv``
    * ``icon``

::

    JFrame(state=max)
    
JList
-----

Databinding
^^^^^^^^^^^

In order to bind a List to a ``JList``, you need to bind it to its ``model`` property, e.g.::

     bind:
          - jList.model: this.books

An alternate (and arguably more powerful) databinding method involves using the GlazedLists library,
please refer to the relevant chapter for more details. 
    
JScrollPane
-----------

Used to wrap components in a scrollable pane. Since it only has one child underneath, it is entered not
as a YAML list, but a single item::

    JScrollPane(name=scrollPane1, verticalScrollBarPolicy=asNeeded,horizontalScrollBarPolicy=asNeeded):
        JTextArea(name=textArea)
        
You can also use the shorter ``vScrollBar`` and ``hScrollBar`` aliases::

    JScrollPane(name=scrollPane1, vScrollBar=asNeeded, hScrollBar=asNeeded):
        JTextArea(name=textArea
        
Valid values for both properties are:

* ``always``
* ``asNeeded``
* ``never``

JSplitPane
----------

In order to use a JSplitPane just list the child components underneath it. The first two will be
automatically added as the left/right (or top/bottom) panes,e.g.::

     JPanel:
          - JSplitPane(name=split1,orientation=verticalSplit):
              - JCustomPanel1(name=panel1)
              - JCustomPanel2(name=panel2)
              
The orientation property's ``verticalSplit`` or ``horizontalSplit`` values define the type of split.

JTabbedPane
-----------

In order to create tab pages, just list the controls you wants as tabs underneath the `JTabbedPane` node.
In order to specify the tab title, tooltip and icon use the following properties:

* ``tabTitle`` (localizable)
* ``tabToolTip`` (localizable)
* ``tabIcon``
* ``tabEnabled``

 Those are used only if a component is listed underneath a JTabbedPane and are ignored if used anywhere else.::
 
     - JTabbedPane(name=tabs):
          - JPanel(tabTitle=tab.frameYamlSource, tabIcon=images/tab1.png)

JTable
------

Custom Table Models
^^^^^^^^^^^^^^^^^^^

You can integrate custom table models into your JTables. First, you must register your custom model
(usually in the main(), so that the Swing JavaBuilder engine is aware of it, e.g.:

.. code-block:: java

    SwingJavaBuilder.getConfig().addType(MyCustomTableModel.class);
    
Then you can just refer to it directly::

     JPanel:
          - JScrollPane(name=scroll2):
               JTable(name=table1):
                   - MyCustomTableModel(name=model)
                   
.. note:: 

    Your custom table does not actually need to have name property. If it does not exist, the Swing
    JavaBuilder will handle it as a virtual property. A named instance of the model (that you can manipulate
    from the Java code) will be created, e.g.:
    
    .. code-block:: java    

        private MyCustomTableModel model;        //reference will be set during build process

Also, please read the GlazedLists chapter on information on some custom GlazedLists table models
that are integrated into the Swing JavaBuilder as an optional plugin.

Table Columns
^^^^^^^^^^^^^

``JTable`` provides an easy way to create table columns, by just specifying a list of ``TableColumn`` objects underneath it, e.g.::

     JPanel:
          - JScrollPane(name=scroll2):
               JTable(name=table1):
                   - TableColumn(name=col1,resizable=true, headerValue=Column 1)
                   - TableColumn(name=col2,resizable=true, headerValue=Column 2)
                   - TableColumn(name=col3,resizable=false, headerValue=Column 3)
                   
When processing the list of table columns, the builder will evaluate the columns that are there already. If
it can match based on the identifier or headerValue then it will use that existing columns, otherwise it will 
create a new one and add it to the ``JTable``.
 
Cell Editor
^^^^^^^^^^^

Adding cell editors to a column is very easy. You can either define an explicit ``TableCellEditor`` implementation::

     JTable(name=table1):
          - TableColumn(name=col1,resizable=true, headerValue=Column 1):
               - MyCustomCellEditor(name=col1Editor)
               
or you can define an explicit JCheckBox, JComboBox or JTextField underneath it. In this case the builder will 
automatically wrap it with a DefaultCellEdior wrapper::

       JTable(name=table1):
           - TableColumn(name=col1,resizable=true, headerValue=Column 1):
                - JComboBox(name=col1Box)
           - TableColumn(name=col2,resizable=true, headerValue=Column 2):
                - JCheckBox(name=col2Box)
           - TableColumn(name=col3,resizable=false, headerValue=Column 3):
                - JTextField(name=col3Field)
                
Cell Renderer
^^^^^^^^^^^^^
Similarly, you can define a TableCellRenderer underneath a column::

     JTable(name=table1):
          - TableColumn(name=col1,resizable=true, headerValue=Column 1):
              - MyCustomRenderer(name=col1Renderer)
              
If you want to define a column header renderer, just add a ``forHeader=true`` property::

     JTable(name=table1):
          - TableColumn(name=col1,resizable=true, headerValue=Column 1):
              - MyCustomRenderer(name=col1Renderer, forHeader=true)
          
                    
Event handlers
--------------

Here's a complete list of event handlers by component type. Next to them is also the event-specific class
that can get passed to your Java method if you require it (just remember to make it part of the method's
signature), e.g.:

.. code-block:: java
 
     private void someButtonClicked() {
              //...one valid signature...
     }
     private void someButtonClicked(ActionEvent evt) {
              //...another valid signature ...
     }
     private void someButtonClicked(JButton source) {
              //...another valid signature ...
     }
     private void someButtonClicked(JButton source, Action evt) {
              //...yet another valid signature ...
     }


* **Action**

=====================    ========================= 
Event Name               Event Class
=====================    =========================
onAction                 ActionEvent
=====================    =========================

* **(Abstract) Button**

=====================    =========================
onAction                 ActionEvent
=====================    =========================

                    
* **Component**       

=====================    =========================
onFocus                  FocusEvent

onFocusLost              FocusEvent
                    
onKeyPressed             KeyEvent
                    
onKeyReleased            KeyEvent
                    
onKeyTyped               KeyEvent
                    
onMouseClicked           MouseEvent
                    
onMouseDoubleClicked     MouseEvent
                    
onMouseDragged           MouseEvent
                    
onMouseEntered           MouseEvent
                    
onMouseExited            MouseEvent
                    
onMouseMoved             MouseEvent
                    
onMousePressed           MouseEvent
                    
onMouseReleased          MouseEvent
                    
onMouseRightClicked      MouseEvent
                    
onMouseWheelMoved        MouseWheelEvent
=====================    =========================

* **JComboBox**       

=====================    =========================
onAction                 ActionEvent
=====================    =========================
                    
* **JFrame**  

=====================    =========================
onStateChanged           WindowEvent

onWindowActivated        WindowEvent
                    
onWindowClosed           WindowEvent
                    
onWindowClosing          WindowEvent
                    
onWindowDeactivated      WindowEvent
                    
onWindowDeiconified      WindowEvent
                    
onWindowFocus            WindowEvent
                    
onWindowFocusLost        WindowEvent
                    
onWindowIconified        WindowEvent
                    
onWindowOpened           WindowEvent
=====================    =========================
                    
* **JTabbedPane**     

=====================    =========================
onChange                 ChangeEvent
=====================    =========================
                    
* **JTable**          

=====================    =========================
onSelection              ListSelectionEvent
=====================    =========================
                    
* **JTextField**

=====================    =========================
onAction                 ActionEvent
=====================    =========================
                    
* **JTree**

=====================    =========================
onSelection              TreeSelectionEvent
=====================    =========================
                    
* **Window** 

=====================    =========================
onStateChanged           WindowEvent

onWindowActivated        WindowEvent
                    
onWindowClosed           WindowEvent
                    
onWindowClosing          WindowEvent
                    
onWindowDeactivated      WindowEvent
                    
onWindowDeiconified      WindowEvent
                    
onWindowFocus            WindowEvent
                    
onWindowFocusLost        WindowEvent
                    
onWindowIconified        WindowEvent
                    
onWindowOpened           WindowEvent
=====================    =========================


Customizing BetterBeansBinding logic
------------------------------------

As mentioned previously Swing JavaBuilder uses the Better Beans Binding (BBB) library for data binding support.
However, in some cases the default binding logic may not be flexible enough for all cases and may 
require further customization (such as adding a custom Converter). There are two ways to accomplish this.

BindingsGroup modification
^^^^^^^^^^^^^^^^^^^^^^^^^^

When a BuildResult object is generated via a build, it's *getBindingContext()* method will return
a standard BBB BindingGroup object:
http://www.jarvana.com/jarvana/view/it/tidalwave/betterbeansbinding/betterbeansbinding-core/1.3.0/betterbeansbinding-core-1.3.0-javadoc.jar!/org/jdesktop/beansbinding/BindingGroup.html

Since BuildResult is generic across all domains, it returns an *Object* from that method. It needs to be cast explicitly
to BindingGroup when working in Swing.

.. code-block:: java

     BindingGroup group = (BindingGroup)result.getBindingContext();

Registering a BindingListener
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Another option is to register a BindingListener for the whole application. This will get fired every time a binding is created,
but *before* the BBB *bind()* is called. 

.. code-block:: java

    SwingJavaBuilder.getConfig().addBindingListener(
                    new IBindingListener<Binding<? extends Object,
                          ? extends Object,
                          ? extends Object,
                          ? extends Object>>() {
                @Override
                public void bindingCreated(BuildResult result,
                        Binding<? extends Object, 
                        ? extends Object, 
                        ? extends Object, 
                        ? extends Object> binding) {
                    
                    //do something, e.g. add custom converter depending on the type of the property
                    
                }
            });


Using special binding properties
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
BBB allows you to specify extra parameters that control how the bound objects
are updated when the UI changes::

	JPanel:
	     - JTextField(name=author)
	     - JTextField(name=title)
	     - JTextField(name=price)
	bind:
	     - author.text: this.book.author_ON_FOCUS_LOST
	     - title.text: this.book.title
	     - price.text: ${this.book.price}
	    
In the example above, the content of the *author* text field will only update
its bound property when the focus is lost.

Full list of properties and what controls they apply to is documented:
http://netbeans.org/kb/docs/java/gui-binding.html#prop-synth
