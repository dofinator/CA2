package facades;

import DTO.PersonDTO;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
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

    //TODO Remove/Change this before use
    public long getPersonCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM Person r").getSingleResult();
            return renameMeCount;
        } finally {
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
            Query query = em.createQuery("SELECT p from FROM Person p where id = :id", Person.class);
            query.setParameter("hobby", hobby);
            Person person = (Person) query.getSingleResult();
            //person skal returneres men der er ikke lavet constructor til det

            return (List<PersonDTO>) new PersonDTO();
        } finally {
            em.close();
        }
    }

}
