package fx.person.app;

import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import org.javabuilders.BuildResult;
import org.javabuilders.annotations.DoInBackground;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.CancelStatus;
import org.javabuilders.fx.FXJB;
import org.javabuilders.fx.handler.background.BackgroundDialog;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.ResourceBundle;


/**
 * FX person sample app
 */
public class FXPersonApp extends Application {

	private Person person;
    private Stage stage;
	private BuildResult result;

    private TextField txtFirstName;
    private TextField txtLastName;
    private TextField txtEmail;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // set up or base data
        person = new Person();
        person.firstNameProperty().set("John");
        person.lastNameProperty().set("Doe");
        person.emailAddressProperty().set("john@test.com");

        stage = primaryStage;

        result = FXJB.build(this, ResourceBundle.getBundle("PersonApp"));

        Scene scene = new Scene((Parent) result.getRoot());
        primaryStage.setTitle("JavaFX Person Application");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

	private void cancel() {
		stage.close();
        System.exit(1);
	}

    private void save2() {
        Service saveService = new Service() {
            @Override
            protected Task createTask() {
                return null;
            }
        };

        saveService.start();
        Task t = new Task() {

            @Override
            protected Object call() throws Exception {
                return null;
            }
        };

        t.se
    }

	@DoInBackground(cancelable = true, indeterminateProgress = false, progressStart = 1, progressEnd = 100)
	private void save(BackgroundEvent evt) {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < 100; i++) {


                    // progress indicator
                    evt.setProgressValue(i + 1);
                    evt.setProgressMessage("" + i + "% done...");
                    // check if cancel was requested
                    if (evt.getCancelStatus() != CancelStatus.REQUESTED) {
                        // sleep
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                    } else {
                        // cancel requested, let's abort
                        evt.setCancelStatus(CancelStatus.COMPLETED);
                        break;
                    }
                }
            }
        };

        task.isCancelled()

		// simulate a long running save to a database
		for (int i = 0; i < 100; i++) {
			// progress indicator
			evt.setProgressValue(i + 1);
			evt.setProgressMessage("" + i + "% done...");
			// check if cancel was requested
			if (evt.getCancelStatus() != CancelStatus.REQUESTED) {
				// sleep
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			} else {
				// cancel requested, let's abort
				evt.setCancelStatus(CancelStatus.COMPLETED);
				break;
			}
		}
	}

	// runs after successful save
	private void done() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Person data");
        alert.setHeaderText("E-mail address updated");
        alert.setContentText("Person data: " + person.toString());

        alert.showAndWait();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        launch(args);
	}
}
