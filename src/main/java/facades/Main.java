/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import entities.Hobby;
import entities.Phone;
import java.util.ArrayList;

/**
 *
 * @author lukas
 */
public class Main {
    public static void main(String[] args) {
    PersonFacade p = new PersonFacade();
    
   
    
    ArrayList<Hobby> hobbies = new ArrayList();
    hobbies.add(new Hobby("hashashsh", "sadasdasd"));
    
     ArrayList<Phone> phones = new ArrayList();
    phones.add(new Phone("hashashsh", "sadasdasd"));

    PersonDTO person = new PersonDTO("Sumit", "Dey", "sumitdey0007@gmail.com", "taastrupvej123", "taastrup", "2630", hobbies, phones);
        System.out.println(p.createNewPerson(person));
        
    }
}
