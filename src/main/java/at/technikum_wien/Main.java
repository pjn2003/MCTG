package at.technikum_wien;


import at.technikum_wien.businesslogic.BattleManager;
import at.technikum_wien.models.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {



    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        User testUser = new User("testuser","Test");

        System.out.println("Logging in with password 'abc'");
        testUser.login("Abc");

        System.out.println("Logging in with password 'Test'");
        testUser.login("Test");

        MonsterCard dragon = new MonsterCard("FireDragon",80,Element.Fire,MonsterType.Dragon);
        dragon.Describe();

        SpellCard tornado = new SpellCard("Tornado",70,Element.Normal);
        tornado.Describe();

        BattleManager bm = new BattleManager();



    }
}