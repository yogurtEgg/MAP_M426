package ch.schule.MAP;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;

public class PersonModel {
	 private final ObservableList<Person> people;

    //DATABASE CONNECTION AND ArrayList
    Connection connection;
    SqliteConnection sqliteConnection = new SqliteConnection();

    public PersonModel() {
    	people = FXCollections.observableArrayList(sqliteConnection.retrieveAll());
        connection = SqliteConnection.Connector();
        if(connection == null){
            System.exit(1);
        }
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
        databaseRefresh();
    }

    public void deletePerson(Person p){
        people.remove(p);
        sqliteConnection.delete(p.getMail());
    }

    //Database
    public boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //refreshes all the values in the database (used due to unspecified id)
    public void databaseRefresh() {
        sqliteConnection.resetAll(people);
    }
}
