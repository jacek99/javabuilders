package org.javabuilders.swing.test.issues.resources;

import javax.swing.JFrame;
import javax.swing.JSeparator;

import net.miginfocom.swing.MigLayout;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

public class Issue77 extends JFrame
{
    private BuildResult result;
    
    private JSeparator sep;
    
    public Issue77()
    {
        result = SwingJavaBuilder.build(this);       
    }
    
    public String getSepConstraints() {
    	MigLayout mig = (MigLayout) getContentPane().getLayout();
        return (String) mig.getComponentConstraints(sep);
    }
    
    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Issue77().setVisible(true);
                } catch (Exception ex) {
                    System.err.println(ex);
                }
            }
        });
    }
}
