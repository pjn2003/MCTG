package at.technikum_wien.controller;

import at.technikum_wien.dummydata.DummyPackages;
import at.technikum_wien.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;



public class TransactionController extends Controller{

    private UserDummyData userDummyData;
    private DummyPackages dummyPackages;
    public TransactionController() {
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