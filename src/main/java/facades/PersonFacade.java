//package facades;
//
//import entities.RenameMe;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.Query;
//
///**
// *
// * Rename Class to a relevant name Add add relevant facade methods
// */
//public class PersonFacade {
//
//    private static PersonFacade instance;
//    private static EntityManagerFactory emf;
//
//    //Private Constructor to ensure Singleton
//    private PersonFacade() {
//    }
//
//    /**
//     *
//     * @param _emf
//     * @return an instance of this facade class.
//     */
//    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
//        if (instance == null) {
//            emf = _emf;
//            instance = new PersonFacade();
//        }
//        return instance;
//    }
//
//    private EntityManager getEntityManager() {
//        return emf.createEntityManager();
//    }
//
//    //TODO Remove/Change this before use
//    public PersonDTO getPerson(long id) {
//        EntityManager em = emf.createEntityManager();
//        try {
//            Query query = em.createQuery("SELECT p from FROM Person p where id = :id", Person.class);
//            query.setParameter("id", id);
//            Person person = (Person) query.getSingleResult();
//            return new PersonDTO(person);
//        } finally {
//            em.close();
//        }
//
//    }
//
//    public List<PersonDTO> getPersonsByHobby(String name) {
//        EntityManager em = emf.createEntityManager();
//        try {
//            Query query = em.createQuery("SELECT p FROM Person p INNER JOIN p.hobbies h on h.name = :name");
//            query.setParameter("name", name);
//            List<Person> persons = (Person) query.getResultList();
//            return new PersonDTO(persons);
//        } finally {
//            em.close();
//        }
//    }
//    
//       public List<PersonDTO> getPersonsByZip(String zip) {
//        EntityManager em = emf.createEntityManager();
//        try {
//            Query query = em.createQuery("SELECT p FROM Person p JOIN p.address a ");
//            query.setParameter("zip", zip);
//            List<Person> persons = (Person) query.getResultList();
//            return new PersonDTO(persons);
//        } finally {
//            em.close();
//        }
//    }
//
//}
