package org.javabuilders.fx.test;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Basic test app  that can display any parent
 */
@RequiredArgsConstructor
public class FXTestApp extends Application {

    private final Class<? extends Pane> paneClass;
    private final int width;
    private final int height;

    @Getter
    private Pane pane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.pane = paneClass.newInstance();

        Scene scene = new Scene(pane,width,height);
        primaryStage.setTitle("FX JavaBuilder Test Application");

        // do not show, we want it to run headless
    }
}
