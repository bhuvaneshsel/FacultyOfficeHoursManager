package s25.cs151.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DefineOfficeHoursController implements Initializable {
    @FXML
    private ChoiceBox<String> semesterChoiceBox;
    @FXML
    private TextField yearTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private CheckBox mondayCheckBox;
    @FXML
    private CheckBox tuesdayCheckBox;
    @FXML
    private CheckBox wednesdayCheckBox;
    @FXML
    private CheckBox thursdayCheckBox;
    @FXML
    private CheckBox fridayCheckBox;
    @FXML
    private Label errorLabel;


    //executes when Save button is pressed
    public void saveForm(ActionEvent e) throws IOException {
        if (validateInputs()) {
            Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    //executes when Cancel button is pressed
    public void cancelForm(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //checks for valid year input
    public boolean validateInputs() {
        if (yearTextField.getText().isEmpty()) {
            errorLabel.setText("This field is required");
            return false;
        }
        try {
            int year = Integer.parseInt(yearTextField.getText());
            if (year < 1000 || year > 9999 || year < 0) {
                errorLabel.setText("Please enter a positive four-digit integer");
                return false;
            }
        } catch (Exception e) {
            errorLabel.setText("Please enter a positive four-digit integer");
            return false;
        }
        return true;
    }

    //sets values for Semester drop-down
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        semesterChoiceBox.getItems().addAll(new String[]{"Spring", "Summer", "Fall", "Winter"});
        semesterChoiceBox.setValue("Spring");
    }
}
