package s25.cs151.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController extends NavController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button officeHoursButton;
    @FXML
    private Button savedOfficeHoursButton;
    @FXML
    private Button coursesButton;

    private final NavController navigator =  new NavController();

    //executes when Semester's Office Hours button is pressed
    public void switchToOfficeHoursPage(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "DefineOfficeHours.fxml");
    }

    //executes when Saved Office Hours button is pressed
    public void switchToSavedOfficeHoursPage(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "SavedOfficeHours.fxml");
    }

    public void switchToSavedTimeSlotsPage(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "SavedTimeSlots.fxml");
    }

    public void switchToDefineTimeSlotsPage(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "DefineTimeSlots.fxml");
    }

    //executes when Semester's Courses button is pressed
    public void switchToSemesterCoursesPage(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "DefineCourses.fxml");
    }

    public void switchToSavedSemesterCoursesPage(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "SavedCourses.fxml");
    }

    public void switchToSavedSchedule(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "SavedSchedule.fxml");
    }

    public void switchToDefineSchedule(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "DefineSchedule.fxml");
    }

    public void switchToSearchSchedulesPage(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "SearchSchedules.fxml");
    }

    public void switchToEditSchedulesPage(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "EditSchedules.fxml");
    }

}
