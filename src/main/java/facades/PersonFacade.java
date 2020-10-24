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
import exceptions.PersonNotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class PersonFacade implements IPersonFacade {

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
    @Override
    public PersonsDTO getAllPersonsByHobby(String hobby) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Person p JOIN p.hobbies hobbies WHERE hobbies.name = :hobby", Person.class);
            query.setParameter("hobby", hobby);
            List<Person> personList = query.getResultList();

            return new PersonsDTO(personList);
        } finally {
            em.close();
        }

    }

    //Get the count of people with a given hobby 
    @Override
    public long getPeopleCountByHobby(String hobby) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT COUNT(p) FROM Person p JOIN p.hobbies hobbies WHERE hobbies.name = :hobby", Person.class);
            query.setParameter("hobby", hobby);
            long count = (long) query.getSingleResult();
            if (count < 1) {
                throw new PersonNotFoundException("No persons was found the given hobby");
            }
            return count;
        } finally {
            em.close();
        }
    }

    //Get all persons living in a given city
    public PersonsDTO getAllPersonsByCity(String city) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Person p WHERE p.address.cityInfo.city = :city");
            query.setParameter("city", city);
            List<Person> personList = query.getResultList();
            if (personList.size() < 1) {
                throw new PersonNotFoundException("No persons was found the given city");
            }
            return new PersonsDTO(personList);
        } finally {
            em.close();
        }
    }

    //Get the person given a phone number
    public PersonDTO getPersonByPhone(String phone) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Person p JOIN p.phones phones where phones.number = :phone");
            query.setParameter("phone", phone);
            Person person = (Person) query.getSingleResult();
            return new PersonDTO(person);
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
    public PersonDTO createNewPerson(PersonDTO personDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("SELECT c FROM CityInfo c WHERE c.zip = :givenZip");
            q.setParameter("givenZip", personDTO.getZip());

            CityInfo cityInfo = (CityInfo) q.getSingleResult();
            Person p = new Person(personDTO.getfName(), personDTO.getlName(), personDTO.getEmail());
            Address address = new Address(personDTO.getStreet());

            p.addAdress(address);
            p.getAddress().setCityInfo(cityInfo);

            for (HobbyDTO hobbyDTO : personDTO.getHobbies()) {
                p.addHobby(new Hobby(hobbyDTO.getName(), hobbyDTO.getDescription()));
            }
            for (PhoneDTO phoneDTO : personDTO.getPhones()) {
                p.addPhone(new Phone(phoneDTO.getNumber(), phoneDTO.getDescription()));
            }

            em.getTransaction().begin();
            em.persist(p);
            em.persist(address);
            em.getTransaction().commit();

            return new PersonDTO(p);
        } finally {
            em.close();
        }
    }

    //edit a person
    @Override
    public PersonDTO editPerson(PersonDTO pDTO) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, pDTO.getId());
        if (person == null) {
            throw new PersonNotFoundException("Could not edit the person with the provided id");
        }
        Query q = em.createQuery("SELECT c FROM CityInfo c WHERE c.zip = :givenZip");
        q.setParameter("givenZip", pDTO.getZip());
        CityInfo cityInfo = (CityInfo) q.getSingleResult();

        person.setFirstName(pDTO.getfName());
        person.setLastName(pDTO.getlName());
        person.setEmail(pDTO.getEmail());
        person.getAddress().setStreet(pDTO.getStreet());
        person.getAddress().setCityInfo(cityInfo);

//        int lastIndexOfHobbies = pDTO.getHobbies().size() - 1;
//        int lastIndexOfPhones = pDTO.getPhones().size() - 1;
//        Hobby newHobby = new Hobby(pDTO.getHobbies().get(lastIndexOfHobbies).getName(),
//                pDTO.getHobbies().get(lastIndexOfHobbies).getDescription());
//        person.addHobby(newHobby);
//        Phone newPhone = new Phone(pDTO.getPhones().get(lastIndexOfPhones).getNumber(),
//                pDTO.getPhones().get(lastIndexOfPhones).getDescription());
//        person.addPhone(newPhone);

        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();

            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    //delete a person
    public PersonDTO deletePerson(long id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
        if (person == null) {
            throw new PersonNotFoundException("The provided id does not belong to any person");
        }
        try {
            Query query = em.createQuery("DELETE p FROM Person p WHERE p.id = :id");
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

}
