package ch.schule.AddressApp;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonModel {
	 private final ObservableList<Person> people;

    public PersonModel() {
    	people = FXCollections.observableArrayList();
    }

    //GETTER & SETTER
    public ObservableList<Person> getPeople(){
        return people;
    }

    public Person getPersonWID(int id) {
        for(Person p : people){
            if(p.getid() == id){
                return p;
            }
        }
        return null;
    }

    //METHODS
    public void addPerson(Person p){
        people.add(p);
    }

    public void deletePerson(Person p){
        people.remove(p);
    }
}
