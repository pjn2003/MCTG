package at.technikum_wien.mtcgapp.businesslogic;

import at.technikum_wien.mtcgapp.models.*;

import java.util.ArrayList;
import java.util.Random;

public class BattleManager {


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

    public int Battle(User user1, User user2)
    {
        if (user1.getUserDeck().size() < 4 || user2.getUserDeck().size() < 4)
        {
            System.out.println("Battle failed! One or both players don't have valid decks.");
            return 0;
        }


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
