package facades;

import entities.Person;
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
@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private Person p1 = new Person("Sebastian", "Lyngbyvej", "Lyngby", "2800", "12345678");
    private Person p2 = new Person("Sumit", "Taarstrupvej", "Taarstrup", "2630", "87654321");

    private List<String> hobbyList1 = new ArrayList<>();
    private List<String> hobbyList2 = new ArrayList<>();
    
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
            em.createNamedQuery("RenameMe.deleteAllRows").executeUpdate();
            
            hobbyList1.add("Fodbold");
            hobbyList1.add("Træning");
            hobbyList2.add("Bajer");
            hobbyList2.add("Programmering");
            
            p1.setHobbies(hobbyList1);
            p2.setHobbies(hobbyList2);
            
            em.persist(p1);
            em.persist(p2);

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
    public void testAFacadeMethod() {
        assertEquals(2, facade.getPersonCount(), "Expects two rows in the database");
    }
    
    @Test
    public void testGetPerson(){
        assertEquals(p1, facade.getPerson(1));
    }
    
    @Test
    public void testPersonsByHobby(){
        assertTrue(facade.getPersonsByHobby("Fodbold").contains(p1));
    }
    
    @Test
    public void testPersonsByCity(){
        assertTrue(facade.getPersonsByCity("Taarstrup").contains(p2));
    }
    
    @Test
    public void testHobbyCount(){
        assertEquals(1, facade.getHobbyCount("Fodbold"));
    }
}
