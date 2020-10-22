/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.HobbyDTO;
import dto.PersonDTO;
import dto.PersonsDTO;
import dto.PhoneDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author phill
 */
public interface IPersonFacade {
    
     public long getPersonCount() ;
    //Get all persons with a given hobby
    public PersonsDTO getAllPersonsByHobby(String hobby);
    //Get the count of people with a given hobby 
    public long getPeopleCountByHobby(String hobby);
    //Get all persons living in a given city
    public PersonsDTO getAllPersonsByCity(String city);
     //Get the person given a phone number
     public PersonDTO getPersonByPhone(String phone);
    //get the number of people with a given hobby
    public long getHobbyCount(String hobby);
    //get a list of all zip codes in DK
    public List<String> getAllZipCodes();
    //create a person
    public PersonDTO createNewPerson(PersonDTO personDTO);
    //edit a person
    public PersonDTO editPerson(PersonDTO pDTO);
    
}
