package at.technikum_wien.mtcgapp.controller;

import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.mtcgapp.businesslogic.BattleManager;
import at.technikum_wien.mtcgapp.models.*;
import at.technikum_wien.mtcgapp.service.HelloService;

import java.sql.*;
import java.util.ArrayList;

public class BattleController extends Controller {

    BattleManager bm = new BattleManager();

    ArrayList<Card> getCards(){
        try {
            Connection con = connect();
            String query = "SELECT * FROM cards ORDER BY id ASC"; //Ensure cards are ordered correctly
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            con.close();
            ArrayList<Card> cards = new ArrayList<>();
            while(rs.next()){
                cards.add(getCardFromDatabase(rs.getInt("id")));
            }
            return cards;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Card getCardFromDatabase(Integer id) {

        Card c=new SpellCard(0,"NULL",0, Element.Normal);

        try {
            Connection con = connect();
            String query = "SELECT * FROM cards WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            con.close();
            if (rs.next()) {
                if (rs.getString("cardtype").equals("Monster"))
                {
                    c=new MonsterCard(rs.getInt("id"),rs.getString("name"),
                            rs.getInt("basedamage"),Element.valueOf(rs.getString("element")),
                            MonsterType.valueOf(rs.getString("monstertype")));
                }
                else
                {
                    c=new SpellCard(rs.getInt("id"),rs.getString("name"),
                            rs.getInt("basedamage"),Element.valueOf(rs.getString("element")));
                }
            }


            //c.describe();

        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Card not found");
        }

        return c;
    }

    public Response enterLobby(String uname)
    {
        try {



            Connection con = connect();
            String query = "SELECT COUNT(*) FROM battlelobby";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) != 0) //If there is a player in the lobby
                {


                    query = "SELECT * FROM battlelobby WHERE username = ?"; //Check if the player already exists in the lobby
                    ps = con.prepareStatement(query);
                    ps.setString(1, uname);
                    rs = ps.executeQuery();
                    if (rs.next())
                    {
                        if (rs.getInt("ended")==1) //Check if the player's battle has ended
                        {
                            Integer seed = rs.getInt("seed");
                            String opName = rs.getString("opponent");
                            query = "SELECT * FROM mtcguser WHERE username = ?";
                            ps = con.prepareStatement(query);
                            ps.setString(1, uname);
                            rs = ps.executeQuery();
                            User userData=null;
                            if (rs.next())
                            {
                                userData = new User(rs.getString("username"),rs.getInt("coins"),rs.getString("bio"),
                                        rs.getInt("elo"),rs.getInt("wins"),
                                        rs.getInt("losses"),rs.getBoolean("is_admin"), (Integer[])rs.getArray("cards").getArray(),
                                        (Integer[])rs.getArray("deck").getArray());
                                userData.setPassword("Hidden");
                            }

                            query = "SELECT * FROM mtcguser WHERE username = ?";
                            ps = con.prepareStatement(query);
                            ps.setString(1, opName);
                            rs = ps.executeQuery();
                            User user2Data=null;
                            if (rs.next())
                            {
                                user2Data = new User(rs.getString("username"),rs.getInt("coins"),rs.getString("bio"),
                                        rs.getInt("elo"),rs.getInt("wins"),
                                        rs.getInt("losses"),rs.getBoolean("is_admin"), (Integer[])rs.getArray("cards").getArray(),
                                        (Integer[])rs.getArray("deck").getArray());
                                user2Data.setPassword("Hidden");
                            }
                            bm.setCards(getCards());
                            String resString="";
                            BattleResult winner = bm.Battle(user2Data,userData,seed); //Swap user1 and user2
                            resString = winner.getResultString();

                            query = "DELETE FROM battlelobby WHERE username = ?";
                            ps = con.prepareStatement(query);
                            ps.setString(1, uname);
                            ps.executeUpdate();

                            return new Response(
                                    HttpStatus.OK,
                                    ContentType.JSON,
                                    resString
                            );

                        }
                        else
                        {
                            return new Response(
                                    HttpStatus.OK,
                                    ContentType.JSON,
                                    "{ \"message\" : \"Nobody has challenged you yet.\nCheck back later!\" }"
                            );
                        }
                    }




                    query = "SELECT * FROM battlelobby WHERE ended = 0 LIMIT 1";
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    if (!rs.next()) //No active battles - create a new one
                    {
                        query = "SELECT * FROM mtcguser WHERE username=?";
                        PreparedStatement user1Statement = con.prepareStatement(query);
                        user1Statement.setString(1, uname);
                        ResultSet user1Res = user1Statement.executeQuery();
                        if (user1Res.next())
                        {
                            Array d = user1Res.getArray("deck");
                            if (d==null)
                            {
                                return new Response(
                                        HttpStatus.FORBIDDEN,
                                        ContentType.JSON,
                                        "{ \"message\" : \"You don't have a deck! Make one first!\" }"
                                );
                            }
                        }

                        Integer randInt = (int) (Math.random() * 9999);
                        System.out.println("Adding user to lobby");
                        query = "INSERT INTO battlelobby (username, seed, ended) VALUES (?,?,?)";
                        ps = con.prepareStatement(query);
                        ps.setString(1, uname);
                        ps.setInt(2, randInt);
                        ps.setInt(3,0);
                        ps.executeUpdate();
                        return new Response(
                                HttpStatus.OK,
                                ContentType.JSON,
                                "{ \"message\" : \"You have entered the lobby, but there is no one else.\nCheck back later!\" }"
                        );
                    }
                    Integer seed = rs.getInt("seed");
                    String opName = rs.getString("username");

                    bm.setCards(getCards());

                    //Retrieve first user
                    query = "SELECT * FROM mtcguser WHERE username=?";
                    System.out.println("Checking if user has a deck");
                    ps = con.prepareStatement(query);
                    ps.setString(1, uname);
                    rs = ps.executeQuery();
                    User userData;
                    if(rs.next())
                    {
                        Array d = rs.getArray("deck");
                        if (d==null)
                        {
                            return new Response(
                                    HttpStatus.FORBIDDEN,
                                    ContentType.JSON,
                                    "{ \"message\" : \"You don't have a deck! Make one first!\" }"
                            );
                        }


                        userData = new User(rs.getString("username"),rs.getInt("coins"),rs.getString("bio"),
                                rs.getInt("elo"),rs.getInt("wins"),
                                rs.getInt("losses"),rs.getBoolean("is_admin"), (Integer[])rs.getArray("cards").getArray(),
                                (Integer[])rs.getArray("deck").getArray());
                        userData.setPassword("Hidden");
                    }
                    else
                    {
                        return new Response(
                                HttpStatus.NOT_FOUND,
                                ContentType.JSON,
                                "{ \"message\" : \"User not found.\" }"
                        );
                    }

                    //Retrieve second user
                    query = "SELECT * FROM mtcguser WHERE username=?";
                    ps = con.prepareStatement(query);
                    ps.setString(1, opName);
                    rs = ps.executeQuery();
                    User user2Data;
                    if(rs.next())
                    {
                        user2Data = new User(rs.getString("username"),rs.getInt("coins"),rs.getString("bio"),
                                rs.getInt("elo"),rs.getInt("wins"),
                                rs.getInt("losses"),rs.getBoolean("is_admin"), (Integer[])rs.getArray("cards").getArray(),
                                (Integer[])rs.getArray("deck").getArray());
                        user2Data.setPassword("Hidden");
                    }
                    else
                    {
                        return new Response(
                                HttpStatus.NOT_FOUND,
                                ContentType.JSON,
                                "{ \"message\" : \"User not found.\" }"
                        );
                    }
                    String resString="";
                    BattleResult winner = bm.Battle(userData,user2Data,seed);
                    resString = winner.getResultString();

                    if (winner.getWinner() == 1)
                    {
                        userData.setElo(userData.getElo()+3);
                        userData.setWins(userData.getWins()+1);
                        user2Data.setElo(user2Data.getElo()-5);
                        user2Data.setLosses(user2Data.getLosses()+1);
                    }
                    else if (winner.getWinner() == 2)
                    {
                        user2Data.setElo(user2Data.getElo()+3);
                        user2Data.setWins(user2Data.getWins()+1);
                        userData.setElo(userData.getElo()-5);
                        userData.setLosses(userData.getLosses()+1);
                    }

                    query = "UPDATE mtcguser SET elo=?, wins=?, losses=? WHERE username=?";
                    ps = con.prepareStatement(query);
                    ps.setInt(1,userData.getElo());
                    ps.setInt(2,userData.getWins());
                    ps.setInt(3,userData.getLosses());
                    ps.setString(4,userData.getUsername());
                    ps.executeUpdate();

                    query = "UPDATE mtcguser SET elo=?, wins=?, losses=? WHERE username=?";
                    ps = con.prepareStatement(query);
                    ps.setInt(1,user2Data.getElo());
                    ps.setInt(2,user2Data.getWins());
                    ps.setInt(3,user2Data.getLosses());
                    ps.setString(4,user2Data.getUsername());
                    ps.executeUpdate();

                    query = "UPDATE battlelobby SET ended=1, opponent=? WHERE username=?";
                    ps = con.prepareStatement(query);
                    ps.setString(1,userData.getUsername());
                    ps.setString(2,user2Data.getUsername());
                    ps.executeUpdate();

                    return new Response(
                            HttpStatus.OK,
                            ContentType.JSON,
                            resString
                    );

                }
                else //If there is no player
                {

                    query = "SELECT * FROM mtcguser WHERE username=?";
                    PreparedStatement user1Statement = con.prepareStatement(query);
                    user1Statement.setString(1, uname);
                    ResultSet user1Res = user1Statement.executeQuery();
                    if (user1Res.next())
                    {
                        Array d = user1Res.getArray("deck");
                        if (d==null)
                        {
                            return new Response(
                                    HttpStatus.FORBIDDEN,
                                    ContentType.JSON,
                                    "{ \"message\" : \"You don't have a deck! Make one first!\" }"
                            );
                        }
                    }

                    Integer randInt = (int) (Math.random() * 9999);
                    System.out.println("Adding user to lobby");
                    query = "INSERT INTO battlelobby (username, seed, ended) VALUES (?,?,?)";
                    ps = con.prepareStatement(query);
                    ps.setString(1, uname);
                    ps.setInt(2, randInt);
                    ps.setInt(3,0);
                    ps.executeUpdate();
                    return new Response(
                            HttpStatus.OK,
                            ContentType.JSON,
                            "{ \"message\" : \"You have entered the lobby, but there is no one else.\nCheck back later!\" }"
                    );
                }


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
        return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ContentType.JSON,
                "{ \"message\" : \"Internal Server Error\" }"
        );
    }


}
