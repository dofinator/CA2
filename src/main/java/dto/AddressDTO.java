/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Address;
import entities.Person;
import java.util.List;

/**
 *
 * @author sebas
 */
public class AddressDTO {

    private String street;

    public AddressDTO() {
    }

    public AddressDTO(Address address) {
        this.street = address.getStreet();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

}
