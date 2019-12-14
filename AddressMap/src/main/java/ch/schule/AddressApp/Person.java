package ch.schule.AddressApp;

public class Person {
    private String firstName;
    private String lastName;
    private String mail;
    private String school;
    private String subject;
    private int id;

    public Person (){
        super();
    }
    public Person(String firstName, String lastName, String mail, String school, String subject, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.school = school;
        this.subject = subject;
        this.id = id;
    }

    public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getid() {
        return id;
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
	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", mail=" + mail + ", school=" + school
				+ ", subject=" + subject + ", id=" + id + "]";
	}

    
}
