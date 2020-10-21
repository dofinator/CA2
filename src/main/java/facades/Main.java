/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author lukas
 */
public class Main {
    public static void main(String[] args) {
        PersonFacade pf = new PersonFacade();
     
        Person person = new Person("Sebastian", "Hansen", "email@gmail.dk");
        Address address = new Address("Lyngbyvej", "Ligger i Lyngby");
        Hobby hobby = new Hobby("Fodbold", "en sport");
        Phone phone = new Phone("12348765", "mobil");
        CityInfo cityInfo = new CityInfo("2800", "Lyngby");
        
        person.addAdress(address);
        person.getAddress().setCityInfo(cityInfo);
        person.addHobby(hobby);
        person.addPhone(phone);
        
        PersonDTO pDTO = new PersonDTO(person);

        pf.createNewPerson(pDTO);
    }
}
