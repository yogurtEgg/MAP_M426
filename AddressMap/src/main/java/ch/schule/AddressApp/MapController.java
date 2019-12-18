package ch.schule.AddressApp;




import com.sun.javafx.scene.NodeEventDispatcher;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;


public class MapController  {
    private PersonModel model;
    @FXML
    GridPane grid;
    @FXML
    TextField firstname, lastname, eMail, subject, school;
    @FXML
    Label firstnameLabel, lastnameLabel, mailLabel, subjectLabel, schoolLabel;
    @FXML
    TableView table;
    
    int newestID;
    Person selectedPerson;

    public MapController() {
        this.model = new PersonModel();
    }
    
    public void initialize(){
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
    }

    
    /**
     * Befüllt Textfelder mit Informationen der Person
     * @param person Ausgewählte Person
     */
    private void setPerson(Person person) {
    	firstname.setText(person.getFirstName());
    	lastname.setText(person.getLastName());
    	eMail.setText(person.getMail());
    	subject.setText(person.getSubject());
    	school.setText(person.getSchool());
    	selectedPerson = person;
	}

    /**
     * Is im Moment save Buttons für News
     * 
     * @param event
     */
	@FXML
    private void handleButtonNew(ActionEvent event) {
        Person p = new Person(firstname.getText(), lastname.getText(), eMail.getText(), school.getText(), subject.getText(), newestID);
        model.addPerson(p);
        //TODO: Textfelder müssen leer gemacht werden und neue TabellenSpalte muss ausgewählt werden.
        
    }

	/**
	 * Ist im Moment save Button für edits, nur Firstname kann bearbeitet werden
	 * @param event
	 */
    @FXML
    private void handleButtonEdit(ActionEvent event){
       if(selectedPerson != null) {
    	   selectedPerson.setLastName(lastname.getText());
           selectedPerson.setFirstName(firstname.getText());
           selectedPerson.setMail(eMail.getText());
           selectedPerson.setSchool(school.getText());
           selectedPerson.setSubject(subject.getText());
    	   //refreshed Tabelle
    	   table.refresh();
       }
       
    }
    
    /**
     * macht nichts
     * 
     * @param event
     */
	@FXML
    private void handleButtonDelete(ActionEvent event){
	    model.deletePerson(selectedPerson);
	    lastname.setText(null);
	    firstname.setText(null);
	    eMail.setText(null);
	    subject.setText(null);
	    school.setText(null);
        //TODO: model.deletePerson(); und Techenxtfields lös
    }

}
