package at.technikum_wien.mtcgapp.service;

import at.technikum_wien.mtcgapp.controller.UserController;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;
import at.technikum_wien.mtcgapp.models.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

public class UserService implements Service {

@Getter private final UserController userController;
public UserService()
{
    this.userController = new UserController();
}

@Override
    public Response handleRequest(Request request) {
    System.out.println("Request received: " + request + " on thread: " + Thread.currentThread());

    if (Objects.equals(request.getHeaderMap().getHeader("Content-Type"), "application/json"))
    {//Creates user from JSON object (CURL Script)
        try {

            String json = request.getBody();
            //System.out.println(json);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode n = mapper.readTree(json);

            if (request.getMethod() == Method.POST)
            {
                User uData = new User(n.get("Username").asText(), n.get("Password").asText());
                //System.out.println(uData.getUsername() + " " + uData.getPassword());
                return this.userController.createUser(uData.getUsername(), uData.getPassword());
            }
            else if (request.getMethod() == Method.GET)
            {
                return this.userController.getUser(n.get("Username").asText());
            }




        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Returns user stats, Usage: /stats/String username - For this, /users does not need to be added
    if (request.getMethod() == Method.GET && request.getPathParts().get(0).equals("stats") && request.getPathParts().size() ==2)
    {
            return this.userController.getUserStats(request.getPathParts().get(1));
    }//Regular /users responses
    else if (request.getPathParts().get(0).equals("users"))
    {
        //Returns a user, Usage: /String username
        if (request.getMethod() == Method.GET && request.getPathParts().size() > 1)
        {
            return this.userController.getUser(request.getPathParts().get(1));
        }//Creates a user, Usage: /String username/String password
        else if (request.getMethod() == Method.POST && request.getPathParts().size() > 2)
        {
            return this.userController.createUser(request.getPathParts().get(1),request.getPathParts().get(2));
        }//Changes a user's bio, Usage: /String username/String newBio
        else if (request.getMethod() == Method.PUT && request.getPathParts().size() > 2)
        {
            return this.userController.editUser(request.getPathParts().get(1),request.getPathParts().get(2));
        }
    }



    return new Response(
            HttpStatus.BAD_REQUEST, ContentType.JSON, "[]"
    );
}


}
