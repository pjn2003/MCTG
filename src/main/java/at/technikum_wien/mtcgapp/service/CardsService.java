package at.technikum_wien.mtcgapp.service;

import at.technikum_wien.mtcgapp.controller.CardsController;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CardsService implements Service {

    private final CardsController controller;


    public CardsService()
    {
        this.controller = new CardsController();
    }
    @Override
    public Response handleRequest(Request request) {

        try {
            String json = request.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode n = mapper.readTree(json);

            if (request.getMethod() == Method.GET) {
                return this.controller.getUserCards(n.get("Username").asText());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return new Response(
                HttpStatus.BAD_REQUEST, ContentType.JSON, "[]"
        );
    }

}
