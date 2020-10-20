package facades;

import DTO.PersonDTO;
import entities.CityInfo;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;


public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
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

        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return renameMeCount;
        }finally{  

            em.close();
        }

    }

    public PersonDTO getPerson(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p from FROM Person p where id = :id", Person.class);
            query.setParameter("id", id);
            Person person = (Person) query.getSingleResult();
            //person skal returneres men der er ikke lavet constructor til det
            return new PersonDTO();
        } finally {
            em.close();
        }

    }

    public List<PersonDTO> getPersonsByHobby(String hobby) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p from FROM Person p where hobby = :hobby", Person.class);
            query.setParameter("hobby", hobby);
            Person person = (Person) query.getSingleResult();
            //person skal returneres men der er ikke lavet constructor til det

            return (List<PersonDTO>) new PersonDTO();
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getPersonsByCity(String city) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Person o WHERE city = :city", Person.class);
            query.setParameter("city", city);
            Person person = (Person) query.getSingleResult();

            return (List<PersonDTO>) new PersonDTO();
        } finally {
            em.close();
        }
    }

    public long getHobbyCount(String hobby) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT COUNT(r) FROM Person r WHERE hobby = :hobby");
            query.setParameter("hobby", hobby);
            long hobbyCount = (long) query.getSingleResult();

            return hobbyCount;
        } finally {
            em.close();
        }

    }

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

    public PersonDTO createPerson(String fName, String street, String city, String zip, String phone) {
        EntityManager em = emf.createEntityManager();
        try {
            Person p = new Person(fName, street, city, zip, phone);
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return new PersonDTO(p);
        } finally {
            em.close();
        }
    }
            
}
