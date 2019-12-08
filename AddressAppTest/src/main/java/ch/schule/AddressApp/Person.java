package ch.schule.AddressApp;

public class Person {
    private String surename;
    private String name;
    private String mail;
    private String school;
    private String subject;
    private int index;

    public Person (){
        super();
    }
    public Person(String surename, String name, String mail, String school, String subject) {
        this.surename = surename;
        this.name = name;
        this.mail = mail;
        this.school = school;
        this.subject = subject;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
