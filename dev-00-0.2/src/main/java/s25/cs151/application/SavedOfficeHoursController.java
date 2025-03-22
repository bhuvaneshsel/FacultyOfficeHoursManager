package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SavedOfficeHoursController implements Initializable {

    @FXML
    private TableColumn<SemesterOfficeHours, String> days;

    @FXML
    private TableView<?> officeHoursTable;

    @FXML
    private TableColumn<SemesterOfficeHours, String> semester;

    @FXML
    private TableColumn<SemesterOfficeHours, String> year;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadCSVData("/src/main/resources/s25/cs151/application/OfficeHours.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //To do: create list and add new object with scanned data to it
    private void loadCSVData (String filePath) throws FileNotFoundException {

        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        //skip header
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        //Read file and populate list
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(",", 3);
        }
        scanner.close();
    }
}


