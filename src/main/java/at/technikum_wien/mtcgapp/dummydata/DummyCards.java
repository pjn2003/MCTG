package at.technikum_wien.mtcgapp.dummydata;

import at.technikum_wien.mtcgapp.models.*;

import java.util.ArrayList;
import java.util.List;

public class DummyCards {



    private MonsterCard dragon = new MonsterCard(1,"Fire Dragon",15, Element.Fire, MonsterType.Dragon);
    private MonsterCard elf = new MonsterCard(2,"Wood Elf", 8, Element.Normal,MonsterType.Elf);
    private MonsterCard goblin = new MonsterCard(3,"Goblin",3,Element.Normal,MonsterType.Goblin);

    private SpellCard fireball = new SpellCard(4,"Fireball",8,Element.Fire);
    private SpellCard blizzard = new SpellCard(5,"Blizzard",12,Element.Water);
    private SpellCard poke = new SpellCard(6, "Poke",1,Element.Normal);

    private List<Card> dummyCards = new ArrayList<Card>();

    public DummyCards()
    {
        this.dummyCards.add(dragon);
        this.dummyCards.add(elf);
        this.dummyCards.add(goblin);
        this.dummyCards.add(fireball);
        this.dummyCards.add(blizzard);
        this.dummyCards.add(poke);
    }

    public Card getCard(int id)
    {
        for (Card card : dummyCards)
        {
            if (card.getId() == id)
                return card;
        }

        return null;
    }




}
