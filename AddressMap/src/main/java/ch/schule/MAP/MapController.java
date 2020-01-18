package ch.schule.MAP;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MapController {

    @FXML
    private TextField firstname, lastname, eMail, subject, school, search;
    @FXML
    private Label requiredLastname, requiredMail, requiredSchool, requiredSubject;
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
        //Enables Selection
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                //Confirms selection
                System.out.println("Selection: " + newSelection);
                setPerson((Person) newSelection);
            }
        });

        //Creates Columns in the tabel
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


        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                updateFilteredData();
            }
        });


        // Add filtered data to the table
        table.setItems(filteredData);
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

    //sorts tabel
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

            //Person that exits will be changed
            if (model.personExists(selectedPerson)) {
                selectedPerson.setLastName(lastname.getText());
                selectedPerson.setFirstName(firstname.getText());
                selectedPerson.setMail(eMail.getText());
                selectedPerson.setSchool(school.getText());
                selectedPerson.setSubject(subject.getText());
                //refreshed tabel
                table.refresh();
                //refreshed ObservableList
                model.refresh(selectedPerson);
                System.out.println("succesfully edited");
                System.out.println(model.getPeople());
            //Person does not exist will be
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

}
