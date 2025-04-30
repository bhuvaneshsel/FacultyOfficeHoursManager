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
import javafx.scene.control.Button;
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

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class SavedCoursesController implements Initializable{

    @FXML
    private TableColumn<SemesterCourses, String> CodeName;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<SemesterCourses, String> CourseName;

    @FXML
    private TableColumn<SemesterCourses, String> SecName;

    @FXML
    private TableView<SemesterCourses> coursesTable;

    private final NavController navigator =  new NavController();

    String coursesCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/Courses.csv";

    private ObservableList<SemesterCourses> dataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            dataList = loadCSVData(coursesCSV);
            FXCollections.sort(dataList);

            CodeName.setCellValueFactory(new PropertyValueFactory<SemesterCourses, String>("CodeName"));
            CourseName.setCellValueFactory(new PropertyValueFactory<SemesterCourses,String>("CourseName"));
            SecName.setCellValueFactory(new PropertyValueFactory<SemesterCourses,String>("SecName"));
            coursesTable.setItems(dataList);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    ObservableList<SemesterCourses> loadCSVData(String filePath) throws FileNotFoundException {
        ObservableList<SemesterCourses> CSVList = FXCollections.observableArrayList();
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


            SemesterCourses course;
            if (values.length > 1) {
                course = new SemesterCourses(values[0], values[1], values[2]);
                CSVList.add(course);
            }
        }
        scanner.close();
        return CSVList;
    }
    public void switchToHome(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "Home.fxml");
    }
}
