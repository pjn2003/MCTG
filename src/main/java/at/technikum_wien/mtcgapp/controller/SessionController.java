package at.technikum_wien.mtcgapp.controller;

import at.technikum_wien.mtcgapp.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionController extends Controller{

    private UserDummyData dummyData;
    public SessionController()
    {
        this.dummyData = new UserDummyData(true);
    }

    public Response login(String username, String password)
    {
        try {
            Connection con = connect();
            String sql = "SELECT * FROM mtcguser WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                Response res = new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        username+"-mtcgToken"
                );


                return res;
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
