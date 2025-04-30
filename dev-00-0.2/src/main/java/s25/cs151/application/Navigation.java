package s25.cs151.application;

import javafx.event.ActionEvent;
import java.io.IOException;

public interface Navigation {

    public void navigateTo(ActionEvent event, String fxmlFile) throws IOException;

}