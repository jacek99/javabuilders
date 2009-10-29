package org.javabuilders.swing.test.issues.resources.issue57;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.UIManager;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

/**
 * BeanTableModelFrame.
 *
 * @author anavarro - 31 Jul 2009
 *
 */
public final class BeanTableModelFrame extends JFrame
{
    private JTable mainTable;


    private final BuildResult result = SwingJavaBuilder.build(this);

    /**
     * add.
     *
     */
    private void addARow()
    {
        final BeanTableModel<Spot> tableModel = (BeanTableModel<Spot>) this.mainTable.getModel();
        this.mainTable.setModel(tableModel);
        System.out.println("mainTable=" + tableModel);
    }


    /**
     * main.
     *
     * @param arguments
     */
    /**
     * main.
     * 
     * @param arguments
     */
    public static void main(final String[] arguments)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    SwingJavaBuilder.getConfig().addType(BeanTableModel.class);
                    //SwingXConfig.register();
                    final BeanTableModelFrame frame = new BeanTableModelFrame();
                    frame.setVisible(true);

                }
                catch (Exception ex)
                {
                    System.err.println(ex);
                }

            }
        });
    }

}
