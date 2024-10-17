package at.technikum_wien.service;

import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;

public class HelloController {

    //GET /hello
        public Response sayHello()
        {
            return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, "Server says hello!");

        }
    //GET /hello(:name
        public Response sayHelloBack(String name)
        {
            return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, "Server says hello to " + name);
        }




}
