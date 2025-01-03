package at.technikum_wien.mtcgapp.service;

import at.technikum_wien.mtcgapp.controller.HelloController;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;

public class HelloService implements Service {
    private final HelloController controller;
    public HelloService() {this.controller = new HelloController();}

    @Override
    public Response handleRequest(Request request) {

        //Says hello to someone
        if (request.getMethod().toString().equals("GET") && request.getPathParts().size() > 1) {
            return this.controller.sayHelloBack(request.getPathParts().get(1));
        } //Says hello
        else if (request.getMethod().toString().equals("GET"))
        {
            return this.controller.sayHello();
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }


}
