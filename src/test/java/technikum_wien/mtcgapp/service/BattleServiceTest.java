package technikum_wien.mtcgapp.service;

import technikum_wien.httpserver.http.ContentType;
import technikum_wien.httpserver.http.HttpStatus;
import technikum_wien.httpserver.http.Method;
import technikum_wien.httpserver.server.Request;
import technikum_wien.httpserver.server.Response;
import technikum_wien.httpserver.server.Service;
import technikum_wien.mtcgapp.controller.BattleControllerTest;

public class BattleServiceTest implements Service {

    private final BattleControllerTest controller;

    public BattleServiceTest()
    {
        this.controller = new BattleControllerTest();
    }

    @Override
    public Response handleRequest(Request request) {

        //Placeholder, simply returns a message
        if (request.getMethod() == Method.POST) {
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    "{ \"message\" : \"Entered battle!\" }"
            );
        }


        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }

}
