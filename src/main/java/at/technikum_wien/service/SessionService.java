package at.technikum_wien.service;

import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;
import at.technikum_wien.models.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Objects;

public class SessionService implements Service{
    private final SessionController sessionController;

    public SessionService(UserDummyData ud)
    {
        this.sessionController = new SessionController();
        for (User user : ud.getUsers())
        {
            this.sessionController.addToDummyData(user);
        }
    }
    public SessionService()
    {
        this.sessionController = new SessionController();
    }
    @Override
    public Response handleRequest(Request request) {

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
                    return this.sessionController.login(uData.getUsername(), uData.getPassword());
                }




            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



        if (request.getMethod() == Method.POST && request.getPathParts().size() > 2) {
            return this.sessionController.login(request.getPathParts().get(1),request.getPathParts().get(2));
        }


        return new Response(
                HttpStatus.BAD_REQUEST, ContentType.JSON, "[]"
        );
    }

}
