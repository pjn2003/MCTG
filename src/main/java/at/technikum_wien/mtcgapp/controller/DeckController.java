package at.technikum_wien.mtcgapp.controller;

import at.technikum_wien.mtcgapp.dummydata.DummyCards;
import at.technikum_wien.mtcgapp.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.mtcgapp.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DeckController extends Controller {

    private final UserDummyData dummyData;
    private final DummyCards dummyCards;
    public DeckController() {
        this.dummyData = new UserDummyData(false);
        this.dummyCards = new DummyCards();
    }
    ObjectMapper objectMapper = new ObjectMapper();

    public Response createUserDeck(String uname, Integer c1, Integer c2, Integer c3, Integer c4)
    {
        if (this.dummyData.getUser(uname) != null) {

            ArrayList<Integer> newDeck = new ArrayList<Integer>();
            newDeck.add(c1);
            newDeck.add(c2);
            newDeck.add(c3);
            newDeck.add(c4);
            this.dummyData.getUser(uname).setUserDeck(newDeck);



            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    "{ \"message\" : \"Deck created.\" }"

            );

        }




        return new Response(
            HttpStatus.NOT_FOUND,
            ContentType.JSON,
                "{ \"message\" : \"User not found\" }"
    );
    }

    public Response getUserDeck(String uname)
    {
        try {
            String result ="";

            Connection con = connect();
            String query = "SELECT * FROM mtcguser WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, uname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer[] c = (Integer[])rs.getArray("deck").getArray();
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
                        "{ \"message\" : \"User deck:\"\n%s }".formatted(result)
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
