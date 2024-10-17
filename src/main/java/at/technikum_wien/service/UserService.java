package at.technikum_wien.service;

import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;

public class UserService implements Service {
private final UserController userController;
public UserService()
{
    this.userController = new UserController();
}

@Override
    public Response handleRequest(Request request) {

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
    return new Response(
            HttpStatus.BAD_REQUEST, ContentType.JSON, "[]"
    );
}


}
