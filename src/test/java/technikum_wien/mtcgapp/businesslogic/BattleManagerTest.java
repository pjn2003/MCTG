package technikum_wien.mtcgapp.businesslogic;

import org.junit.jupiter.api.Test;
import technikum_wien.mtcgapp.dummydata.DummyCards;
import technikum_wien.mtcgapp.models.*;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class BattleManagerTest {

    public User user1 = new User("John", 20, "i am john", 9999,500,0,false, new Integer[]{1, 3, 5, 7, 9}, new Integer[]{7, 3, 5, 9});
    public User user2 = new User("Jim", 20, "The Jimster", 0,0,125,false, new Integer[]{2, 4, 6, 8}, new Integer[]{2,4, 6, 8});
    DummyCards dummyCards = new DummyCards();
    public void initFight(User user1, User user2)
    {
        System.out.println("Starting fight between: " + user1 + " and " + user2);
    }

    //Special interactions
    boolean doesMonsterLose(MonsterCard card1, MonsterCard card2)
    {
        if (card1.getType() == MonsterType.Goblin && card2.getType() == MonsterType.Dragon ||
            card1.getType() == MonsterType.Ork && card2.getType() == MonsterType.Wizard ||
                card2.getType() == MonsterType.Elf && card2.getElement() == Element.Fire && card1.getType() == MonsterType.Dragon
        )
        {
            System.out.println("Monster " + card1.getName() + " lost due to a special interaction!");
            return true;
        }
        System.out.println("No special interaction occurred!");
        return false;
    }

    boolean doesSpellCardLose(SpellCard card1, MonsterCard card2)
    {
        if (card1.getElement() == Element.Water && card2.getType() == MonsterType.Knight){

            System.out.println("Knights are unaffected by water!");
            return true;
        }

        if (card2.getType() == MonsterType.Kraken){
            System.out.println("The Kraken resists all magic!");
            return true;
        }

        return false;
    }

    //Elemental effectiveness
    public static float resolveElements(Element attack, Element defense)
    {
        if (
                attack == Element.Fire && defense == Element.Normal ||
                        attack == Element.Water && defense == Element.Fire ||
                        attack == Element.Normal && defense == Element.Water

        )
        {
            System.out.println("It's super effective!");
            return 2; //Effective
        }
        else if (
                attack == Element.Normal && defense == Element.Fire ||
                        attack == Element.Fire && defense == Element.Water ||
                        attack == Element.Water && defense == Element.Normal

        )
        {
            System.out.println("It's not very effective...");
            return 0.5f; //Not effective
        }
        else
        {
            return 1; //Same element
        }
    }
    Element f = Element.Fire;
    Element w = Element.Water;
    Element n = Element.Normal;

    //Unit tests: Check if element effectiveness was properly implemented
    @Test
    public void testSameElement()
    {
        assertEquals(1, resolveElements(f, f));
    }
    @Test
    public void testNotEffective()
    {
        assertEquals(0.5f, resolveElements(f, w));

    }
    @Test
    public void testEffective()
    {
        assertEquals(2, resolveElements(f, n));
    }

    @Test //Unit test: Check if monsters have immunities properly implemented
    public void testMonsters()
    {
        MonsterCard goblin = new MonsterCard(1,"Wood Goblin",1,Element.Normal,MonsterType.Goblin);
        MonsterCard dragon = new MonsterCard(2,"Flame Dragon",50,Element.Fire,MonsterType.Dragon);
        MonsterCard wizard = new MonsterCard(3, "Tsunami Wizard",25,Element.Water,MonsterType.Wizard);
        MonsterCard ork = new MonsterCard(4,"Savage Ork",15,Element.Normal,MonsterType.Ork);
        MonsterCard knight = new MonsterCard(5,"Steel Knight",18,Element.Normal,MonsterType.Knight);
        MonsterCard kraken = new MonsterCard(6,"The Kraken",65,Element.Water,MonsterType.Kraken);
        MonsterCard fireElf = new MonsterCard(7,"Xarius the Volcano Dweller",25,Element.Fire,MonsterType.Elf);
        SpellCard waterSpell = new SpellCard(8,"Tsunami",35,Element.Water);

        assertEquals(true, doesMonsterLose(goblin, dragon));
        assertEquals(true, doesMonsterLose(ork, wizard));
        assertEquals(true, doesMonsterLose(dragon, fireElf));
        assertEquals(false, doesMonsterLose(dragon, wizard));
        assertEquals(false, doesMonsterLose(ork, goblin));
        assertEquals(true, doesSpellCardLose(waterSpell, knight));
        assertEquals(true, doesSpellCardLose(waterSpell, kraken));

    }

    @Test //Unit test: Conduct a battle
    public void conductBattle()
    {
        int roundCounter;
        int winner=0;
        ArrayList<Integer> deck1 = user1.getUserDeck();
        ArrayList<Integer> deck2 = user2.getUserDeck();
        Random rand = new Random();
        for (roundCounter = 1; roundCounter <= 100; roundCounter++)
        {

            Integer rand1 = rand.nextInt(deck1.size());
            if (rand1 > deck1.size())
                rand1 = deck1.size() - 1;
            Integer rand2 = rand.nextInt(deck2.size());
            if (rand2 > deck2.size())
                rand2 = deck2.size() - 1;
            System.out.println("\n---#|> ROUND " + roundCounter + " <|#---\n");
            System.out.println("Player 1's deck: " + deck1.size());
            System.out.println("Player 2's deck: " + deck2.size());
            Integer card1id = deck1.get(rand1);
            Integer card2id = deck2.get(rand2);
            System.out.println("Player 1's card: " + dummyCards.getCard(card1id).getName());
            System.out.println("Player 2's card: " + dummyCards.getCard(card2id).getName());
            String t = turn(deck1,deck2,dummyCards.getCard(card1id),dummyCards.getCard(card2id));
            System.out.println(t);

            if (deck1.isEmpty())
            {
                winner=2;
                System.out.println(user1.getUsername() + " has no cards left. " + user2.getUsername() + " has won the fight!");
                break;
            }
            else if (deck2.isEmpty())
            {
                winner=1;
                System.out.println(user2.getUsername() + " has no cards left. " + user1.getUsername() + " has won the fight!");
                break;
            }

            if (roundCounter == 100)
            {
                System.out.println("Round 100 reached. The battle ends in a draw!");
            }
        }

        //This battle should always end with Jim winning
        assertEquals(winner, 2);
        assertNotSame(roundCounter,100);


    }


    //For other scripts to use
    public int Battle(User user1, User user2)
    {
        int roundCounter;
        ArrayList<Integer> deck1 = user1.getUserDeck();
        ArrayList<Integer> deck2 = user2.getUserDeck();
        Random rand = new Random();
        for (roundCounter = 1; roundCounter <= 100; roundCounter++)
        {

            Integer rand1 = rand.nextInt(deck1.size());
            if (rand1 > deck1.size())
                rand1 = deck1.size() - 1;
            Integer rand2 = rand.nextInt(deck2.size());
            if (rand2 > deck2.size())
                rand2 = deck2.size() - 1;
            System.out.println("\n---#|> ROUND " + roundCounter + " <|#---\n");
            System.out.println("Player 1's deck: " + deck1.size());
            System.out.println("Player 2's deck: " + deck2.size());
            Integer card1id = deck1.get(rand1);
            Integer card2id = deck2.get(rand2);
            System.out.println("Player 1's card: " + dummyCards.getCard(card1id).getName());
            System.out.println("Player 2's card: " + dummyCards.getCard(card2id).getName());
            String t = turn(deck1,deck2,dummyCards.getCard(card1id),dummyCards.getCard(card2id));
            System.out.println(t);

            if (deck1.isEmpty())
            {
                System.out.println(user1.getUsername() + " has no cards left. " + user2.getUsername() + " has won the fight!");
                return 2;
            }
            else if (deck2.isEmpty())
            {
                System.out.println(user2.getUsername() + " has no cards left. " + user1.getUsername() + " has won the fight!");
                return 1;
            }

            if (roundCounter == 100)
            {
                System.out.println("Round 100 reached. The battle ends in a draw!");
                return 0;
            }


        }
        return 0;
    }



    String turn(ArrayList<Integer> deck1, ArrayList<Integer> deck2, Card card1, Card card2)
    {
        //Check if there is a spell card
        boolean spellActive= card1 instanceof SpellCard || card2 instanceof SpellCard;


        if (!spellActive)
        {
            boolean card1lose = doesMonsterLose((MonsterCard) card1, (MonsterCard) card2);
            if (card1lose)
            {
                deck1.remove((Integer) card1.getId());
                deck2.add(card1.getId());
                return "\n"+card2.getName() + " wins!";
            }
            else
            {
                boolean card2lose = doesMonsterLose((MonsterCard) card1, (MonsterCard) card2);
                if (card2lose)
                {
                    deck2.remove((Integer) card2.getId());
                    deck1.add(card2.getId());
                    return "\n" + card1.getName() + " wins!";
                }
            }


        }
        else
        {
            if (card1 instanceof SpellCard && card2 instanceof MonsterCard)
            {
                if(doesSpellCardLose((SpellCard) card1, (MonsterCard) card2))
                {
                    deck1.remove((Integer) card1.getId());
                    deck2.add(card1.getId());
                    return "\n" +card2.getName() + " wins!";
                }
            }
            else if (card2 instanceof SpellCard && card1 instanceof MonsterCard)
            {
                if(doesSpellCardLose((SpellCard) card2, (MonsterCard) card1))
                {
                    deck2.remove((Integer) card2.getId());
                    deck1.add(card2.getId());
                    return "\n" +card1.getName() + " wins!";
                }
            }
        }


        System.out.println("Attacker: " + card1.getName() + ", Defender: " + card2.getName());
        float dmg = card1.getBaseDamage();
        if (spellActive)
            dmg = dmg*resolveElements(card1.getElement(),card2.getElement());
        System.out.println("Dealt "+dmg+ " points of damage!");

        System.out.println("Attacker: " + card2.getName() + ", Defender: " + card1.getName());
        float dmg2 = card2.getBaseDamage();
        if (spellActive)
            dmg2 = dmg2 * resolveElements(card2.getElement(),card1.getElement());
        System.out.println("Dealt "+dmg2+ " points of damage!");

        if (dmg > dmg2)
        {
            deck2.remove((Integer) card2.getId());
            deck1.add(card2.getId());
            return "\n" +card1.getName() + " wins!";

        }
        else if (dmg2 > dmg)
        {
            deck1.remove((Integer) card1.getId());
            deck2.add(card1.getId());
            return "\n" +card2.getName() + " wins!";
        }
        else if (dmg == dmg2)
        {
            return "It's a draw!";
        }

        return "ERROR";

    }


}
