package technikum_wien.mtcgapp.dummydata;

import technikum_wien.mtcgapp.models.CardPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyPackages {

    private List<CardPackage> packages = new ArrayList<CardPackage>();
    private DummyCards dummyCards;

    public DummyPackages() {
        ArrayList<Integer> packs = new ArrayList<Integer>();
        dummyCards = new DummyCards();

        Integer[] defaultPack = {1,2,3,4,5,6};
        Integer[] spellPack = {4,5,6};
        Integer[] monsterPack = {1,2,3};
        addPackage("FullPack",defaultPack);
        addPackage("SpellPack", spellPack);
        addPackage("MonsterPack",monsterPack);


    }

    public void addPackage(String name, Integer[] p) {

        CardPackage cardPackage = new CardPackage(name, p);

        this.packages.add(cardPackage);
        //System.out.println(printPackages());
    }

    public String printPackages()
    {
        String result = "";
        for (int i = 0; i < packages.size(); i++) {
            for (int j = 0; j < packages.get(i).getCardList().length; j++)
                result += packages.get(i).getPackName()+ ", Card " + j + ": " + dummyCards.getCard(packages.get(i).getCardList()[j]).getName() + "\n";
        }
        return result;
    }

    public CardPackage getPackage(String packName) {
        for (CardPackage cardPackage : packages) {
            if (cardPackage.getPackName().equals(packName))
                return cardPackage;
        }

        return null;
    }

}
