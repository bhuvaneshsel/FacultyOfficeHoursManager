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
import javafx.util.Pair;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;

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

    String officeHoursCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/OfficeHours.csv";

    HashSet<Pair<String,String>> dataSet = new HashSet<>();

    //executes when Save button is pressed
    public void saveForm(ActionEvent e) throws IOException {
        if (validateInputs()) {
            errorLabel.setText("");

            //stores data in CSV
            FileWriter writer = new FileWriter(officeHoursCSV, true);
            String selectedDays = getSelectedDays();
            writer.append("\n" + semesterChoiceBox.getValue() + "," + yearTextField.getText() + "," + selectedDays);
            writer.close();


            //stores Semester and Year in HashSet to use to check for duplicate entries
            dataSet.add(new Pair<>(semesterChoiceBox.getValue(), yearTextField.getText()));
            switchToHome(e);
        }
    }

    public String getSelectedDays() {
        String selectedDays = "";
        if (mondayCheckBox.isSelected()) {
            selectedDays += "Monday ";
        }
        if (tuesdayCheckBox.isSelected()) {
            selectedDays += "Tuesday ";
        }
        if (wednesdayCheckBox.isSelected()) {
            selectedDays += "Wednesday ";
        }
        if (thursdayCheckBox.isSelected()) {
            selectedDays += "Thursday ";
        }
        if (fridayCheckBox.isSelected()) {
            selectedDays += "Friday ";
        }
        return selectedDays;
    }

    //Gets initial CSV data (if there is any) and puts it in a HashMap which is used to check for duplicate entries
    public HashSet<Pair<String, String>> getInitialData() throws FileNotFoundException {
        HashSet<Pair<String,String>> data = new HashSet<>();
        File officeHoursFile = new File(officeHoursCSV);

        Scanner sc = new Scanner(officeHoursFile);

        //skips headers
        if (sc.hasNextLine()) {
            sc.nextLine();
        }

        //reads each line in CSV and puts semester and year values in HashSet
        while(sc.hasNextLine()) {
            String[] values = sc.nextLine().split(",");
            String semester = "";
            String year = "";
            if (values.length > 1) {
                semester = values[0];
                year = values[1];
            }

            data.add(new Pair<String,String>(semester, year));
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
        //checks for valid year input
        if (yearTextField.getText().isEmpty()) {
            errorLabel.setText("A year is required");
            return false;
        }
        //checks that at least on day is selected
        if (!mondayCheckBox.isSelected() && !tuesdayCheckBox.isSelected() && !wednesdayCheckBox.isSelected()
                && !thursdayCheckBox.isSelected() && !fridayCheckBox.isSelected()) {
            errorLabel.setText("Please select at least one day");
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

        //checks for duplicate entries
        if (dataSet.contains(new Pair<>(semesterChoiceBox.getValue(), yearTextField.getText()))) {
            errorLabel.setText("Duplicate entries are not allowed");
            return false;
        }
        return true;
    }

    //sets values for Semester drop-down
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        semesterChoiceBox.getItems().addAll(new String[]{"Spring", "Summer", "Fall", "Winter"});
        semesterChoiceBox.setValue("Spring");

        try {
            dataSet = getInitialData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
