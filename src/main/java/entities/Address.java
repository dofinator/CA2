package entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String street;
    private String additionalInfo;
    @OneToMany(mappedBy = "address")
    private Person person;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private CityInfo cityInfo;

    public Address() {
    }

    public Address(String street, String Additionalinfo) {
        this.street = street;
        this.additionalInfo = Additionalinfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalinfo() {
        return additionalInfo;
    }

    public void setAdditionalinfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

}
