package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SearchSchedulesController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<SemesterSchedule, String> comment;

    @FXML
    private TableColumn<SemesterSchedule, String> course;

    @FXML
    private TableColumn<SemesterSchedule, String> date;

    @FXML
    private TableColumn<SemesterSchedule, String> fullName;

    @FXML
    private TableColumn<SemesterSchedule, String> reason;

    @FXML
    private TableView<SemesterSchedule> scheduleTable;

    @FXML
    private TextField searchInput;

    @FXML
    private TableColumn<SemesterSchedule, String> timeSlot;

    String scheduleCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/Schedule.csv";

    private ObservableList<SemesterSchedule> dataList = FXCollections.observableArrayList();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

            //Adds all schedules to filtered list initially
            FilteredList<SemesterSchedule> filteredSchedules = new FilteredList<>(dataList, schedule-> true);

            //Adds a listener to the search input field. Every time the search input is updated, the filtered list updates
            searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredSchedules.setPredicate(schedule -> {

                    //shows all items if input is empty
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    //searches based on substring of full name
                    String input = newValue.toLowerCase();
                    if (schedule.getFullName().toLowerCase().contains(input)) {
                        return true;
                    }
                    return false;
                });
            });

            //sorts filtered list, descending on date and time slot
            SortedList<SemesterSchedule> sortedSchedules = new SortedList<>(filteredSchedules);
            sortedSchedules.setComparator(Comparator.reverseOrder());

            scheduleTable.setItems(sortedSchedules);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToHome(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
