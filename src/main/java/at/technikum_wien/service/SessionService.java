package at.technikum_wien.service;

import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SessionService implements Service{
    private final SessionController sessionController;
    public SessionService()
    {
        this.sessionController = new SessionController();
    }
    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.POST && request.getPathParts().size() > 2) {
            return this.sessionController.login(request.getPathParts().get(1),request.getPathParts().get(2));
        }


        return new Response(
                HttpStatus.BAD_REQUEST, ContentType.JSON, "[]"
        );
    }

}
