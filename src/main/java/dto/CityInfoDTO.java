/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Address;
import entities.CityInfo;
import java.util.ArrayList;
import java.util.List;


public class CityInfoDTO {
    
    private String zip;
    private String city;
    private List<AddressDTO> addresses;

    public CityInfoDTO(CityInfo cityInfo) {
        this.zip = cityInfo.getZip();
        this.city = cityInfo.getCity();
        
        this.addresses = new ArrayList();
        for (Address address : cityInfo.getAddresses()) {
            this.addresses.add(new AddressDTO(address));
        }
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
    
}
