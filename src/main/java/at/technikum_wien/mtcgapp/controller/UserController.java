package at.technikum_wien.mtcgapp.controller;

import at.technikum_wien.mtcgapp.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.mtcgapp.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserController extends Controller {



    //GET user with username
    public Response getUser(String uname)
    {
        try {



            Connection con = connect();
            String query = "SELECT * FROM mtcguser WHERE username=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, uname);
            ResultSet rs = ps.executeQuery();
            User userData;
            String userJson="";
            if(rs.next())
            {
                userData = new User(rs.getString("username"),rs.getInt("coins"),rs.getString("bio"),
                        rs.getInt("elo"),rs.getInt("wins"),
                        rs.getInt("losses"),rs.getBoolean("is_admin"), (Integer[])rs.getArray("cards").getArray(),
                        (Integer[])rs.getArray("deck").getArray());
                userData.setPassword("Hidden");
                userJson = this.getObjectMapper().writeValueAsString(userData);
            }
            else
            {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ \"message\" : \"User not found.\" }"
                );
            }

            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    userJson
            );

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }
    //POST user
    public Response createUser(String uname, String password)
    {
        try {
            User userData = new User(uname, password);
            if(true)
            {
                return new Response(
                        HttpStatus.CONFLICT,
                        ContentType.JSON,
                        "{ \"message\" : \"User with same username already registered.\" }"
                );
            }
            else
            {

                return new Response(
                        HttpStatus.CREATED,
                        ContentType.JSON,
                        "{ \"message\" : \"User successfully created\" }"
                );
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }


    public Response editUser(String uname, String newBio)
    {
        try {

            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    "{ \"message\" : \"User successfully updated.\" }"
            );
        }
        catch(Exception e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.NOT_FOUND,
                    ContentType.JSON,
                    "{ \"message\" : \"User not found.\" }"
            );
        }
    }

    public Response getUserStats(String uname)
    {
        try {

            if(true)
            {

                //String result = "\nELO Score: "+ userData.getElo() + "\nWins: " + userData.getWins() + "\nLosses: " + userData.getLosses() + "\n";
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{ \"message\" : \"User stats:\"\n%s }".formatted("amongus")
                );
            }
            else
            {

                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ \"message\" : \"User not found\" }"
                );
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }


}
