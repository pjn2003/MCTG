package at.technikum_wien.service;

import at.technikum_wien.controller.Controller;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;

public class UserController extends Controller {
    @Getter private UserDummyData dummyData;
    public UserController()
    {
        this.dummyData = new UserDummyData();
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


}
