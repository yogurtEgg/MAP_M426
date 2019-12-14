package ch.schule.AddressApp;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;


public class MapController  {
    private PersonModel model;
    @FXML
    GridPane grid;
    @FXML
    TextField surename, name, eMail, subject, school;
    @FXML
    Label surenameV, nameV, mailV, subjectV, schoolV;


    public MapController() {
        this.model = new PersonModel();
    }

    @FXML
    private void handleButtonAdd(ActionEvent event) {
        Person p = new Person(surename.getText(), name.getText(), eMail.getText(), school.getText(), subject.getText());
        model.addPerson(p);
        //Personen anzeigen
        
    }

    @FXML
    private void handleButtonEdit(ActionEvent event){
       for(Node child : grid.getChildren()){
           child.setVisible(!child.isVisible());
       }
        /*todo
        subjectV.setText(p1.getSubject());
        nameV.setText(p1.getName());
        surenameV.setText(p1.getSurename());
        mailV.setText(p1.getMail());
        schoolV.setText(p1.getSchool());
        */
    }

    @FXML
    private void handleButtonDelete(ActionEvent event){
        //todo
        //model.deletePerson();
    }

}
