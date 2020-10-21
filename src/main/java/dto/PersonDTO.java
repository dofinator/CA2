package dto;

import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import java.util.List;

public class PersonDTO {

    private long id;
    private String fName;
    private String lName;
    private String email;
    private String street;
    private String city;
    private String zip;
    private List<HobbyDTO> hobbies;
    private List<PhoneDTO> phones;

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.fName = person.getFirstName();
        this.lName = person.getLastName();
        this.email = person.getEmail();
        this.street = person.getAddress().getStreet();
        this.city = person.getAddress().getCityInfo().getCity();
        this.zip = person.getAddress().getCityInfo().getZip();

        this.hobbies = new ArrayList();
        for (Hobby hobby : person.getHobbies()) {
            this.hobbies.add(new HobbyDTO(hobby));
        }

        this.phones = new ArrayList();
        for (Phone phone : person.getPhones()) {
            this.phones.add(new PhoneDTO(phone));
        }
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

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }
    
    
}
