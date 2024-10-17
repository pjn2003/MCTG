package at.technikum_wien.mtcgapp.service;

import at.technikum_wien.mtcgapp.controller.CardsController;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;

public class CardsService implements Service {

    private final CardsController controller;


    public CardsService()
    {
        this.controller = new CardsController();
    }
    @Override
    public Response handleRequest(Request request) {

        //Gets cards of specific user, Usage: /String username
        if (request.getMethod() == Method.GET && request.getPathParts().size() == 2) {
            return this.controller.getUserCards(request.getPathParts().get(1));

        }


        return new Response(
                HttpStatus.BAD_REQUEST, ContentType.JSON, "[]"
        );
    }

}
