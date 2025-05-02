package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Model.SemesterSchedule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SavedScheduleController implements Initializable {

    @FXML
    private TableView<SemesterSchedule> scheduleTable;

    @FXML
    private TableColumn<SemesterSchedule, String> fullName;

    @FXML
    private TableColumn<SemesterSchedule, String> date;

    @FXML
    private TableColumn<SemesterSchedule, String> timeSlot;

    @FXML
    private TableColumn<SemesterSchedule, String> course;

    @FXML
    private TableColumn<SemesterSchedule, String> reason;

    @FXML
    private TableColumn<SemesterSchedule, String> comment;

    private final NavController navigator =  new NavController();

    String scheduleCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/Schedule.csv";

    private ObservableList<SemesterSchedule> dataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //gets data from CSV and sorts in descending order
        try {
            dataList = loadCSVData(scheduleCSV);
            FXCollections.sort(dataList);

            //initializes tableView with data in CSV
            fullName.setCellValueFactory(new PropertyValueFactory<SemesterSchedule, String>("FullName"));
            date.setCellValueFactory(new PropertyValueFactory<SemesterSchedule, String>("date"));
            timeSlot.setCellValueFactory(new PropertyValueFactory<SemesterSchedule, String>("TimeSlot"));
            course.setCellValueFactory(new PropertyValueFactory<SemesterSchedule, String>("Course"));
            reason.setCellValueFactory(new PropertyValueFactory<SemesterSchedule, String>("Reason"));
            comment.setCellValueFactory(new PropertyValueFactory<SemesterSchedule, String>("Comment"));
            scheduleTable.setItems(dataList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //gets data from CSV and stores it in a list
    private ObservableList<SemesterSchedule> loadCSVData (String filePath) throws FileNotFoundException {
        ObservableList<SemesterSchedule> CSVList = FXCollections.observableArrayList();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        //skip header
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        //Read file and populate list
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(",", 6);

            SemesterSchedule semesterSchedule;
            if (values.length > 4) {
                semesterSchedule = new SemesterSchedule(values[0], values[1], values[2], values[3], values[4], values[5]);
                CSVList.add(semesterSchedule);
            }
        }
        scanner.close();
        return CSVList;
    }

    public void switchToHome(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "Home.fxml");
    }
}
