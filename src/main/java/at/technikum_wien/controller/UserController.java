package at.technikum_wien.controller;

import at.technikum_wien.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;

public class UserController extends Controller {
    private UserDummyData dummyData;
    public UserController()
    {
        this.dummyData = new UserDummyData(false);
    }

    //GET user with username
    public Response getUser(String uname)
    {
        try {

            User userData = this.dummyData.getUser(uname);
            String userJson = this.getObjectMapper().writeValueAsString(userData);
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    userJson
            );

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }
    //POST user
    public Response createUser(String uname, String password)
    {
        try {
            User userData = new User(uname, password);
            if(this.dummyData.getUser(uname) != null)
            {
                return new Response(
                        HttpStatus.CONFLICT,
                        ContentType.JSON,
                        "{ \"message\" : \"User with same username already registered.\" }"
                );
            }
            else
            {
                this.dummyData.addUser(userData);
                return new Response(
                        HttpStatus.CREATED,
                        ContentType.JSON,
                        "{ \"message\" : \"User successfully created\" }"
                );
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }


    public Response editUser(String uname, String newBio)
    {
        try {
            this.dummyData.editUser(uname, newBio);
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    "{ \"message\" : \"User successfully updated.\" }"
            );
        }
        catch(Exception e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.NOT_FOUND,
                    ContentType.JSON,
                    "{ \"message\" : \"User not found.\" }"
            );
        }
    }

    public Response getUserStats(String uname)
    {
        try {

            if(this.dummyData.getUser(uname) != null)
            {
                User userData = this.dummyData.getUser(uname);
                String result = "\nELO Score: "+ userData.getElo() + "\nWins: " + userData.getWins() + "\nLosses: " + userData.getLosses() + "\n";
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{ \"message\" : \"User stats:\"\n%s }".formatted(result)
                );
            }
            else
            {

                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ \"message\" : \"User not found\" }"
                );
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }


}
