package technikum_wien;


import technikum_wien.mtcgapp.businesslogic.BattleManagerTest;
import technikum_wien.mtcgapp.dbtests.UserTests;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class TestMain {

    static BattleManagerTest bmt = new BattleManagerTest();
    UserTests us = new UserTests();

    public static void main(String[] args) {

        bmt.testEffective();
        bmt.testNotEffective();
        bmt.testSameElement();
        bmt.testMonsters();
        bmt.conductBattle();

    }




    }
