package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
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
    private static Hobby fodbold = new Hobby("fodbold", "spark til bold");
    private static Hobby håndbold = new Hobby("håndbold", "kast med bold");
    private static CityInfo charlottenlund = new CityInfo("2920", "charlottenlund");
    private static CityInfo gentofte = new CityInfo("2820", "gentofte");
    private static CityInfo hellerrup = new CityInfo("2900", "hellerrup");
    private static Address hovmarksvej = new Address("hovmarksvej");
    private static Address skovvej = new Address("skovvej");
    private static Phone phone1 = new Phone("44444444", "mobil");
    private static Phone phone2 = new Phone("33333333", "hjemmetelefon");
    private static Phone phone3 = new Phone("22222222", "hjemmetelefon");

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNativeQuery("alter table PERSON AUTO_INCREMENT = 1").executeUpdate();
            hovmarksvej.setCityInfo(charlottenlund);
            skovvej.setCityInfo(charlottenlund);

            p1.addAdress(skovvej);
            p2.addAdress(hovmarksvej);
            p1.addHobby(fodbold);
            p2.addHobby(fodbold);
            p2.addPhone(phone3);
            p1.addPhone(phone1);
            em.persist(fodbold);
            em.persist(charlottenlund);
            em.persist(hovmarksvej);
            em.persist(skovvej);
            em.persist(gentofte);
            em.persist(hellerrup);

            em.persist(p1);
            em.persist(p2);

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
    public void testGetAllPersonsByHobby() throws PersonNotFoundException {
        PersonsDTO p = facade.getAllPersonsByHobby("fodbold");
        int exp = 3;
        assertEquals(exp, p.getAll().size(), "Expects the size of three");
        assertThat(p.getAll(), everyItem(hasProperty("fName")));
    }

    @Test
    public void testGetAllPersonsByCity() throws PersonNotFoundException {
        PersonsDTO p = facade.getAllPersonsByCity("charlottenlund");
        int exp = 2;
        assertEquals(exp, p.getAll().size(), "Expects the size of two");
        assertThat(p.getAll(), everyItem(hasProperty("email")));
    }

    @Test
    public void testGetPeopleCountByHobby() throws PersonNotFoundException {
        long count = facade.getPeopleCountByHobby("fodbold");
        int exp = 2;
        assertEquals(exp, count);
    }

    @Test
    public void testGetAllZipCodes() {
        List<String> zip = facade.getAllZipCodes();
        int exp = 3;
        assertThat(zip, hasSize(3));
    }

    @Test
    public void testGetPersonByPhone() throws PersonNotFoundException {
        PersonDTO p = facade.getPersonByPhone("44444444");
        String expFname = "Sebastian";
        assertEquals(p.getfName(), expFname);
    }

    @Test
    public void testCreateNewPerson() throws PersonNotFoundException {
        Person p3 = new Person("jens", "ole", "email@hjælp.dk");
        p3.addAdress(skovvej);
        p3.addHobby(fodbold);
        p3.addPhone(phone2);
        PersonDTO p = facade.createNewPerson(new PersonDTO(p3));
        PersonsDTO persons = facade.getAllPersonsByHobby("fodbold");
        int exp = 4;
        assertEquals(exp, persons.getAll().size(), "Expects the size of 4");
    }

    @Test
    public void testEditPerson() throws PersonNotFoundException {
        PersonDTO p = new PersonDTO(p2);
        p.setEmail("nyemail@email.dk");
        PersonDTO yalla = facade.editPerson(p);
        String expEmail = "nyemail@email.dk";
        assertEquals(yalla.getEmail(), expEmail, "Expects the email: nyemail@email.dk");
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
    public void testGetAllPersonsByHobbyError() throws PersonNotFoundException {
        String fakeCity = "CityThatDoesNotExist";
        try {
            PersonsDTO personsDTO = facade.getAllPersonsByCity(fakeCity);
        } catch (PersonNotFoundException ex) {
            assertThat(ex.getMessage(), is("No persons was found the given city"));
        }
    }
    
    @Test
    public void testGetAllPersonsByCityError() throws PersonNotFoundException {
        String fakeHobby = "HobbyThatDoesNotExist";
        try {
            PersonsDTO personsDTO = facade.getAllPersonsByHobby(fakeHobby);
        } catch (PersonNotFoundException ex) {
            assertThat(ex.getMessage(), is("Could not find any persons with the given hobby"));
        }
    }

}
