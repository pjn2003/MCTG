package at.technikum_wien.controller;

import at.technikum_wien.dummydata.DummyCards;
import at.technikum_wien.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public Response getUserDeck(String uname){
        String result = "";
        try {


        if (this.dummyData.getUser(uname) != null) {

            if (this.dummyData.getUser(uname).getUserDeck().size() > 0) {

                User user = this.dummyData.getUser(uname);

                for(Integer id : user.getUserDeck())
                {
                    result += objectMapper.writeValueAsString(this.dummyCards.getCard(id));
                }

                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        result

                );
            }
            else
            {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ \"message\" : \"User deck is empty.\" }"
                );
            }



        }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "[]"
            );
        }

        return new Response(
                HttpStatus.NOT_FOUND,
                ContentType.JSON,
                "{ \"message\" : \"User not found\" }"
        );

    }

}
