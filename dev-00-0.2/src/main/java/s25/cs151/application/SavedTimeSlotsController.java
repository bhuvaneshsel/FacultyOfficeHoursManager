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

public class SavedTimeSlotsController implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private TableColumn<SemesterTimeSlot, String> fromHour;

    @FXML
    private TableView<SemesterTimeSlot> timeSlotsTable;

    @FXML
    private TableColumn<SemesterTimeSlot, String> toHour;

    String timeSlotsCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/TimeSlots.csv";

    private ObservableList<SemesterTimeSlot> dataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            dataList = loadCSVData(timeSlotsCSV);
            FXCollections.sort(dataList);

            fromHour.setCellValueFactory(new PropertyValueFactory<SemesterTimeSlot, String>("fromHour"));
            toHour.setCellValueFactory(new PropertyValueFactory<SemesterTimeSlot,String>("toHour"));
            timeSlotsTable.setItems(dataList);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    ObservableList<SemesterTimeSlot> loadCSVData(String filePath) throws FileNotFoundException {
        ObservableList<SemesterTimeSlot> CSVList = FXCollections.observableArrayList();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        //skip header
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        //Read file and populate list
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(",", 2);


            SemesterTimeSlot timeSlot;
            if (values.length > 1) {
                timeSlot = new SemesterTimeSlot(values[0], values[1]);
                CSVList.add(timeSlot);
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
