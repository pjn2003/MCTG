package at.technikum_wien.mtcgapp.businesslogic;

import at.technikum_wien.mtcgapp.models.*;
import lombok.Getter;
import lombok.Setter;

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
            //System.out.println("Monster " + card1.getName() + " lost due to a special interaction!");
            return true;
        }
        //System.out.println("No special interaction occurred!");
        return false;
    }

    boolean doesSpellCardLose(SpellCard card1, MonsterCard card2)
    {
        if (card1.getElement() == Element.Water && card2.getType() == MonsterType.Knight){

           // System.out.println("Knights are unaffected by water!");
            return true;
        }

        if (card2.getType() == MonsterType.Kraken){
          //  System.out.println("The Kraken resists all magic!");
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
           // System.out.println("It's super effective!");
            return 2; //Effective
        }
        else if (
                attack == Element.Normal && defense == Element.Fire ||
                        attack == Element.Fire && defense == Element.Water ||
                        attack == Element.Water && defense == Element.Normal

        )
        {
            //System.out.println("It's not very effective...");
            return 0.5f; //Not effective
        }
        else
        {
            return 1; //Same element
        }
    }

    @Getter
    @Setter
    ArrayList<Card> cards = new ArrayList<>();


    public BattleResult Battle(User user1, User user2, Integer seed)
    {
        String resultString = "";

        BattleResult result = new BattleResult();


        if (user1.getUserDeck().size() < 4 || user2.getUserDeck().size() < 4)
        {
            resultString+="\nBattle failed! One or both players don't have valid decks.";
            result.setResultString(resultString);
            result.setWinner(0);
            return result;
        }

        for (Card card : cards)
        {
            System.out.println(card.getId() + ": " + card.getName());
        }


        int roundCounter;
        ArrayList<Integer> deck1 = user1.getUserDeck();
        ArrayList<Integer> deck2 = user2.getUserDeck();
        Random rand = new Random(seed); //Assign a seed taken from the database to ensure the battle is synced for both
        for (roundCounter = 1; roundCounter <= 100; roundCounter++)
        {

            Integer rand1 = rand.nextInt(deck1.size());
            if (rand1 > deck1.size())
                rand1 = deck1.size() - 1;
            Integer rand2 = rand.nextInt(deck2.size());
            if (rand2 > deck2.size())
                rand2 = deck2.size() - 1;
            resultString+="\n---#|> ROUND " + roundCounter + " <|#---\n";
            resultString+="Player 1's deck: " + deck1;
            resultString+="\nPlayer 2's deck: " + deck2;
            Integer card1id = deck1.get(rand1);
            Integer card2id = deck2.get(rand2);

            Card c1 = null;
            Card c2 = null;
            //System.out.println("Cards in BM: "+cards.size());
            for (Card c : cards)
            {
                if (c.getId() == card1id)
                    c1=c;

                if (c.getId() == card2id)
                    c2=c;


            }
            resultString+="\nPlayer 1's card: " + c1.getName();
            resultString+="\nPlayer 2's card: " + c2.getName();
            resultString+=turn(deck1,deck2,c1,c2);

            if (deck1.isEmpty())
            {
                resultString+="\n"+user1.getUsername() + " has no cards left. " + user2.getUsername() + " has won the fight!";
                result.setResultString(resultString);
                result.setWinner(2);
                return result;
            }
            else if (deck2.isEmpty())
            {
                resultString+="\n"+user2.getUsername() + " has no cards left. " + user1.getUsername() + " has won the fight!";
                result.setResultString(resultString);
                result.setWinner(1);
                return result;
            }

            if (roundCounter == 100)
            {
                resultString+="\nRound 100 reached. The battle ends in a draw!";
                result.setResultString(resultString);
                result.setWinner(0);
                return result;
            }


        }
        result.setResultString("\nAn error occured!");
        result.setWinner(0);
        return result;
    }



    String turn(ArrayList<Integer> deck1, ArrayList<Integer> deck2, Card card1, Card card2)
    {
        //Check if there is a spell card
        boolean spellActive= card1 instanceof SpellCard || card2 instanceof SpellCard;
        String r="";

        if (!spellActive)
        {
            boolean card1lose = doesMonsterLose((MonsterCard) card1, (MonsterCard) card2);
            if (card1lose)
            {
                deck1.remove(Integer.valueOf(card1.getId()));
                deck2.add(card1.getId());
                r+="\n"+card2.getName() + " wins because of a special interaction!";
                return r;
            }
            else
            {
                boolean card2lose = doesMonsterLose((MonsterCard) card1, (MonsterCard) card2);
                if (card2lose)
                {
                    deck2.remove(Integer.valueOf(card2.getId()));
                    deck1.add(card2.getId());
                    r+= "\n" + card1.getName() + " wins because of a special interaction!";
                    return r;
                }
            }


        }
        else
        {
            if (card1 instanceof SpellCard && card2 instanceof MonsterCard)
            {
                if(doesSpellCardLose((SpellCard) card1, (MonsterCard) card2))
                {
                    deck1.remove(Integer.valueOf(card1.getId()));
                    deck2.add(card1.getId());
                    r+= "\n" + card2.getName() + " is immune to the spell and wins!";
                    return r;
                }
            }
            else if (card2 instanceof SpellCard && card1 instanceof MonsterCard)
            {
                if(doesSpellCardLose((SpellCard) card2, (MonsterCard) card1))
                {
                    deck2.remove(Integer.valueOf(card2.getId()));
                    deck1.add(card2.getId());
                    r+= "\n" + card1.getName() + " is immune to the spell and wins!";
                    return r;
                }
            }
        }


        r+="\nAttacker: " + card1.getName() + ", Defender: " + card2.getName();
        float dmg = card1.getBaseDamage();
        if (spellActive) {
            dmg = dmg * resolveElements(card1.getElement(), card2.getElement());
            r+="\n<The elements are in effect! Damage multiplier: " + resolveElements(card1.getElement(), card2.getElement()) + ">";
        }
        r+="\nDealt "+dmg+ " points of damage!";

        r+="\nAttacker: " + card2.getName() + ", Defender: " + card1.getName();
        float dmg2 = card2.getBaseDamage();
        if (spellActive) {
            dmg2 = dmg2 * resolveElements(card2.getElement(), card1.getElement());
            r+="\n<The elements are in effect! Damage multiplier: " + resolveElements(card1.getElement(), card2.getElement()) + ">";
        }
        r+="\nDealt "+dmg2+ " points of damage!";

        if (dmg > dmg2)
        {
            deck2.remove(Integer.valueOf(card2.getId()));
            deck1.add(card2.getId());
            r+= "\n" +card1.getName() + " wins!";
            return r;

        }
        else if (dmg2 > dmg)
        {
            deck1.remove(Integer.valueOf(card1.getId()));
            deck2.add(card1.getId());
            r+="\n" +card2.getName() + " wins!";
            return r;
        }
        else if (dmg == dmg2)
        {
            r+= "It's a draw!";
            return r;
        }

        return "ERROR";

    }


}
