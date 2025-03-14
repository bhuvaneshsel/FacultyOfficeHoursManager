package s25.cs151.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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
    private Button submitButton;
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



    public void submitForm(ActionEvent e) throws IOException {
        System.out.println(yearTextField.getText());
        System.out.println(semesterChoiceBox.getValue());
        System.out.println(mondayCheckBox.isSelected());
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void cancelForm(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        semesterChoiceBox.getItems().addAll(new String[]{"Spring", "Summer", "Fall", "Winter"});
        semesterChoiceBox.setValue("Spring");
    }
}
