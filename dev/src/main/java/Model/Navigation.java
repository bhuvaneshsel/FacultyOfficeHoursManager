package Model;

import javafx.event.ActionEvent;
import java.io.IOException;

public interface Navigation {

    public void navigateTo(ActionEvent event, String fxmlFile) throws IOException;

}