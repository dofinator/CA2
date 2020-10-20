package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private List<Phone> phones;
    @ManyToMany(mappedBy = "persons", cascade = CascadeType.PERSIST)
    private List<Hobby> hobbies;
    
    @ManyToOne
    private Address address;

    public Person() {
    }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hobbies = new ArrayList();
        this.phones = new ArrayList();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void addPhone(Phone phone) {
        this.phones.add(phone);
        if(phone != null){
            phone.addPerson(this);
        }
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void addHobby(Hobby hobby) {
        this.hobbies.add(hobby);
        if(hobby != null){
            hobby.getPersons().add(this);
            
        }
    }

    public Address getAddress() {
        return address;
    }

    public void addAdress(Address address) {
        this.address = address;
        if (address != null) {
            address.getPersons().add(this);
        }
    }

    

}
