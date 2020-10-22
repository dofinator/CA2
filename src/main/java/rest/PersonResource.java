package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getPersonCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Path("hobby/{hobby}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonsByHobby(@PathParam("hobby") String hobby) throws PersonNotFoundException {
        PersonsDTO personsDTOList = FACADE.getAllPersonsByHobby(hobby);

        return GSON.toJson(personsDTOList);
    }

    @Path("zip/{zip}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllZip(@PathParam("zip") String zip) {
        //List</*PersonDTO navn der passer til*/> allZip = FACADE.getAllZipCodes(zip);
        //return GSON.toJson(allZip);
        return "";
    }

    //Henter ALLE informationer om en person ud fra telefonnummer
    @Path("personphone/{phone}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByPhone(@PathParam("phone") String phone) throws PersonNotFoundException {
        PersonDTO person = FACADE.getPersonByPhone(phone);
        return GSON.toJson(person);

    }

    //Find på et bedre pathnavn
    @Path("persons/{city}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonsByCity(@PathParam("city") String city) throws PersonNotFoundException {
        PersonsDTO allPersons = FACADE.getAllPersonsByCity(city);
        return GSON.toJson(allPersons);

    }

    //Antallet af personer med den givne hobby
    @Path("peoplecount/{hobby}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPeopleCountByHobby(@PathParam("hobby") String hobby) throws PersonNotFoundException {
        long count = FACADE.getPeopleCountByHobby(hobby);
        return GSON.toJson("Amount of people with given hobby: " + count);

    }
    //Antallet af personer med den givne hobby

    @Path("zip")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllZipCode() {
        List<String> ZIPCodes = FACADE.getAllZipCodes();
        return GSON.toJson(ZIPCodes);

    }

    @Path("add")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(String person) {
        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        p = FACADE.createNewPerson(p);
        return Response.ok(p).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("edit/{id}")
    public Response editPerson(@PathParam("id") long id, String person) throws PersonNotFoundException{
        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        p.setId(id);
        PersonDTO personEdit = FACADE.editPerson(p);
        return Response.ok(personEdit).build();
    }
}
