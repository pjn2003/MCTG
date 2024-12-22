package technikum_wien.mtcgapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import technikum_wien.httpserver.http.ContentType;
import technikum_wien.httpserver.http.HttpStatus;
import technikum_wien.httpserver.http.Method;
import technikum_wien.httpserver.server.Request;
import technikum_wien.httpserver.server.Response;
import technikum_wien.httpserver.server.Service;
import technikum_wien.mtcgapp.controller.SessionControllerTestTest;
import technikum_wien.mtcgapp.models.User;

import java.io.IOException;
import java.util.Objects;

public class SessionServiceTest implements Service {
    private final SessionControllerTestTest sessionControllerTest;


    public SessionServiceTest()
    {
        this.sessionControllerTest = new SessionControllerTestTest();
    }

    @Override
    public Response handleRequest(Request request) {
        System.out.println("Session request received: " + request + " on thread: " + Thread.currentThread());
        if (Objects.equals(request.getHeaderMap().getHeader("Content-Type"), "application/json"))
        { //Logs in user using JSON object (CURL Script)
            try {

                String json = request.getBody();
                //System.out.println(json);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode n = mapper.readTree(json);

                if (request.getMethod() == Method.POST)
                {
                    User uData = new User(n.get("Username").asText(), n.get("Password").asText());
                    //System.out.println(uData.getUsername() + " " + uData.getPassword());
                    return this.sessionControllerTest.login(uData.getUsername(), uData.getPassword());
                }




            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        //Login using path parts, Usage: /String username/String password
        if (request.getMethod() == Method.POST && request.getPathParts().size() > 2) {
            return this.sessionControllerTest.login(request.getPathParts().get(1),request.getPathParts().get(2));
        }


        return new Response(
                HttpStatus.BAD_REQUEST, ContentType.JSON, "[]"
        );
    }

}
