package ch.schule.AddressApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URISyntaxException;

public class RootController {
    MapController mapController = new MapController();

    @FXML
    public void handleButtonClose(ActionEvent actionEvent) {

        System.out.println("Test erfolgreich handleButtonClose");
    }


    @FXML
    public void handleButtonMail(ActionEvent actionEvent) throws IOException, URISyntaxException {
        System.out.println("Test erfolgreich handleButtonMail");
        mapController.mail();
    }


    @FXML
    public void handleButtonAbout(ActionEvent actionEvent) {
        System.out.println("Test erfolgreich handleButtonAbout");
    }
}

