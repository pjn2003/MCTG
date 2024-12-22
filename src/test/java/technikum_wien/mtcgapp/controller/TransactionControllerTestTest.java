package technikum_wien.mtcgapp.controller;

import technikum_wien.httpserver.http.ContentType;
import technikum_wien.httpserver.http.HttpStatus;
import technikum_wien.httpserver.server.Response;
import technikum_wien.mtcgapp.dummydata.DummyPackages;
import technikum_wien.mtcgapp.dummydata.UserDummyData;


public class TransactionControllerTestTest extends ControllerTest {

    private UserDummyData userDummyData;
    private DummyPackages dummyPackages;
    public TransactionControllerTestTest() {
        this.userDummyData = new UserDummyData(true);
        this.dummyPackages = new DummyPackages();
    }

    public Response purchasePack(String packName, String userName )
    {
        if (this.dummyPackages.getPackage(packName) != null)
        {
            if (this.userDummyData.getUser(userName) != null)
            {
                if (this.userDummyData.getUser(userName).getCoins() < 5)
                {
                    return new Response(
                            HttpStatus.FORBIDDEN,
                            ContentType.JSON,
                            "{ \"message\" : \"User did not have enough coins.\" }"
                    );
                }
                else
                {
                    System.out.println("User has enough coins");
                    for (Integer cardId : this.dummyPackages.getPackage(packName).getCardList())
                    {
                        System.out.println("Added card "+cardId + " to " + userName);
                        this.userDummyData.getUser(userName).addCardToInventory(cardId);
                    }

                    this.userDummyData.getUser(userName).setCoins(this.userDummyData.getUser(userName).getCoins() - 5);

                    return new Response(
                            HttpStatus.OK,
                            ContentType.JSON,
                            "{ \"message\" : \"Package bought!\" }"
                    );
                }


            }
            else
            {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ \"message\" : \"User not found.\" }"
                );
            }
        }
        else
        {
            return new Response(
                    HttpStatus.NOT_FOUND,
                    ContentType.JSON,
                    "{ \"message\" : \"Pack not found.\" }"
            );
        }
    }





}