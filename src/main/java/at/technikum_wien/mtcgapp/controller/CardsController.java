package at.technikum_wien.mtcgapp.controller;

import at.technikum_wien.mtcgapp.dummydata.DummyCards;
import at.technikum_wien.mtcgapp.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CardsController extends Controller{


    public Response getUserCards(String uname)
    {
        try {
            String result ="";

            Connection con = connect();
            String query = "SELECT * FROM mtcguser WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, uname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer[] c = (Integer[])rs.getArray("cards").getArray();
                for (Integer i : c) {
                    String cQuery = "SELECT * FROM cards WHERE id =?";
                    PreparedStatement cs = con.prepareStatement(cQuery);
                    cs.setInt(1, i);
                    ResultSet rs2 = cs.executeQuery();
                    if (rs2.next()) {
                        result += "Card no. " +i +": "+ rs2.getString("name") + "\n";
                    }
                    else
                    {
                        return new Response(
                                HttpStatus.FORBIDDEN,
                                ContentType.JSON,
                                "{ \"message\" : \"User owns a card that does not exist.\" }"
                        );
                    }



                }
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{ \"message\" : \"User cards:\"\n%s }".formatted(result)
                );
            }
            else
            {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ \"message\" : \"User not found.\" }"
                );
            }




        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


}
