package facades;

import com.sun.jndi.url.rmi.rmiURLContext;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.ArrayList;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    private static Person p1 = new Person("Sebastian", "Godsk", "email@email.com");
    private static Person p2 = new Person("Sumit", "Dey", "Taarstrup@email.dk");

    private static Hobby hobby1 = new Hobby("fodbold", "spark til bold");
    private static Hobby hobby2 = new Hobby("håndbold", "kast med bold");

    private static CityInfo cityInfo1 = new CityInfo("2920", "charlottenlund");
    private static CityInfo cityInfo2 = new CityInfo("2820", "gentofte");
    private static CityInfo cityInfo3 = new CityInfo("2900", "hellerrup");

    private static Address address1 = new Address("charlottenlundvej 23");
    private static Address address2 = new Address("gentoftevej 32");
    private static Address address3 = new Address("hellerupvej 232");

    private static Phone phone1 = new Phone("28939700", "mobil");
    private static Phone phone2 = new Phone("48474848", "hjemmetelefon");
    private static Phone phone3 = new Phone("48484848", "hjemmetelefon");

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
        EntityManager em = emf.createEntityManager();
        
        address1.setCityInfo(cityInfo1);
        p1.addAdress(address1);
        p1.addHobby(hobby1);
        p1.addPhone(phone1);

        address2.setCityInfo(cityInfo2);
        p2.addAdress(address2);
        p2.addHobby(hobby1);
        p2.addPhone(phone2);

        address3.setCityInfo(cityInfo3);

        try {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM PHONE").executeUpdate();
            em.createNativeQuery("DELETE FROM HOBBY_PERSON").executeUpdate();  
            em.createNativeQuery("DELETE FROM HOBBY").executeUpdate();
            em.createNativeQuery("DELETE FROM PERSON").executeUpdate();
            em.createNativeQuery("DELETE FROM ADDRESS").executeUpdate();
            em.createNativeQuery("DELETE FROM CITYINFO").executeUpdate();
            
            
            
                    

            em.persist(cityInfo1);
            em.persist(address1);
            em.persist(p1);

            em.persist(cityInfo2);
            em.persist(address2);
            em.persist(p2);

            em.persist(cityInfo3);
            em.persist(address3);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getAllPersonsByHobby() throws PersonNotFoundException, MissingInputException {
        PersonsDTO p = facade.getAllPersonsByHobby("fodbold");
        int exp = 2;
        assertEquals(exp, p.getAll().size(), "Expects the size of 2");
        assertThat(p.getAll(), everyItem(hasProperty("fName")));
    }

    @Test
    public void getAllPersonsByCity() throws PersonNotFoundException, MissingInputException {
        PersonsDTO p = facade.getAllPersonsByCity("charlottenlund");
        int exp = 1;
        assertEquals(exp, p.getAll().size(), "Expects the size of 1");
        assertThat(p.getAll(), everyItem(hasProperty("email")));
    }

    @Test
    public void getPeopleCountByHobby() throws PersonNotFoundException, MissingInputException {
        long count = facade.getPeopleCountByHobby("fodbold");
        int exp = 2;
        assertEquals(exp, count);
    }

    @Test
    public void getAllZipCodes() {
        List<String> zip = facade.getAllZipCodes();
        int exp = 3;
        assertThat(zip, hasSize(3));
    }

    @Test
    public void getPersonByPhone() throws PersonNotFoundException, MissingInputException {
        PersonDTO p = facade.getPersonByPhone("28939700");
        String expFname = "Sebastian";
        assertEquals(p.getfName(), expFname);
    }

    @Test
    public void createNewPerson() throws PersonNotFoundException, MissingInputException {
        Person p3 = new Person("jens", "ole", "email@hjælp.dk");
        p3.addAdress(address3);
        p3.addHobby(hobby2);
        p3.addPhone(phone3);

        PersonDTO p = facade.createNewPerson(new PersonDTO(p3));
        PersonsDTO persons = facade.getAllPersonsByHobby("håndbold");
        int exp = 1;
        assertEquals(exp, persons.getAll().size(), "Expects the size of 1");
        assertThat(persons.getAll(), everyItem(hasProperty("fName")));
    }

    @Test
    public void editPerson() throws PersonNotFoundException {
        PersonDTO p = new PersonDTO(p2);
        p.setEmail("editmail@email.dk");
        PersonDTO pDTO = facade.editPerson(p);
        String expEmail = "editmail@email.dk";
        assertEquals(pDTO.getEmail(), expEmail, "Expects the email: editmail@email.dk");
    }

    // Negative tests
    @Test
    public void editPersonError() throws PersonNotFoundException {
        long fakeId = 5;
        try {
            p1.setId(fakeId);
            PersonDTO personDTO = new PersonDTO(p1);
            personDTO.setfName("Christian");
            PersonDTO personEdited = facade.editPerson(personDTO);
            assert false;
        } catch (PersonNotFoundException e) {
            assert true;
        }
    }

    @Test
    public void getAllPersonsByHobbyError() throws PersonNotFoundException, MissingInputException {
        String fakeCity = "CityThatDoesNotExist";
        try {
            PersonsDTO personsDTO = facade.getAllPersonsByCity(fakeCity);
        } catch (PersonNotFoundException ex) {
            assertThat(ex.getMessage(), is("No persons was found the given city"));
        }
    }

    @Test
    public void testGetAllPersonsByCityError() throws PersonNotFoundException, MissingInputException {
        String fakeHobby = "HobbyThatDoesNotExist";
        try {
            PersonsDTO personsDTO = facade.getAllPersonsByHobby(fakeHobby);
        } catch (PersonNotFoundException ex) {
            assertThat(ex.getMessage(), is("Could not find any persons with the given hobby"));
        }
    }
}
