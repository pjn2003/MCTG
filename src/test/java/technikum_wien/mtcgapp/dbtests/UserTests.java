package technikum_wien.mtcgapp.dbtests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import technikum_wien.mtcgapp.businesslogic.BattleManagerTest;
import technikum_wien.mtcgapp.controller.ControllerTest;
import technikum_wien.mtcgapp.models.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests extends ControllerTest {

    public static Connection con;
    BattleManagerTest battleManagerTest = new BattleManagerTest();

    @BeforeAll
    public static void setUp() throws Exception {
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mtcgdb?user=postgres&password=postgres");
    }


    @Test //Test user fetching
    public void TestGetUser() throws SQLException {
        System.out.println("Searching for user:" + "test_user");
        String query = "SELECT * FROM mtcguser WHERE username=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, "test_user");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("User found: " +rs.getString("username"));
            assert(true);
        }
        else
        {
            System.out.println("User not found");
            assert(false);
        }


    }

    @Test //Test adding, updating and deleting a new user
    public void TestNewUser() throws SQLException {
        System.out.println("Adding user:" + "test_user_2");
        String query = "INSERT INTO mtcguser (username, password) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, "test_user_2");
        ps.setString(2, "pasowred");
        ps.executeUpdate();

        System.out.println("Updating user:" + "test_user_2");
        query = "UPDATE mtcguser SET bio=? WHERE username=?";
        ps = con.prepareStatement(query);
        ps.setString(2, "test_user_2");
        ps.setString(1, "I now have a bio");
        ps.executeUpdate();

        System.out.println("Updated user:" + "test_user_2");
        query = "SELECT * FROM mtcguser WHERE username=?";
        ps = con.prepareStatement(query);
        ps.setString(1, "test_user_2");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("User found: " +rs.getString("username"));
            System.out.println("Bio: " +rs.getString("bio"));
            assert(true);
        }
        else
        {
            System.out.println("User not found");
            assert(false);
        }

        System.out.println("Deleting user:" + "test_user_2");
        query = "DELETE FROM mtcguser WHERE username=?";
        ps = con.prepareStatement(query);
        ps.setString(1, "test_user_2");
        ps.executeUpdate();

    }

    @Test
    public void TestScoreboard() throws SQLException {
        System.out.println("Scoreboard: ");
        String query = "SELECT * FROM mtcguser ORDER BY elo DESC";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        System.out.println("ELO - Username - W/L\n");
        int counter=1;
        StringBuilder res= new StringBuilder();
        while (rs.next()) {
            res.append(counter).append(". ").append(rs.getString("elo")).append(" - ").append(rs.getString("username")).append(" - ").append(rs.getInt("wins")).append("/").append(rs.getInt("losses")).append("\n");
            counter++;
        }
        System.out.println(res);
        rs.close();
    }

    @Test
    public void TestCardIds() throws SQLException {
        String query = "SELECT * FROM mtcguser WHERE username=?";

        PreparedStatement ps= con.prepareStatement(query);
        ps.setString(1, "test_user");
        ResultSet rs=ps.executeQuery();
        if (rs.next()) {
            for (Integer cardid : (Integer[])rs.getArray("cards").getArray()) {
                query = "SELECT * FROM cards WHERE id=?";
                ps = con.prepareStatement(query);
                ps.setInt(1, cardid);
                Card c;
                ResultSet rs2=ps.executeQuery();
                if (rs2.next()) {
                    if (rs2.getString("cardtype").equals("Monster")) {
                        c = new MonsterCard(cardid,rs2.getString("name"),
                                rs2.getInt("basedamage"), Element.valueOf(rs2.getString("element")),
                                MonsterType.valueOf(rs2.getString("monstertype")));

                    }
                    else
                    {
                        c = new SpellCard(cardid,rs2.getString("name"),
                                rs2.getInt("basedamage"), Element.valueOf(rs2.getString("element")));
                    }

                    c.describe();
                }


            }

        }

    }

    @Test //Test a battle and update elo/wins/losses
    public void TestBattle() throws SQLException {
        System.out.println("Battle: ");
        String query = "SELECT * FROM mtcguser ORDER BY elo DESC LIMIT 2";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        User user1;
        User user2;
        if (rs.next()) {
            System.out.println("User found: " +rs.getString("username"));
            user1 = new User(rs.getString("username"),rs.getInt("coins"),rs.getString("bio"),
                    rs.getInt("elo"),rs.getInt("wins"),
                    rs.getInt("losses"),rs.getBoolean("is_admin"), (Integer[])rs.getArray("cards").getArray(),
                    (Integer[])rs.getArray("deck").getArray());
            if (rs.next())  {
                System.out.println("User found: " +rs.getString("username"));
                user2 = new User(rs.getString("username"),rs.getInt("coins"),rs.getString("bio"),
                        rs.getInt("elo"),rs.getInt("wins"),
                        rs.getInt("losses"),rs.getBoolean("is_admin"), (Integer[])rs.getArray("cards").getArray(),
                        (Integer[])rs.getArray("deck").getArray());
                int r = battleManagerTest.Battle(user1,user2);

                if (r==1)
                {
                    query = "UPDATE mtcguser SET wins=?, elo=? WHERE username=?";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, user1.getWins()+1);
                    ps.setInt(2, user1.getElo()+3);
                    ps.setString(3, user1.getUsername());

                    ps.executeUpdate();

                    query = "UPDATE mtcguser SET losses=?, elo=? WHERE username=?";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, user2.getLosses()+1);
                    ps.setInt(2, user2.getElo()-5);
                    ps.setString(3, user2.getUsername());
                    ps.executeUpdate();

                }
                else if (r==2)
                {
                    query = "UPDATE mtcguser SET wins=?, elo=? WHERE username=?";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, user2.getWins()+1);
                    ps.setInt(2, user2.getElo()+3);
                    ps.setString(3, user2.getUsername());

                    ps.executeUpdate();

                    query = "UPDATE mtcguser SET losses=?, elo=? WHERE username=?";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, user1.getLosses()+1);
                    ps.setInt(2, user1.getElo()-5);
                    ps.setString(3, user1.getUsername());
                    ps.executeUpdate();
                }
            }
        }





    }



    @AfterAll //Disconnect
    public static void tearDown() throws Exception {
        con.close();
    }


}
