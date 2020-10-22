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
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1 = new Person("Sebastian", "Godsk", "email@email.com");
    private static Person p2 = new Person("Sumit", "Dey", "Taarstrup@email.dk");
    private static Hobby fodbold = new Hobby("fodbold", "spark til bold");
    private static Hobby håndbold = new Hobby("håndbold", "kast med bold");
    private static CityInfo charlottenlund = new CityInfo("2920", "charlottenlund");
    private static CityInfo gentofte = new CityInfo("2920", "gentofte");
    private static CityInfo hellerrup = new CityInfo("2900", "hellerrup");
    private static Address hovmarksvej = new Address("hovmarksvej", "10, st.tv");
    private static Address skovvej = new Address("skovvej", "14, st.th");
    private static Phone phone1 = new Phone("44444444", "mobil");
    private static Phone phone2 = new Phone("44444444", "hjemmetelefon");
    

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
         EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            
            hovmarksvej.setCityInfo(charlottenlund);
            skovvej.setCityInfo(charlottenlund);
            
            p1.addAdress(skovvej);
            p2.addAdress(hovmarksvej);
            p1.addHobby(fodbold);
            p2.addHobby(fodbold);
            
            em.persist(fodbold);
            em.persist(charlottenlund);
            em.persist(hovmarksvej);
            em.persist(skovvej);
            
            
            
            
            em.persist(p1);
            em.persist(p2);
            
            em.persist(gentofte);
            em.persist(hellerrup);
            
            
          
            

            em.getTransaction().commit();
            
            System.out.println("******************");
            System.out.println(facade.getAllPersonsByHobby("fodbold").getAll());
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
    public void testGetAllPersonsByHobby() {
         
        PersonsDTO p = facade.getAllPersonsByHobby("fodbold");
        int exp = 2;

        assertEquals(exp, p.getAll().size());
    }

    
    @Test
    public void testGetAllPersonsByCity(){
        PersonsDTO p = facade.getAllPersonsByCity("charlottenlund");
        int exp = 2;
        assertEquals(exp, p.getAll().size());
    }
    
    @Test
    public void testGetHobbyCount(){
        long count = facade.getHobbyCount("fodbold");
        int exp = 2;
        
        assertEquals(exp, count);
      
    }
    
    
    @Test
    public void testGetAllZipCodes(){
        List <String> zip = facade.getAllZipCodes();
        int exp = 3;
        
        assertEquals(exp, zip.size());
    }
    
    
    
}
