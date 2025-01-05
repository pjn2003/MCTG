package at.technikum_wien.mtcgapp.controller;

import at.technikum_wien.mtcgapp.dummydata.DummyPackages;
import at.technikum_wien.mtcgapp.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.mtcgapp.models.User;

import java.sql.*;
import java.util.ArrayList;


public class TransactionController extends Controller{



    public Response purchasePack(String packName, String userName ) {


        try {
            Connection con = connect();
            String query = "SELECT coins FROM mtcguser WHERE username=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userName);
            System.out.println("Executing query: " + query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int coins = rs.getInt("coins");
                System.out.println(coins);
                if (coins < 5) {
                    return new Response(
                            HttpStatus.FORBIDDEN,
                            ContentType.JSON,
                            "{ \"message\" : \"That user does not have enough coins!\" }"
                    );
                }
                else
                {
                    query = "SELECT * FROM cardpacks WHERE packname=?";
                    ps = con.prepareStatement(query);
                    ps.setString(1, packName);
                    System.out.println("Executing query: " + query);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        User userData=new User(null,null);
                        String pQuery = "SELECT * FROM mtcguser WHERE username=?";
                        PreparedStatement ps2 = con.prepareStatement(pQuery);
                        ps2.setString(1, userName);
                        System.out.println("Executing query: " + pQuery);
                        ResultSet rs2 = ps2.executeQuery();

                        if (rs2.next())
                        {
                            userData = new User(rs2.getString("username"),rs2.getInt("coins"),rs2.getString("bio"),
                                    rs2.getInt("elo"),rs2.getInt("wins"),
                                    rs2.getInt("losses"),rs2.getBoolean("is_admin"), (Integer[])rs2.getArray("cards").getArray(),
                                    (Integer[])rs2.getArray("deck").getArray());
                            userData.setPassword("Hidden");
                            Integer[] packcards = (Integer[])rs.getArray("card_list").getArray();
                            Integer addedCardsCounter=0;
                            for (int i = 0; i < packcards.length; i++)
                            {

                                    System.out.println("adding card " + packcards[i]);
                                    ArrayList al = userData.getUserCards();

                                        al.add(packcards[i]);
                                        addedCardsCounter++;

                                    userData.setUserCards(al);
                            }


                            userData.setCoins(userData.getCoins() -5);
                            userData.describeUser();
                            String query3 = "UPDATE mtcguser SET coins=?,cards=? WHERE username=?";
                            PreparedStatement ps3 = con.prepareStatement(query3);

                            Integer[] newCards = userData.getUserCards().toArray(new Integer[userData.getUserCards().size()]);
                            Array sqlarray = con.createArrayOf("integer", newCards);
                            ps3.setInt(1, userData.getCoins());
                            ps3.setArray(2, sqlarray);
                            ps3.setString(3, userName);
                            System.out.println("Executing query: " + query3);
                            ps3.executeUpdate();
                            return new Response(
                                    HttpStatus.OK,
                                    ContentType.JSON,
                                    "{ \"message\" : \"Successful purchase!\" }"
                            );


                        }

                    }
                    else
                    {
                        return new Response(
                                HttpStatus.NOT_FOUND,
                                ContentType.JSON,
                                "{ \"message\" : \"That package does not exist!\" }"
                        );
                    }


                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        //Obsolete
        /*

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
        */
        return null;
    }





}