package technikum_wien.mtcgapp.controller;

import technikum_wien.httpserver.http.ContentType;
import technikum_wien.httpserver.http.HttpStatus;
import technikum_wien.httpserver.server.Response;
import technikum_wien.mtcgapp.dummydata.UserDummyData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SessionControllerTestTest extends ControllerTest {

    private UserDummyData dummyData;
    public SessionControllerTestTest()
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
