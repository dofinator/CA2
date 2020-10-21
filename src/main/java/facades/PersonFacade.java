package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    PersonFacade() {
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getPersonCount() {
        EntityManager em = emf.createEntityManager();

        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return renameMeCount;
        } finally {

            em.close();
        }

    }

    //Get all persons with a given hobby
    public PersonsDTO getAllPersonsByHobby(String hobby) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Person p JOIN p.hobbies hobbies WHERE hobbies.name = :hobby");
            query.setParameter("hobby", hobby);
            List<Person> personList = query.getResultList();
            
            return new PersonsDTO(personList);
        } finally {
            em.close();
        }

    }

    //Get all persons living in a given city
    public PersonsDTO getAllPersonsByCity(String city) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Person p WHERE p.address.cityInfo.city = :city");
            query.setParameter("city", city);
            List<Person> personList = query.getResultList();

            return new PersonsDTO(personList);
        } finally {
            em.close();
        }
    }

    //get the number of people with a given hobby
    public long getHobbyCount(String hobby) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT COUNT(p) FROM Person p JOIN p.hobbies hobbies WHERE hobbies.name = :hobby");
            query.setParameter("hobby", hobby);
            long hobbyCount = (long) query.getSingleResult();

            return hobbyCount;
        } finally {
            em.close();
        }

    }

    //get a list of all zip codes in DK
    public List<String> getAllZipCodes() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT c.zip FROM CityInfo c");
            List<String> listOfAllZipCodes = query.getResultList();

            return listOfAllZipCodes;
        } finally {
            em.close();
        }
    }
    
    //create a person
    public PersonDTO createNewPerson(PersonDTO person ){
        EntityManager em = emf.createEntityManager();
        try{
            Person p = new Person(person.getfName(), person.getlName(), person.getEmail());
            Address address = new Address(person.getStreet(), person.getAdditionalInfo());
            CityInfo cityInfo = new CityInfo(person.getZip(), person.getCity());
            p.addAdress(address);
            cityInfo.addAdress(address);
            for (Hobby hobby : person.getHobbies()) {
                p.addHobby(hobby);
            }
            for(Phone phone: person.getPhones()){
                p.addPhone(phone);
            }
                      
            em.getTransaction();
            em.persist(p);
            em.getTransaction().commit();
            
            return new PersonDTO(p);
        } finally {
            em.close();
        }
    }
    
    //edit a person
    public PersonDTO editPerson(PersonDTO pDTO){
        EntityManager em = emf.createEntityManager();
        
        Person person = em.find(Person.class, pDTO.getId());
        
        try{
            person.setFirstName(pDTO.getfName());
            person.setLastName(pDTO.getlName());
            person.setEmail(pDTO.getEmail());
            person.setStreet(pDTO.getStreet());
            person.setCity(pDTO.getCity());
            person.setZip(pDTO.getZip());
            person.setPhone(pDTO.getPhones().get(0).getNumber());
            
            return pDTO;
        } finally {
            em.close();
        }
    }
    
    //delete a person
    /*
    public String deletePerson(long id){
        EntityManager em = emf.createEntityManager();
        try{
            Query query = em.createQuery("DELETE p FROM Person p WHERE p.id = :id");
            
            return 
        } finally {
            em.close();
        }
    }
     */

}
