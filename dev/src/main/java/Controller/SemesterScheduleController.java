package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SemesterScheduleController implements Initializable {
    @FXML
    private TextField commentTextField;

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    private TextField fullNameTextField;

    @FXML
    private TextField reasonTextField;

    @FXML
    private DatePicker scheduleDate;

    @FXML
    private ComboBox<String> timeSlotComboBox;


    @FXML
    private Label errorLabel;

    @FXML
    private Button saveButton;

    private final NavController navigator =  new NavController();

    String timeSlotsCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/TimeSlots.csv";
    String coursesCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/Courses.csv";
    String scheduleCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/Schedule.csv";

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

    public void submitForm(ActionEvent e) throws IOException {
        if (validateInputs()) {
            FileWriter writer = new FileWriter(scheduleCSV, true);
            String reformattedDate = getReformattedDate(scheduleDate.getValue());
            writer.append("\n" + fullNameTextField.getText() + "," + reformattedDate + "," + timeSlotComboBox.getValue() + "," + courseComboBox.getValue()+ "," + reasonTextField.getText() + "," + commentTextField.getText());
            writer.close();
            switchToHome(e);
        }
    }

    public void switchToHome(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "Home.fxml");
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

    //reformats LocalDate into MM/DD/YYYY
    public String getReformattedDate(LocalDate date) {
        System.out.println(date);

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
            timeSlotComboBox.setValue(timeSlots.get(0));

            courseComboBox.getItems().addAll(courses);
            courseComboBox.setValue(courses.get(0));

            scheduleDate.setValue(LocalDate.now());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
