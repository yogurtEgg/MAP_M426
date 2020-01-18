package ch.schule.MAP;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class SqliteConnection {

    private ArrayList<Person> people = new ArrayList<>();

    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:Database/people.sqlite");
            return con;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void insert(Person person) {
        String sql = "INSERT INTO people(id, firstname, lastname, mail, school, subject) VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement statement = Connector().prepareStatement(sql);
            statement.setInt(1, person.getid());
            statement.setString(2, person.getFirstName());
            statement.setString(3, person.getLastName());
            statement.setString(4, person.getMail());
            statement.setString(5, person.getSchool());
            statement.setString(6, person.getSubject());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void delete(int id){
        String sql = "DELETE FROM people WHERE id = ?";
        try {
            PreparedStatement statement = Connector().prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(String mail){
        String sql = "DELETE FROM people WHERE mail = ?";
        try {
            PreparedStatement statement = Connector().prepareStatement(sql);
            statement.setString(1, mail);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Person retrieve(int id){
        String sql = "SELECT id, firstname, lastname, mail, school, subject FROM people";
        try {
            Statement stmt  = Connector().createStatement();
            ResultSet resultSet  = stmt.executeQuery(sql);


            Person tmp = new Person();
            while (resultSet.next()) {
                if(resultSet.getInt("id") == id){
                    tmp.setFirstName(resultSet.getString("firstname"));
                    tmp.setLastName(resultSet.getString("lastname"));
                    tmp.setMail(resultSet.getString("mail"));
                    tmp.setSchool(resultSet.getString("school"));
                    tmp.setSubject(resultSet.getString("subject"));
                }
            }
            return tmp;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Person> retrieveAll(){
        String sql = "SELECT id, firstname, lastname, mail, school, subject FROM people";
        try {
            Statement stmt  = Connector().createStatement();
            ResultSet resultSet  = stmt.executeQuery(sql);
            while(resultSet.next()){
                Person tmp = new Person();
                tmp.setFirstName(resultSet.getString("firstname"));
                tmp.setLastName(resultSet.getString("lastname"));
                tmp.setMail(resultSet.getString("mail"));
                tmp.setSchool(resultSet.getString("school"));
                tmp.setSubject(resultSet.getString("subject"));
                people.add(tmp);
            }
            return people;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void resetAll(ObservableList<Person> people) {
        String sql = "DELETE FROM people";
        try {
            PreparedStatement statement = Connector().prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        for(Person p : people){
            if(!(p == null)){
                insert(p);
                System.out.println("Database updated");
            }
        }
    }

}
