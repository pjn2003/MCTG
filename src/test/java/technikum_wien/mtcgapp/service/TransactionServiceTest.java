package technikum_wien.mtcgapp.service;

import technikum_wien.httpserver.http.ContentType;
import technikum_wien.httpserver.http.HttpStatus;
import technikum_wien.httpserver.http.Method;
import technikum_wien.httpserver.server.Request;
import technikum_wien.httpserver.server.Response;
import technikum_wien.httpserver.server.Service;
import technikum_wien.mtcgapp.controller.TransactionControllerTestTest;


public class TransactionServiceTest implements Service {

    TransactionControllerTestTest controller;

    public TransactionServiceTest() {
        this.controller = new TransactionControllerTestTest()  ;
    }

    @Override
    public Response handleRequest(Request request) {
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
