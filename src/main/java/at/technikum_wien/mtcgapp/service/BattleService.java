package at.technikum_wien.mtcgapp.service;

import at.technikum_wien.mtcgapp.controller.BattleController;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;

public class BattleService implements Service {

    private final BattleController controller;

    public BattleService()
    {
        this.controller = new BattleController();
    }

    @Override
    public Response handleRequest(Request request) {

        //Placeholder, simply returns a message
        if (request.getMethod() == Method.POST) {
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    "{ \"message\" : \"Entered battle lobby!\" }"
            );
        }


        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }

}
