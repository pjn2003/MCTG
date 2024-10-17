package at.technikum_wien.service;

import at.technikum_wien.controller.DeckController;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;

public class DeckService implements Service {

    private final DeckController controller;

    public DeckService()
    {
        this.controller = new DeckController();
    }

    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.GET && request.getPathParts().size() == 2)
        {
            return this.controller.getUserDeck(request.getPathParts().get(1));
        }
        else if (request.getMethod() == Method.PUT && request.getPathParts().size() == 6)
        {
            return this.controller.createUserDeck(request.getPathParts().get(1),
                    Integer.parseInt(request.getPathParts().get(2)),
                    Integer.parseInt(request.getPathParts().get(3)),
                    Integer.parseInt(request.getPathParts().get(4)),
                    Integer.parseInt(request.getPathParts().get(5))
                    );
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}