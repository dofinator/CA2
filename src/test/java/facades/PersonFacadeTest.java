package facades;

import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private Person p1 = new Person("Sebastian", "Godsk", "email@email.com");
    private Person p2 = new Person("Sumit", "Dey", "Taarstrup@email.dk");
    private Hobby fodbold = new Hobby("fodbold", "spark til bold");
    private Hobby håndbold = new Hobby("håndbold", "kast med bold");
    private CityInfo charlottenlund = new CityInfo("2920", "charlottenlund");
    private Address hovmarksvej = new Address("hovmarksvej", "10, st.tv");
    private Address skovvej = new Address("skovvej", "14, st.th");
    private Phone phone1 = new Phone("44444444", "mobil");
    private Phone phone2 = new Phone("44444444", "hjemmetelefon");
    

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //em.createNativeQuery("DROP DATABASE startcode_test").executeUpdate();
            //em.createNativeQuery("alter table CAR AUTO_INCREMENT = 1").executeUpdate();

            em.persist(p1);
            em.persist(p2);
            em.persist(fodbold);
            p1.addHobby(fodbold);
            p2.addHobby(fodbold);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testGetAllPersonsByHobby() {
         
        PersonsDTO ost = facade.getAllPersonsByHobby("fodbold");
        int exp = 2;

        assertEquals(ost.getAll().size(), exp);
    }

}
