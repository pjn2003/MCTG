package at.technikum_wien.mtcgapp.controller;

import at.technikum_wien.mtcgapp.dummydata.DummyCards;
import at.technikum_wien.mtcgapp.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.mtcgapp.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DeckController extends Controller {

    private final UserDummyData dummyData;
    private final DummyCards dummyCards;
    public DeckController() {
        this.dummyData = new UserDummyData(false);
        this.dummyCards = new DummyCards();
    }
    ObjectMapper objectMapper = new ObjectMapper();

    public Response createUserDeck(String uname, Integer[] cards)
    {

        if (cards.length != 4)
        {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    "{ \"message\" : \"Invalid deck size\" }"
            );
        }

        try {
        Connection con = connect();
        String query = "SELECT * FROM mtcguser WHERE username = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, uname);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {

            Integer[] uCards = (Integer[])rs.getArray("cards").getArray();
            Integer[] uSCards = (Integer[])rs.getArray("cardsinstore").getArray();

            ArrayList<Integer> cList = new ArrayList<>();
            ArrayList<Integer> sList = new ArrayList<>();
            cList.addAll(List.of(uCards));
            sList.addAll(List.of(uSCards));
            for (int i = 0; i < cards.length; i++) {

                //If the user has a card up for trade, remove it from the list of "owned" cards
                //If the same card is owned multiple times, it will skip over the first one
                if (sList.contains(cards[i])) {
                    cList.remove(Integer.valueOf(cards[i]));
                    sList.remove(Integer.valueOf(cards[i]));
                }

                if (!cList.contains(cards[i])) {
                    return new Response(
                            HttpStatus.FORBIDDEN,
                            ContentType.JSON,
                            "{ \"message\" : \"That user does not own all of these cards!\" }"
                    );
                }


            }

            Array cArray = con.createArrayOf("integer", cards);
            query = "UPDATE mtcguser SET deck = ? WHERE username = ?";
            ps = con.prepareStatement(query);
            ps.setArray(1, cArray);
            ps.setString(2, uname);
            ps.executeUpdate();
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    "{ \"message\" : \"Deck updated!\" }"
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



        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
