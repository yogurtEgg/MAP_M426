
package ch.schule.AdressMAP;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;


public class RootController{

    private MapController mapController;

    public RootController() {
        mapController = new MapController();
    }



    public void handleButtonClose(ActionEvent actionEvent) {
        Alert closeConfirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Have you saved everything?"
        );

        closeConfirmation.setHeaderText("Confirm Exit");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if ("OK".equals(closeResponse.get().getText())) {
            Platform.exit();
        }
        System.out.println("Test erfolgreich handleButtonClose");
    }



    public void handleButtonMail(ActionEvent actionEvent) throws IOException, URISyntaxException {
        System.out.println("Test erfolgreich handleButtonMail");
        mapController.mail();
    }


    public void handleButtonKBW(ActionEvent actionEvent) {
        System.out.println("Test erfolgreich handleButtonKBW");
        try {
            Desktop.getDesktop().browse(new URI("https://www.kbw.ch/wirtschaft-und-recht"));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }

    }
    public void handleButtonBBW(ActionEvent actionEvent) {
        Alert closeConfirmation = new Alert(
                Alert.AlertType.INFORMATION,
                "Username: surename.lastname\nClick at the top-right on 'Schreiben'"
        );

        closeConfirmation.setHeaderText("Confirm Exit");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if ("OK".equals(closeResponse.get().getText())) {
            actionEvent.consume();
        }
        System.out.println("Test erfolgreich handleButtonBBW");
        try {
            Desktop.getDesktop().browse(new URI("https://lernende.bbw.ch/roundcube/"));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }

    }
    }

