package ch.schule.AddressApp;

import java.util.ArrayList;

public class PersonModel {
    private ArrayList<Person>people;

    public PersonModel() {
        this.people = new ArrayList<>();
    }

    public ArrayList<Person> getPeople(){
        return people;
    }
    public void addPerson(Person p){
        people.add(p);
    }
    public void deletePerson(Person p){
        people.remove(p);
    }

}
