package technikum_wien.mtcgapp.service;

import technikum_wien.httpserver.http.ContentType;
import technikum_wien.httpserver.http.HttpStatus;
import technikum_wien.httpserver.server.Request;
import technikum_wien.httpserver.server.Response;
import technikum_wien.httpserver.server.Service;
import technikum_wien.mtcgapp.controller.HelloController;

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
