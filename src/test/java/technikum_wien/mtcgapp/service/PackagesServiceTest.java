package technikum_wien.mtcgapp.service;

import technikum_wien.httpserver.http.ContentType;
import technikum_wien.httpserver.http.HttpStatus;
import technikum_wien.httpserver.server.Request;
import technikum_wien.httpserver.server.Response;
import technikum_wien.httpserver.server.Service;
import technikum_wien.mtcgapp.controller.PackagesControllerTestTest;

import static java.lang.Integer.parseInt;

public class PackagesServiceTest implements Service {
    private final PackagesControllerTestTest controller;
    public PackagesServiceTest() {this.controller = new PackagesControllerTestTest();}

    @Override
    public Response handleRequest(Request request) {

        //Returns all existing packs
        if (request.getMethod().toString().equals("GET")) {
            return this.controller.getPacks();
        }//Creates a new pack, Usage: /String packName,/int cardID/..., amount of cards can be anything
        else if (request.getMethod().toString().equals("POST") && request.getPathParts().size() > 2)
        {
            Integer[] newPack = new Integer[request.getPathParts().size()-2];
            for (int i = 2; i < request.getPathParts().size(); i++)
            {
                newPack[i-2] = parseInt(request.getPathParts().get(i));
            }

            return this.controller.createPack(request.getPathParts().get(1),newPack);

        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }


}
