package at.technikum_wien.businesslogic;

import at.technikum_wien.models.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class CardPackage {

    @Getter @Setter
    private String packName;

    @Getter @Setter
    private List<Integer> cardList = new ArrayList<Integer>(); //List of cards in the package

    public CardPackage(String packName, List<Integer> cardList) {
        this.packName = packName;
        this.cardList = cardList;
    }


}
