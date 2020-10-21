package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
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
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    @Path("hobby/{hobby}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonsByHobby(@PathParam("hobby") String hobby){
        PersonsDTO personsDTOList = FACADE.getAllPersonsByHobby(hobby);
        
        return GSON.toJson(personsDTOList);
    }
    
    @Path("{city}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPhonesByCity(@PathParam("city") String city){
        //List</*PersonDTO navn der passer til*/> phonesByCity= FACADE.getAllPhonesByCity(city);
        //return GSON.toJson(personByCity);
        return "";
    }
    
    @Path("hobbycount/{hobby}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getHobbyCount(@PathParam("hobby") String hobby){
        //List</*PersonDTO navn der passer til*/> hobbyCount = FACADE.getHobbyCount(hobby);
        //return GSON.toJson(hobbyCount);
        return "";
    }
    
    @Path("zip/{zip}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllZip(@PathParam("zip") String zip){
        //List</*PersonDTO navn der passer til*/> allZip = FACADE.getAllZipCodes(zip);
        //return GSON.toJson(allZip);
        return "";
    }
    
    //Henter ALLE informationer om en person ud fra telefonnummer
    @Path("personphone/{phone}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByPhone(@PathParam("phone") String phone){
        PersonDTO person = FACADE.getPersonByPhone(phone);
        return GSON.toJson(person);
       
    }
    //Antallet af personer med den givne hobby
    @Path("peoplecount/{hobby}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPeopleCountByHobby(@PathParam("hobby") String hobby){
        long count = FACADE.getPeopleCountByHobby(hobby);
        return GSON.toJson("Amount of people with given hobby" + count);
       
    }
    
    
    
    
    
    
}
