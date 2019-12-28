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

    public Person getPersonID(int id) {
        for(Person p : people){
            if(p.getid() == id){
                return p;
            }
        }
        return null;
    }

    public boolean personExists(Person person){

        if(person!=null && person.getid()!=null){
            for(Person p : people){
                if(p.getid() == person.getid()){
                    System.out.println("exists");
                    return true;
                }
            }
        }
        System.out.println("does not exist");
        return false;

    }
    //METHODS
    public void addPerson(Person p){
        people.add(p);
    }

    public void deletePerson(Person p){
        people.remove(p);
    }
}
