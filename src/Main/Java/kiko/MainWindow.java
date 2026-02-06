package kiko;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Kiko's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = kiko.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
    }
}