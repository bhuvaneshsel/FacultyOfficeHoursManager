package s25.cs151.application;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SemesterCoursesController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField codeTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField numberTextField;
    @FXML
    private Label errorLabel;

    String coursesCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/Courses.csv";

    HashSet<ImmutableTriple<String,String,String>> dataSet = new HashSet<>();

    //executes when Save button is pressed
    public void saveForm(ActionEvent e) throws IOException {
        if (validateInputs()) {
            errorLabel.setText("");

            //stores data in CSV
            FileWriter writer = new FileWriter(coursesCSV, true);
            writer.append("\n").append(codeTextField.getText()).append(",").append(nameTextField.getText()).append(",").append(numberTextField.getText());
            writer.close();

            //stores course code,name and number in HashSet to use to check for duplicate entries
            dataSet.add(new ImmutableTriple<>(codeTextField.getText(), nameTextField.getText(), numberTextField.getText()));
            switchToHome(e);
        }
    }

    //Gets initial CSV data (if applicable) and puts it in a HashSet which is used to check for duplicate entries
    public HashSet<ImmutableTriple<String, String, String>> getInitialData() throws FileNotFoundException {
        HashSet<ImmutableTriple<String,String,String>> data = new HashSet<>();
        File coursesFile = new File(coursesCSV);

        Scanner sc = new Scanner(coursesFile);

        //skips headers
        if (sc.hasNextLine()) {
            sc.nextLine();
        }

        //reads each line in CSV and puts values in HashSet
        while(sc.hasNextLine()) {
            String[] values = sc.nextLine().split(",");
            String code = "";
            String name = "";
            String number = "";
            if (values.length > 2) {
                code = values[0];
                name = values[1];
                number = values[2];
            }
            data.add(new ImmutableTriple<>(code, name, number));
        }
        sc.close();

        return data;
    }

    //executes when Cancel and Save button are pressed
    public void switchToHome(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public boolean validateInputs() {
        //checks for course code input
        if (codeTextField.getText().isEmpty()) {
            errorLabel.setText("A course code is required");
            return false;
        }
        //checks for course name input
        if (nameTextField.getText().isEmpty()) {
            errorLabel.setText("A course name is required");
            return false;
        }
        //checks for course number input
        if (numberTextField.getText().isEmpty()) {
            errorLabel.setText("A section number is required");
            return false;
        }

        //checks for duplicate entries for combination "course code, course name, section number"
        if (dataSet.contains(new ImmutableTriple<>(codeTextField.getText(), nameTextField.getText(), numberTextField.getText()))) {
            errorLabel.setText("Duplicate entries are not allowed");
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dataSet = getInitialData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
