package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Model.SemesterSchedule;

import java.io.*;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SearchSchedulesController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

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

    private final NavController navigator =  new NavController();

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
        navigator.navigateTo(e, "Home.fxml");
    }

    //Occurs when delete button is pushed, if an item in the table was selected it will be removed
    @FXML
    private void handleDelete(ActionEvent e) {
        SemesterSchedule selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            dataList.remove(selectedItem);
            updateCSVFile();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an item to delete.");
            alert.showAndWait();
        }
    }
    //Rewrites the Schedule csv file, without the deleted line
    private void updateCSVFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(scheduleCSV))) {
            writer.println("Name,Date,TimeSlot,Course,Reason,Comment");

            //Loops thorugh current data and rewrites in csv
            for (SemesterSchedule schedule : dataList) {
                writer.printf("%s,%s,%s,%s,%s,%s%n",
                        schedule.getFullName(),
                        schedule.getDate(),
                        schedule.getTimeSlot(),
                        schedule.getCourse(),
                        schedule.getReason(),
                        schedule.getComment());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
