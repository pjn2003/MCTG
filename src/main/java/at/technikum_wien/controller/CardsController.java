package at.technikum_wien.controller;

import at.technikum_wien.dummydata.DummyCards;
import at.technikum_wien.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.models.Card;
import at.technikum_wien.models.User;

public class CardsController extends Controller{

    private UserDummyData userData;
    private DummyCards dummyCards;

    public CardsController() {
        this.userData = new UserDummyData(true);
        this.dummyCards = new DummyCards();
    }

    public Response getUserCards(String uname)
    {
        try {

            if(this.userData.getUser(uname) != null)
            {

                if (this.userData.getUser(uname).getUserCards().size() > 0)
                {

                    String cardString = "";

                    for (Integer cardId : this.userData.getUser(uname).getUserCards()) {
                        if (this.dummyCards.getCard(cardId) != null)
                        {
                            cardString += dummyCards.getCard(cardId).getName() + "\n";
                        }
                        else
                        {
                            return new Response(
                                    HttpStatus.NOT_FOUND,
                                    ContentType.JSON,
                                    "{ \"message\" : \"User has an invalid card.\" }"
                            );
                        }
                    }

                    return new Response(
                            HttpStatus.OK,
                            ContentType.JSON,
                            "{ \"message\" : \"The user has the following cards: \"\n%s }".formatted(cardString)
                    );
                }
                else
                {
                    return new Response(
                            HttpStatus.NOT_FOUND,
                            ContentType.JSON,
                            "{ \"message\" : \"User has no cards\" }"
                    );
                }



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
