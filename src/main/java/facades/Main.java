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
import javax.persistence.Persistence;

/**
 *
 * @author lukas
 */
public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

   EntityManager em = emf.createEntityManager();
        try{
            Person p = new Person("hans", "Andersen", "sssss");
            Address address = new Address("ssdasdad", "asdsadsa");
            CityInfo cityInfo = new CityInfo("2639", "hshs");
            p.addAdress(address);
            cityInfo.addAdress(address);
            
            
            ArrayList<Hobby> hobbies = new ArrayList();
            hobbies.add(new Hobby("sss", "sdasdsa"));
            
              ArrayList<Phone> phones = new ArrayList();
            phones.add(new Phone("sadasdas", "sdasdsad"));
            
            
            
            for (Hobby hobby : hobbies) {
                p.addHobby(hobby);
            }
            for(Phone phone: phones){
                p.addPhone(phone);
            }
                      
            em.getTransaction().begin();
            em.persist(p);
            em.persist(address);
            em.persist(cityInfo);
            em.getTransaction().commit();
            
            
        } finally {
            em.close();
        }

    }
}
