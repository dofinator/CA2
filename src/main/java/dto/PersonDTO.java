package dto;

import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.List;

public class PersonDTO {

    private long id;
    private String fName;
    private String lName;
    private String email;
    private String street;
    private String city;
    private String zip;
    private List<Hobby> hobbies;
    private List<Phone> phones;

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.fName = person.getFirstName();
        this.lName = person.getLastName();
        this.email = person.getEmail();
        this.street = person.getAddress().getStreet();
        this.city = person.getAddress().getCityInfo().getCity();
        this.zip = person.getAddress().getCityInfo().getZip();
        this.hobbies = person.getHobbies();
        this.phones = person.getPhones();
    }
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

}
