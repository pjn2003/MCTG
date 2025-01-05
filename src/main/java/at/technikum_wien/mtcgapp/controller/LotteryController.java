package at.technikum_wien.mtcgapp.controller;

import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class LotteryController extends Controller {
    public LotteryController() {}

    public Response playLottery(String userName) {
        try {
            Connection con = connect();
            String query = "SELECT * FROM mtcguser WHERE username=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userName);
            System.out.println("Executing query: " + query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int coins = rs.getInt("coins");
                Array inv = rs.getArray("cards");
                Integer[] temp = (Integer[]) inv.getArray();
                for (int i = 0; i < temp.length; i++) {
                    System.out.println(temp[i]);
                }
                System.out.println(coins);
                if (coins < 2) {
                    return new Response(
                            HttpStatus.FORBIDDEN,
                            ContentType.JSON,
                            "{ \"message\" : \"That user does not have enough coins!\" }"
                    );
                }
                else
                {
                    query = "SELECT COUNT(*) FROM cards";
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        Integer cards = rs.getInt(1);
                        Integer rnd = (int) (Math.random() * cards);
                        System.out.println("Card won: "+rnd);


                        Integer[] newInv = new Integer[temp.length + 1];
                        for (int i = 0; i < temp.length; i++) {
                            newInv[i] = temp[i];
                        }
                        newInv[newInv.length - 1] = rnd;

                        Array newSQLArray = con.createArrayOf("integer", newInv);

                        query = "UPDATE mtcguser SET coins = ?, cards=? WHERE username = ?";
                        ps = con.prepareStatement(query);
                        ps.setInt(1, coins-2);
                        ps.setArray(2, newSQLArray);
                        ps.setString(3, userName);
                        ps.executeUpdate();
                        String responseString = "You have acquired a new card with the ID " + rnd+ "!";

                        return new Response(
                                HttpStatus.OK,
                                ContentType.JSON,
                                responseString
                        );


                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ContentType.JSON,
                "{ \"message\" : \"Error!\" }"
        );

    }
}
