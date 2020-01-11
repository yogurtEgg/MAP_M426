package ch.schule.AddressApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MapController {
    @FXML
    GridPane grid;
    @FXML
    TextField firstname, lastname, eMail, subject, school;
    @FXML
    Label requiredLastname, requiredMail, requiredSchool, requiredSubject;
    @FXML
    Button saveButton;
    @FXML
    TableView table;


    private PersonModel model;
    private FormValidation formValidation;
    private Integer newestID;
    private Person selectedPerson;

    public MapController() {
        this.model = new PersonModel();
        this.formValidation = new FormValidation();
        this.newestID = 0;
    }

    public void initialize() {
        //Tabelle wird initialized. addListener = Funktion
        //Ermölicht Selektierung
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                //Bestätigung selektierung
                System.out.println("Selection: " + newSelection);
                //setPerson Methode aufgerufen
                setPerson((Person) newSelection);
            }
        });

        //Erstellt Columns der Tabelle
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        //Befüllt Tabelle mit bestehenden Einträgen
        table.setItems(model.getPeople());
        table.getColumns().addAll(firstNameCol, lastNameCol);
        System.out.println("successfully initialized");
    }


    /**
     * Befüllt Textfelder mit Informationen der Person
     *
     * @param person Ausgewählte Person
     */
    private void setPerson(Person person) {
        firstname.setText(person.getFirstName());
        lastname.setText(person.getLastName());
        eMail.setText(person.getMail());
        subject.setText(person.getSubject());
        school.setText(person.getSchool());
        selectedPerson = person;
        System.out.println("New selected Person");
    }


    /** @FXML public void handleButtonSave(ActionEvent actionEvent) {
    if (!textfieldNotNull()) {
    System.out.println(school.getText().length());
    Person p = new Person(firstname.getText(), lastname.getText(), eMail.getText(), school.getText(), subject.getText(), newestID);
    if (p.getSchool().length() != 3) {
    requiredSchool.setText("Needs exactly three letters");
    System.out.println("error");
    }
    }
    }**/

    /**
     * Is im Moment save Buttons für News
     *
     * @param event
     */
    @FXML
    private void handleButtonNew(ActionEvent event) {
        if (selectedPerson != null) {
            selectedPerson.setID(null);
            System.out.println("Id removed");
        }
        lastname.setText(null);
        firstname.setText(null);
        eMail.setText(null);
        subject.setText(null);
        school.setText(null);
        System.out.println("Textfields empty");
    }

    /**
     * save Button für edits
     *
     * @param event
     */
    @FXML
    private void handleButtonSave(ActionEvent event) {
        boolean test = textfieldNotNull();
        if (!textfieldNotNull()) {
            if (school.getText().length() != 3) {
                requiredSchool.setText("Needs exactly three letters");
                System.out.println("error while saving");
            } else {
                Person p = new Person();
                p.setSubject(subject.getText());
                p.setSchool(school.getText());
                p.setMail(eMail.getText());
                p.setFirstName(firstname.getText());
                p.setLastName(lastname.getText());
                if (model.personExists(selectedPerson)) {
                    selectedPerson.setLastName(lastname.getText());
                    selectedPerson.setFirstName(firstname.getText());
                    selectedPerson.setMail(eMail.getText());
                    selectedPerson.setSchool(school.getText());
                    selectedPerson.setSubject(subject.getText());
                    //refreshed Tabelle
                    table.refresh();
                    System.out.println("succesfully edited");
                } else {
                    p.setID(newestID++);
                    model.addPerson(p);
                    table.refresh();
                    System.out.println("succesfully added");
                }

            }
        }
    }

    /**
     * deletes person
     *
     * @param event
     */
    @FXML
    private void handleButtonDelete(ActionEvent event) {
        model.deletePerson(selectedPerson);
        lastname.setText(null);
        firstname.setText(null);
        eMail.setText(null);
        subject.setText(null);
        school.setText(null);

        System.out.println("successfully deleted: " + selectedPerson);
    }

    /**
     * Opens a Mail-Program
     *
     * @param actionEvent
     */
    public void handleButtonMail(ActionEvent actionEvent) throws IOException, URISyntaxException {
        mail();
    }

    public boolean textfieldNotNull() {
        String text = null;
        boolean correct = true;
        boolean validName = formValidation.textFieldNotEmpty(lastname, requiredLastname, "Lastname is required");
        boolean validMail = formValidation.textFieldNotEmpty(eMail, requiredMail, "Mail is required");
        boolean validSubject = formValidation.textFieldNotEmpty(subject, requiredSubject, "Subject is required");
        boolean validSchool = formValidation.textFieldNotEmpty(school, requiredSchool, "School is required");

        if (!validMail || !validName || !validSchool || !validSubject) {
            correct = false;
        }
        return correct;
    }

    public void mail() throws IOException, URISyntaxException {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                URI mailto = new URI("mailto:" + eMail.getText());
                desktop.mail(mailto);
                System.out.println("Mail to " +eMail.getText() + "send");
            }
        }
    }
}
