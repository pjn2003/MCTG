package technikum_wien.mtcgapp.service;

import technikum_wien.httpserver.http.ContentType;
import technikum_wien.httpserver.http.HttpStatus;
import technikum_wien.httpserver.http.Method;
import technikum_wien.httpserver.server.Request;
import technikum_wien.httpserver.server.Response;
import technikum_wien.httpserver.server.Service;
import technikum_wien.mtcgapp.controller.DeckControllerTestTest;

public class DeckServiceTest implements Service {

    private final DeckControllerTestTest controller;

    public DeckServiceTest()
    {
        this.controller = new DeckControllerTestTest();
    }

    @Override
    public Response handleRequest(Request request) {

        //Gets a specific user's deck, Usage: /String username
        if (request.getMethod() == Method.GET && request.getPathParts().size() == 2)
        {
            return this.controller.getUserDeck(request.getPathParts().get(1));
        } //Creates a deck for a specific user, Usage: /String username/int cardID/int cardID/..., must be 4 cards
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
