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

public class HomeController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button officeHoursButton;
    @FXML
    private Button savedOfficeHoursButton;
    @FXML
    private Button coursesButton;

    //executes when Semester's Office Hours button is pressed
    public void switchToOfficeHoursPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DefineOfficeHours.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //executes when Saved Office Hours button is pressed
    public void switchToSavedOfficeHoursPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SavedOfficeHours.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSavedTimeSlotsPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SavedTimeSlots.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDefineTimeSlotsPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DefineTimeSlots.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //executes when Semester's Courses button is pressed
    public void switchToSemesterCoursesPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DefineCourses.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSavedSemesterCoursesPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SavedCourses.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSavedSchedule(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SavedSchedule.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDefineSchedule(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DefineSchedule.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSearchSchedulesPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SearchSchedules.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
