package ch.schule.AddressApp;

import java.util.ArrayList;

public class PersonModel {
    private ArrayList<Person> people;

    public PersonModel() {
        this.people = new ArrayList<>();
    }

    //GETTER & SETTER
    public ArrayList<Person> getPeople(){
        return people;
    }

    public Person getPersonWID(int index) {
        for(Person p : people){
            if(p.getIndex() == index){
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

    public void editPerson(String nSurname, String nName,  String nMail, String nSchool, String nSubject, int index){
        Person tmpPerson = getPersonWID(index);
        tmpPerson.setSurename(nSurname);
        tmpPerson.setName(nName);
        tmpPerson.setMail(nMail);
        tmpPerson.setSchool(nSchool);
        tmpPerson.setSubject(nSubject);
    }

}
