package facades;

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

        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return renameMeCount;
        } finally {

            em.close();
        }

    }

    //Get all persons with a given hobby
    //Get all phone numbers for a person living in a given city
    //get the number of people with a given hobby
    public long getHobbyCount(String hobby) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT h.id FROM Hobby h WHERE h.name = :hobby");
            query.setParameter("hobby", hobby);
            long hobbyId = (long) query.getSingleResult();

            Query query2 = em.createQuery("SELECT COUNT(hp) FROM HOBBY_PERSON hp WHERE hp.hobbies_ID = :hobbyId");
            query2.setParameter("hobbyId", hobbyId);
            long hobbyCount = (long) query2.getSingleResult();

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
    //edit a person
    //delete a person

}
