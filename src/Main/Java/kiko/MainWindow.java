package kiko;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Kiko kiko;
    private Stage stage;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Cinnamoroll.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Pompompurin.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        
        // Add Kiko's greeting when the GUI starts
        String greeting = "Hello! I'm Kiko the bunny\nWhat can I do for you? >.<";
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(greeting, dukeImage)
        );
    }

       /** Injects the Kiko instance */
    public void setKiko(Kiko k) {
        kiko = k;
    }
    
    /** Sets the stage for window control */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Kiko's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = kiko.getResponse(input);
        
        // Check if the response indicates window should close
        if (response.startsWith("CLOSE_WINDOW:")) {
            // Extract the actual message without the close signal
            String actualResponse = response.substring("CLOSE_WINDOW:".length());
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(actualResponse, dukeImage)
            );
            userInput.clear();
            
            // Close the window after a short delay to show the farewell message
            new Thread(() -> {
                try {
                    Thread.sleep(1500); // Wait 1.5 seconds to show the message
                    javafx.application.Platform.runLater(() -> {
                        if (stage != null) {
                            stage.close();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            // Normal response
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            userInput.clear();
        }
    }
}