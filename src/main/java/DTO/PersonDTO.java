/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sumit
 */
public class PersonDTO {

    private String fName;
    private String street;
    private String city;
    private String zip;
    private String phone;

    public PersonDTO(Person person) {
        this.fName = person.getName();
        this.street = person.getStreet();
        this.city = person.getCity();
        this.zip = person.getZip();
        this.phone = person.getPhone();
    }

    public PersonDTO() {
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    

}
