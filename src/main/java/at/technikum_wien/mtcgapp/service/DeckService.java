package at.technikum_wien.mtcgapp.service;

import at.technikum_wien.mtcgapp.controller.DeckController;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeckService implements Service {

    private final DeckController controller;

    public DeckService()
    {
        this.controller = new DeckController();
    }

    @Override
    public Response handleRequest(Request request) {

        try {
            String json = request.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode n = mapper.readTree(json);

            if (request.getMethod() == Method.GET) {
                return this.controller.getUserDeck(n.get("Username").asText());
            }
            else if (request.getMethod() == Method.PUT) {

                Integer[] cards = mapper.readValue(n.get("Cards").asText(), Integer[].class);

                return this.controller.createUserDeck(n.get("Username").asText(),cards);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
