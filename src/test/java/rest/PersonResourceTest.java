package rest;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonResourceTest {
    
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    
    private static Person p1, p2, p3;
    private static Hobby hobby1, hobby2;
    private static Address address1, address2, address3;
    private static Phone phone1, phone2, phone3;
    private static CityInfo cityInfo1, cityInfo2, cityInfo3;
    
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    
    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }
    
    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
        
        EntityManager em = emf.createEntityManager();
        
        p1 = new Person("Sebastian", "Hansen", "email@hhaa.dk");
        p2 = new Person("Lukas", "Bang", "yalla@habibi.dut");
        p3 = new Person("add", "add", "add@habibi.com");
        
        hobby1 = new Hobby("fodbold", "bold med fod");
        hobby2 = new Hobby("håndbold", "bold med hånd");
        
        address1 = new Address("lyngbyvej 22");
        address2 = new Address("fredensborgvej 244");
        address3 = new Address("hillerødvej 2223");
        
        phone1 = new Phone("12345612", "mobil");
        phone2 = new Phone("65884774", "mobil");
        phone3 = new Phone("28456685", "mobil");
        
        cityInfo1 = new CityInfo("2800", "Lyngby");
        cityInfo2 = new CityInfo("3480", "Fredensborg");
        cityInfo3 = new CityInfo("3400", "Hillerød");
        
        address1.setCityInfo(cityInfo1);
        address2.setCityInfo(cityInfo2);
        address3.setCityInfo(cityInfo3);
        
        p1.addAdress(address1);
        p1.addHobby(hobby1);
        p1.addPhone(phone1);
        
        p2.addAdress(address2);
        p2.addHobby(hobby1);
        p2.addPhone(phone2);
        
        try {
            em.getTransaction().begin();
            
            em.persist(address1);
            em.persist(address2);
            em.persist(address3);
            em.persist(cityInfo1);
            em.persist(cityInfo2);
            em.persist(cityInfo3);
            
            em.persist(p1);
            em.persist(p2);
            
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        
    }
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/person/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }
    
    @Test
    public void testCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/person/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(2));
    }
    
    @Test
    public void testPersonByHobby() throws PersonNotFoundException {
        
        given()
                .contentType("application/json")
                .get("/person/hobby/fodbold").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(1));
        
    }
    
    @Test
    public void testGetAllPersonsByCity() throws PersonNotFoundException {
        given()
                .contentType("application/json")
                .get("/person/persons/Lyngby").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(1));
        
    }
//

    @Test
    public void getPeopleCountByHobby() throws PersonNotFoundException {
        given()
                .contentType("application/json")
                .get("/person/peoplecount/fodbold").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(2));
        
    }
//

    @Test
    public void getAllZipCodes() throws PersonNotFoundException {
        given()
                .contentType("application/json")
                .get("/person/zip").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(3));
        
    }
    
//    @Test
//    public void addPerson() throws PersonNotFoundException {
//        p3.addAdress(address3);
//        p3.addHobby(hobby2);
//        p3.addPhone(phone3);
//        given()
//                .contentType(ContentType.JSON)
//                .body(new PersonDTO(p3))
//                .when()
//                .post("person/add")
//                .then()
//                .body("fName", equalTo("add"))
//                .body("lName", equalTo("add"))
//                .body("email", equalTo("add@habibi.com"));
//        
//    }

//    @Test
//    public void testEditPerson() {
//        Person person = new Person("Sumit", "Dey", "test@gmail.com");
//        Hobby hobby = new Hobby("fodbold", "bold med fod jalla");
//        Address address = new Address("lyngbyvej");
//        Phone phone = new Phone("1234561243", "mobil");
//        CityInfo ci = new CityInfo("2800", "Lyngby");
//        long id = 1;
//        person.setId(id);
//        person.addAdress(address);
//        person.addHobby(hobby);
//        person.addPhone(phone);
//        address.setCityInfo(ci);
//
//        PersonDTO personDTO = new PersonDTO(person);
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(personDTO)
//                .when()
//                .put("person/edit/" + id)
//                .then()
//                .body("fName", equalTo("Sumit"))
//                .body("lName", equalTo("Dey"))
//                .body("email", equalTo("test@gmail.com"));
//
//    }
}
