package at.technikum_wien.mtcgapp.service;

import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.mtcgapp.controller.PackagesController;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;
import at.technikum_wien.mtcgapp.models.CardPackage;
import at.technikum_wien.mtcgapp.models.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class PackagesService implements Service {
    private final PackagesController controller;
    public PackagesService() {this.controller = new PackagesController();}

    @Override
    public Response handleRequest(Request request) {


        try {


            String json = request.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode n = mapper.readTree(json);

            if (request.getMethod() == Method.POST) {
                CardPackage p = new CardPackage(null,null);

                String pName = n.get("Name").asText();
                Integer[] cards = mapper.readValue(n.get("Cards").asText(), Integer[].class);
                p.setPackName(pName);
                p.setCardList(cards);
                return this.controller.createPack(p);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Returns all existing packs
        if (request.getMethod().toString().equals("GET")) {
            return this.controller.getPacks();
        }


        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }


}
