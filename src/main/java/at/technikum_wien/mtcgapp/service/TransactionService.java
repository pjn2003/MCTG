package at.technikum_wien.mtcgapp.service;

import at.technikum_wien.mtcgapp.controller.TransactionController;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;
import at.technikum_wien.mtcgapp.models.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class TransactionService implements Service {

    TransactionController controller;

    public TransactionService() {
        this.controller = new TransactionController()  ;
    }

    @Override
    public Response handleRequest(Request request) {

        try {
            String json = request.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode n = mapper.readTree(json);

            if (request.getMethod() == Method.POST) {
                //User uData = new User(n.get("Username").asText(), n.get("Password").asText());
                //System.out.println(uData.getUsername() + " " + uData.getPassword());
                return this.controller.purchasePack(n.get("PackName").asText(), n.get("Username").asText());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //Purchases a specific pack, Usage: /packages/String packName/String userName
        if (request.getMethod()== Method.POST) {
            if (request.getPathParts().size() > 3 && request.getPathParts().get(1).equals("packages")) {
                //System.out.println(request.getPathParts().get(2)+ " " + request.getPathParts().get(3));
                return this.controller.purchasePack(request.getPathParts().get(2), request.getPathParts().get(3));
            }
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
