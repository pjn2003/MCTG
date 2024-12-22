package technikum_wien.mtcgapp.service;

import technikum_wien.httpserver.http.ContentType;
import technikum_wien.httpserver.http.HttpStatus;
import technikum_wien.httpserver.http.Method;
import technikum_wien.httpserver.server.Request;
import technikum_wien.httpserver.server.Response;
import technikum_wien.httpserver.server.Service;
import technikum_wien.mtcgapp.controller.CardsControllerTest;

public class CardsServiceTest implements Service {

    private final CardsControllerTest controller;


    public CardsServiceTest()
    {
        this.controller = new CardsControllerTest();
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
