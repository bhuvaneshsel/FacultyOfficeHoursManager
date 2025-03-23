package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SavedOfficeHoursController implements Initializable {

    @FXML
    private TableColumn<SemesterOfficeHours, String> days;

    @FXML
    private TableView<SemesterOfficeHours> officeHoursTable;

    @FXML
    private TableColumn<SemesterOfficeHours, String> semester;

    @FXML
    private TableColumn<SemesterOfficeHours, String> year;

    String officeHoursCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/OfficeHours.csv";

    private ObservableList<SemesterOfficeHours> dataList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //gets data from CSV and sorts in descending order
        try {
             dataList = loadCSVData(officeHoursCSV);
             FXCollections.sort(dataList);

            //initializes tableView with data in CSV
            semester.setCellValueFactory(new PropertyValueFactory<SemesterOfficeHours, String>("semester"));
            year.setCellValueFactory(new PropertyValueFactory<SemesterOfficeHours, String>("year"));
            days.setCellValueFactory(new PropertyValueFactory<SemesterOfficeHours, String>("days"));
            officeHoursTable.setItems(dataList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //gets data from CSV and stores it in a list
    private ObservableList<SemesterOfficeHours> loadCSVData (String filePath) throws FileNotFoundException {
        ObservableList<SemesterOfficeHours> CSVList = FXCollections.observableArrayList();
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

            SemesterOfficeHours semOfficeHours;

            if (values.length > 1 && values.length < 3) {
                semOfficeHours = new SemesterOfficeHours(values[0], values[1]);
                CSVList.add(semOfficeHours);
            }
            else if (values.length > 2) {
                semOfficeHours = new SemesterOfficeHours(values[0], values[1], values[2]);
                CSVList.add(semOfficeHours);
            }
        }
        scanner.close();
        return CSVList;
    }

    public void switchToHome(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}


