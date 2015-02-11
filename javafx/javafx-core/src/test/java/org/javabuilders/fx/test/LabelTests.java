package org.javabuilders.fx.test;

import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.javabuilders.fx.FXJavaBuilder;
import org.javabuilders.fx.test.samplesapp.LabelPane;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Label tests
 */
public class LabelTests {

    private Stage stage;
    private LabelPane labelPane;

    @Before
    public void setup() {
        JFXPanel fxPanel = new JFXPanel();
        labelPane = new LabelPane();
   }

    @After
    public void tearDown() {
    }

    @Test
    public void testControls() {
        labelPane.setCache(false);
    }

}
