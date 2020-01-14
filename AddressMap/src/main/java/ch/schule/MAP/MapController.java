package ch.schule.MAP;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MapController {
    @FXML
    private GridPane grid;
    @FXML
    private TextField firstname, lastname, eMail, subject, school, search;
    @FXML
    private Label requiredLastname, requiredMail, requiredSchool, requiredSubject;
    @FXML
    private Button saveButton;
    @FXML
    private TableView table;


    private ObservableList<Person> filteredData = FXCollections.observableArrayList();

    private PersonModel model;
    private FormValidation formValidation;
    private Integer newestID;
    public static Person selectedPerson;

    public MapController() {
        this.model = new PersonModel();
        this.formValidation = new FormValidation();
        this.newestID = 0;

        //check the connection to the database
        if(model.isDbConnected()){
            System.out.println("connected");
        } else {
            System.out.println("not connected");
        }

        /*TEST
        Person per0 = new Person("Test", "Teach", "test.teach@school.ch", "school", "subject", 0);
        Person per1 = new Person("Jaime", "Oberle", "jaime.oberle@bbw.ch", "BBW", "M122, M104", 1);
        Person per2 = new Person("", "Lüthi", "laure.luethi@bbw.ch", "BBW", "M120, M326, M226A", 2);
        Person per3 = new Person("", "Cavuoti", "mall@bbw.ch", "BWW", "M426", 3);
        Person per4 = new Person("Leander", "Schickling", "leander.schickling@kbw.ch", "KBW", "Deutsch", 4);
        Person per5 = new Person("", "Rogg", "luzia.rogg@kbw.ch", "KBW", "Mathe", 5);
        Person per6 = new Person("Kamelia", "Merrad", "kamelia.merrad@kbw.ch", "KBW", "Franz", 6);
        Person per7 = new Person("", "Cavicciolo", "claudio.Cavicciolo@kbw.ch", "KBW", "Franz", 7);
        Person per8 = new Person("Rahel", "Solenthaler", "rahel.solenthaler@kbw.ch", "KBW", "Englisch", 8);
        Person per9 = new Person("Peter", "Meier", "mila@srtsrtk.ch", "BBW", "Mathe", 9);
        Person per10 = new Person("Hans", "Muster", "mail@aitq54zl.ch", "BBW", "Franz", 10);
        Person per11 = new Person("Ueli", "Müller", "mall@aasdfil.ch", "BWW", "Deutsch", 11);
        Person per12 = new Person("Freni", "Kater", "maal@aasdfl.ch", "KBW", "Yeet", 12);

        //TEST
        model.getPeople().add(per0);
        model.getPeople().add(per1);
        model.getPeople().add(per2);
        model.getPeople().add(per3);
        model.getPeople().add(per4);
        model.getPeople().add(per5);
        model.getPeople().add(per6);
        model.getPeople().add(per7);
        model.getPeople().add(per8);
        model.getPeople().add(per9);
        model.getPeople().add(per10);
        model.getPeople().add(per11);
        model.getPeople().add(per12);*/

        filteredData.addAll(model.getPeople());

        // Listen for changes in master data.
        // Whenever the master data changes, filtered data updates
        model.getPeople().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> change) {
                updateFilteredData();
            }
        });
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
        firstNameCol.setMinWidth(75);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(75);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        TableColumn mailCol = new TableColumn("E-Mail");
        mailCol.setMinWidth(175);
        mailCol.setCellValueFactory(
                new PropertyValueFactory<>("mail"));


        // Listen for text changes in the filter text field
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                updateFilteredData();
            }
        });


        // Add filtered data to the table
        table.setItems(filteredData);
        //Befüllt Tabelle mit bestehenden Einträgen
        table.getColumns().addAll(firstNameCol, lastNameCol, mailCol);
        System.out.println("successfully initialized");
    }


    /**
     * Updates the filteredData to contain all data from the masterData that
     * matches the current filter.
     */
    private void updateFilteredData() {
        filteredData.clear();

        for (Person p : model.getPeople()) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }


    /**
     * Returns true if the person matches the current filter. Lower/Upper case
     * is ignored.
     *
     * @param person
     * @return
     */
    private boolean matchesFilter(Person person) {
        String filterString = search.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        //Jeder Eintrag der Person kann gesucht werden, wenn man sie hier einfügt
        if (person.getFirstName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (person.getLastName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (person.getMail().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    //sortiert
    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Person, ?>> sortOrder = new ArrayList<>(table.getSortOrder());
        table.getSortOrder().clear();
        table.getSortOrder().addAll(sortOrder);
    }


    /**
     * Befüllt Textfelder mit Informationen der Person
     *
     * @param person Ausgewählte Person
     */
    public void setPerson(Person person) {
        firstname.setText(person.getFirstName());
        lastname.setText(person.getLastName());
        eMail.setText(person.getMail());
        subject.setText(person.getSubject());
        school.setText(person.getSchool());
        selectedPerson = person;
        System.out.println("New selected Person");
    }


    /**
     * New Button
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
     * save Button
     *
     * @param event
     */
    @FXML
    private void handleButtonSave(ActionEvent event) {
        boolean test = textfieldNotNull();
        if (!textfieldNotNull()) {
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
                URI mailto = new URI("mailto:" + selectedPerson.getMail());
                desktop.mail(mailto);
                System.out.println("Mail to " + selectedPerson.getMail() + "send");
            }
        }
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }
}
