package at.technikum_wien.mtcgapp.businesslogic;

import at.technikum_wien.mtcgapp.models.Card;
import at.technikum_wien.mtcgapp.models.Element;
import at.technikum_wien.mtcgapp.models.SpellCard;
import at.technikum_wien.mtcgapp.models.User;

public class BattleManager {


    public void initFight(User user1, User user2)
    {
        System.out.println("Starting fight between: " + user1 + " and " + user2);
    }

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




    public void fight(Card card1, Card card2)
    {
        boolean spellActive= card1 instanceof SpellCard || card2 instanceof SpellCard;
        //CHeck if there is a spell card
        System.out.println("Attacker: " + card1.getName() + ", Defender: " + card2.getName());
        float dmg = card1.getBaseDamage();
        if (spellActive)
            dmg *= resolveElements(card1.getElement(),card2.getElement());
        System.out.println("Dealt "+dmg+ " points of damage!");

        System.out.println("Attacker: " + card2.getName() + ", Defender: " + card1.getName());
        float dmg2 = card2.getBaseDamage();
        if (spellActive)
            dmg *= resolveElements(card2.getElement(),card1.getElement());
        System.out.println("Dealt "+dmg2+ " points of damage!");

        if (dmg > dmg2)
        {
            System.out.println(card1.getName() + " wins!");
        }
        else if (dmg2 > dmg)
        {
            System.out.println(card2.getName() + " wins!");
        }
        else if (dmg == dmg2)
        {
            System.out.println("It's a draw!");
        }

    }


}
