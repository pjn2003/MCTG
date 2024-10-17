package at.technikum_wien.service;

import at.technikum_wien.controller.TransactionController;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;


public class TransactionService implements Service {

    TransactionController controller;

    public TransactionService() {
        this.controller = new TransactionController()  ;
    }

    @Override
    public Response handleRequest(Request request) {

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
