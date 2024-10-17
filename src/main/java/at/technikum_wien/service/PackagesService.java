package at.technikum_wien.service;

import at.technikum_wien.controller.PackagesController;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;

import static java.lang.Integer.parseInt;

public class PackagesService implements Service {
    private final PackagesController controller;
    public PackagesService() {this.controller = new PackagesController();}

    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod().toString().equals("GET")) {
            return this.controller.getPacks();
        }
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
