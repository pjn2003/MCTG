package at.technikum_wien.mtcgapp.models;

import lombok.Getter;
import lombok.Setter;

public class CardPackage {

    @Getter @Setter
    private String packName;

    @Getter @Setter
    private Integer[] cardList; //List of cards in the package

    public CardPackage(String packName, Integer[] cardList) {
        this.packName = packName;
        this.cardList = cardList;
    }

    public String getPack()
    {
        String result = getPackName() + "\n";
        for (int i = 0; i < cardList.length; i++)
            result += cardList[i] + "\n";
        return result;
    }


}
