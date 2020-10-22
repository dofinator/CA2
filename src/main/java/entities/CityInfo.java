package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CityInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@Column(length = 4)
    private String zip;
    //@Column(length=35)
    private String city;

    @OneToMany(mappedBy = "cityInfo", cascade = CascadeType.PERSIST)
    List<Address> addresses;
    
  
    public CityInfo() {
    }

    public CityInfo(String zip, String city) {
        this.zip = zip;
        this.city = city;
        this.addresses = new ArrayList();
    }

    public String getZip() {
        return zip;
    }


    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void addAdress(Address address) {
        this.addresses.add(address);
        if(address != null){
            address.setCityInfo(this);
        }
        
    }

}
