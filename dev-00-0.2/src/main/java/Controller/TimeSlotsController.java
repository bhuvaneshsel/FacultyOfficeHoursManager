package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TimeSlotsController implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> fromHourComboBox;

    @FXML
    private ComboBox<String> fromMinuteComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> toHourComboBox;

    @FXML
    private ComboBox<String> toMinuteComboBox;

    @FXML
    private Label statusLabel;

    private final NavController navigator =  new NavController();

    String timeSlotsCSV = System.getProperty("user.dir")+"/src/main/resources/s25/cs151/application/TimeSlots.csv";

    String[] hours = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String[] minutes = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                        "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                        "31" ,"32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45",
                        "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};

    public void saveForm(ActionEvent e) throws IOException {
        String fromHour = fromHourComboBox.getValue();
        String fromMinute = fromMinuteComboBox.getValue();
        String toHour = toHourComboBox.getValue();
        String toMinute = toMinuteComboBox.getValue();

        if (fromHour == null || fromMinute == null | toHour == null || toMinute == null) {
            statusLabel.setText("All fields are required");
            statusLabel.setTextFill(Color.RED);
        }
        else {
            FileWriter writer = new FileWriter(timeSlotsCSV, true);
            writer.append("\n" + fromHour + ":" + fromMinute + "," + toHour + ":" + toMinute);
            writer.close();
            fromHourComboBox.setValue(null);
            fromMinuteComboBox.setValue(null);
            toHourComboBox.setValue(null);
            toMinuteComboBox.setValue(null);

            statusLabel.setText("The time slot has been successfully added");
            statusLabel.setTextFill(Color.GREEN);
        }
    }

    public void switchToHome(ActionEvent e) throws IOException {
        navigator.navigateTo(e, "Home.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fromHourComboBox.getItems().addAll(hours);
        fromMinuteComboBox.getItems().addAll(minutes);
        toHourComboBox.getItems().addAll(hours);
        toMinuteComboBox.getItems().addAll(minutes);
    }
}
