package at.technikum_wien;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

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

    public static void Fight(Card card1, Card card2)
    {
        boolean spellActive=false;
        if (card1 instanceof SpellCard || card2 instanceof SpellCard) //CHeck if there is a spell card
        {
            spellActive = true;
        }
        System.out.println("Attacker: " + card1.getName() + ", Defender: " + card2.getName());
        float dmg = card1.baseDamage;
        if (spellActive)
            dmg *= resolveElements(card1.element,card2.element);
        System.out.println("Dealt "+dmg+ " points of damage!");

        System.out.println("Attacker: " + card2.getName() + ", Defender: " + card1.getName());
        float dmg2 = card2.baseDamage;
        if (spellActive)
            dmg *= resolveElements(card2.element,card1.element);
        System.out.println("Dealt "+dmg2+ " points of damage!");

        if (dmg > dmg2)
        {
            System.out.println(card1.name + " wins!");
        }
        else if (dmg2 > dmg)
        {
            System.out.println(card2.name + " wins!");
        }
        else if (dmg == dmg2)
        {
            System.out.println("It's a draw!");
        }

    }

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        User testUser = new User("TestUser","Test");

        System.out.println("Logging in with password 'abc'");
        testUser.Login("Abc");

        System.out.println("Logging in with password 'Test'");
        testUser.Login("Test");

        MonsterCard dragon = new MonsterCard("FireDragon",80,Element.Fire,MonsterType.Dragon);
        dragon.Describe();

        SpellCard tornado = new SpellCard("Tornado",70,Element.Normal);
        tornado.Describe();

        Fight(dragon,tornado);

    }
}