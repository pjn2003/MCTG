package at.technikum_wien.service;

import at.technikum_wien.controller.UserController;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;
import at.technikum_wien.models.User;
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

    //If request is a JSON string, parse it
    if (Objects.equals(request.getHeaderMap().getHeader("Content-Type"), "application/json"))
    {
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




        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    if (request.getMethod() == Method.GET && request.getPathParts().get(0).equals("stats") && request.getPathParts().size() ==2)
    {
            return this.userController.getUserStats(request.getPathParts().get(1));
    }
    else if (request.getPathParts().get(0).equals("users"))
    {
        if (request.getMethod() == Method.GET && request.getPathParts().size() > 1)
        {
            return this.userController.getUser(request.getPathParts().get(1));
        }
        else if (request.getMethod() == Method.POST && request.getPathParts().size() > 2)
        {
            return this.userController.createUser(request.getPathParts().get(1),request.getPathParts().get(2));
        }
        else if (request.getMethod() == Method.PUT && request.getPathParts().size() > 2)
        {  // username / new bio
            return this.userController.editUser(request.getPathParts().get(1),request.getPathParts().get(2));
        }
    }



    return new Response(
            HttpStatus.BAD_REQUEST, ContentType.JSON, "[]"
    );
}


}
