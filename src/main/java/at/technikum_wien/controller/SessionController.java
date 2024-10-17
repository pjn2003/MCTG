package at.technikum_wien.controller;

import at.technikum_wien.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;

public class SessionController extends Controller{

    private UserDummyData dummyData;
    public SessionController()
    {
        this.dummyData = new UserDummyData(true);
    }

    public Response login(String username, String password)
    {
        try {
            if (this.dummyData.getUser(username)!=null && this.dummyData.getUser(username).getPassword().equals(password))
            {
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        username+"-mtcgToken"
                );
            }
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.JSON,
                    "Invalid username or password"
            );

        }
        catch (Exception e)
        {
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "[]"
            );
        }
    }


}
