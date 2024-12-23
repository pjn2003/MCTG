package technikum_wien.mtcgapp.dbtests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import technikum_wien.mtcgapp.businesslogic.BattleManagerTest;
import technikum_wien.mtcgapp.models.*;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTests {
    public static Connection con;

    @BeforeAll
    public static void setUp() throws Exception {
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mtcgdb?user=postgres&password=postgres");
    }

    //Unit Tests

    @Test
    public void testCardDescribe()
    {
        MonsterCard m = new MonsterCard(0,"Alfred",10,Element.Normal,MonsterType.Knight);
        assertEquals("Monster Alfred, Damage: 10, Element: Normal, Type: Knight",m.describe());
    }

    @Test
    public void testTradeDealDescription()
    {
        TradeDeal td = new TradeDeal(0,8,"Spell",5,"Jim");
        assertEquals(td.printDeal(),"Deal 0 | Card offered: 8, Minimum Damage Wanted: 5, Looking for card of type: Spell, posted by Jim");
    }

    @Test
    public void packageTest()
    {
        CardPackage p = new CardPackage("Beginner Pack",new Integer[]{1,2,3,4,5});
        System.out.println(p.getPack());
        assertEquals("Beginner Pack\n1\n2\n3\n4\n5\n",p.getPack());
    }


    //SQL Tests (integration tests)

    @Test
    public void testCardRead() throws SQLException {
        String query = "SELECT * FROM cards";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            String output="";
            for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                if(rs.getString(k)!=null) {
                    output += rs.getMetaData().getColumnName(k) + ": " + rs.getString(k) + " | ";
                }

            }
            System.out.println(output);
        }
    }

    @Test
    public void getCardFromDatabase() {
        int cardId = 10;
        Card c=new SpellCard(0,"NULL",0,Element.Normal);
        try {
            String query = "SELECT * FROM cards WHERE id = 10";
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
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

            assert(c.getName().equals("Grigori"));
            c.describe();

        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Card not found");
        }
    }

    @Test //User 1 offers a card. User 2 must submit a card they own that fits the criteria.
    public void tradeTest() throws SQLException {

        TradeDeal td = new TradeDeal(0,8,"Spell",5,"admin");
        td.printDeal();
        User user1 = new User("john","smith");
        User user2=user1;
        String query = "SELECT * FROM mtcguser WHERE username=? OR username=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, "admin");
        ps.setString(2, "test_user");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user1 = new User(rs.getString("username"),rs.getInt("coins"),rs.getString("bio"),
                    rs.getInt("elo"),rs.getInt("wins"),
                    rs.getInt("losses"),rs.getBoolean("is_admin"), (Integer[])rs.getArray("cards").getArray(),
                    (Integer[])rs.getArray("deck").getArray());
        }
        if (rs.next()) {
            user2 = new User(rs.getString("username"),rs.getInt("coins"),rs.getString("bio"),
                    rs.getInt("elo"),rs.getInt("wins"),
                    rs.getInt("losses"),rs.getBoolean("is_admin"), (Integer[])rs.getArray("cards").getArray(),
                    (Integer[])rs.getArray("deck").getArray());
        }
        System.out.println("User 1 cards: " + user1.getUserCards());
        System.out.println("User 2 cards: " + user2.getUserCards());
        if (user1.getCardFromInv(td.getCardToTrade())==null)
        {
            System.out.println("User 1 does not have a card to trade.");
            assert(false);
        }

        Integer offeredCard=2;
        if (user2.getCardFromInv(offeredCard)==null)
        {
            System.out.println("User 2 does not own this card!");
            assert(false);
        }


        query = "SELECT * FROM cards WHERE id = ?";
        ps = con.prepareStatement(query);
        ps.setInt(1, offeredCard);
        rs = ps.executeQuery();
        if (rs.next()) {
            if (rs.getString("cardtype").equals(td.getCardType()) &&
            rs.getInt("basedamage") >= td.getMinDamage())
            {
                System.out.println("Card " + rs.getInt("id")+ ": " + rs.getString("name") + " has been accepted as a valid offer!");
                user1.addCardToInventory(offeredCard);
                user2.addCardToInventory(td.getCardToTrade());
                user1.removeCardFromInventory(td.getCardToTrade());
                user2.removeCardFromInventory(offeredCard);
                //SQL query in final code
                System.out.println("User 1 cards: " + user1.getUserCards());
                System.out.println("User 2 cards: " + user2.getUserCards());
            }
            else
            {
                System.out.println("Card " + rs.getInt("id")+ ": " + rs.getString("name") + " does not fit the criteria!");
                assert(false);
            }
        }



    }


    @Test
    public void testMonsterCardCreation() throws SQLException {

        MonsterCard monsterCard = new MonsterCard(2,"Thunder Dragon",38, Element.Normal, MonsterType.Dragon);
        String query = "SELECT MAX(id)+1 FROM cards";
        PreparedStatement ps = con.prepareStatement(query);
        int newId=0;
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            newId = rs.getInt(1);
        }
        else
        {
            System.out.println("There was an error.");
            assert(false);
        }

        monsterCard.setId(newId);
        query="INSERT INTO cards VALUES (?,?,?,?,?,?)";
        ps = con.prepareStatement(query);
        ps.setInt(1, monsterCard.getId());
        ps.setString(2, monsterCard.getName());
        ps.setInt(3,monsterCard.getBaseDamage());
        ps.setString(4,monsterCard.getElement().toString());
        ps.setString(5,"Monster");
        ps.setString(6,monsterCard.getType().toString());
        ps.executeUpdate();
        System.out.println("Card created.");

        query="DELETE FROM cards WHERE id=?";
        ps = con.prepareStatement(query);
        ps.setInt(1, monsterCard.getId());
        ps.executeUpdate();
        System.out.println("Card deleted.");



    }

}
