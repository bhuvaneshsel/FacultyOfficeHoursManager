package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class EditScheduleFormController extends NavController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TextField commentTextField;

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField fullNameTextField;

    @FXML
    private TextField reasonTextField;

    @FXML
    private Button saveButton;

    @FXML
    private DatePicker scheduleDate;

    @FXML
    private ComboBox<String> timeSlotComboBox;

    private SemesterSchedule selectedSchedule;

    private ObservableList<SemesterSchedule> schedulesList = FXCollections.observableArrayList();

    private final NavController navigator =  new NavController();



    String timeSlotsCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/TimeSlots.csv";
    String coursesCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/Courses.csv";
    String scheduleCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/Schedule.csv";

    public void setInitialData(SemesterSchedule selectedSchedule,ObservableList<SemesterSchedule> schedulesList) {
        this.selectedSchedule = selectedSchedule;
        this.schedulesList = schedulesList;
        System.out.println(selectedSchedule);

        courseComboBox.setValue(selectedSchedule.getCourse());
        fullNameTextField.setText(selectedSchedule.getFullName());
        commentTextField.setText(selectedSchedule.getComment());
        reasonTextField.setText(selectedSchedule.getReason());
        timeSlotComboBox.setValue(selectedSchedule.getTimeSlot());

        String StringDate = selectedSchedule.getDate();
        String dateValues[] = StringDate.split("/");
        int month = Integer.parseInt(dateValues[0]);
        int day = Integer.parseInt(dateValues[1]);
        int year = Integer.parseInt(dateValues[2]);
        LocalDate date = LocalDate.of(year, month, day);
        scheduleDate.setValue(date);

    }

    public ArrayList<String> getTimeSlots(String filePath) throws FileNotFoundException {
        ArrayList<String> CSVList = new ArrayList<>();

        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        //skip header
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(",", 2);

            if (values.length > 1) {
                String timeSlot = values[0] + " - " + values[1];
                CSVList.add(timeSlot);
            }
        }
        scanner.close();
        return CSVList;
    }

    public ArrayList<String> getCourses(String filePath) throws FileNotFoundException {
        ArrayList<String> CSVList = new ArrayList<>();

        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        //skip header
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(",", 3);

            if (values.length > 2) {
                String course = values[0] + "-" + values[2];
                CSVList.add(course);
            }
        }
        scanner.close();
        return CSVList;
    }

    public boolean validateInputs() {
        if (fullNameTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter a full name");
            return false;
        }
        if (scheduleDate.getValue() == null) {
            errorLabel.setText("Please enter valid date");
            return false;
        }
        return true;
    }

    //reformats LocalDate into MM/DD/YYYY
    public String getReformattedDate(LocalDate date) {
        int monthInt = date.getMonthValue();
        String month = "";
        if (monthInt < 10) {
            month = "0" + monthInt;
        }
        else {
            month += monthInt;
        }

        int dayInt = date.getDayOfMonth();
        String day = "";
        if (dayInt < 10) {
            day = "0" + dayInt;
        }
        else {
            day += dayInt;
        }

        String year = String.valueOf(date.getYear());

        String reformattedDate = month + "/" + day + "/" + year;

        return reformattedDate;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArrayList<String> timeSlots = getTimeSlots(timeSlotsCSV);
            ArrayList<String> courses = getCourses(coursesCSV);

            timeSlotComboBox.getItems().addAll(timeSlots);
            courseComboBox.getItems().addAll(courses);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelForm(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "EditSchedules.fxml");
    }

    public void submitForm(ActionEvent e) throws IOException {
        if (validateInputs()) {
            schedulesList.remove(selectedSchedule);

            SemesterSchedule updatedSchedule = new SemesterSchedule(
                    fullNameTextField.getText(),
                    getReformattedDate(scheduleDate.getValue()),
                    timeSlotComboBox.getValue(),
                    courseComboBox.getValue(),
                    reasonTextField.getText(),
                    commentTextField.getText()
            );
            schedulesList.add(updatedSchedule);
            updateCSVFile();
            navigator.navigateTo(e, "EditSchedules.fxml");
        }
    }



    private void updateCSVFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(scheduleCSV))) {
            writer.println("Name,Date,TimeSlot,Course,Reason,Comment");

            //Loops thorugh current data and rewrites in csv
            for (SemesterSchedule schedule : schedulesList) {
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
