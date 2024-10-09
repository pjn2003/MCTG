package at.technikum_wien.models;


import lombok.Getter;
import lombok.Setter;

public abstract class Card {


    @Getter @Setter private String name;



    @Getter @Setter private int baseDamage;

    @Getter @Setter private Element element;


    public void Describe()
    {
        System.out.println("This is a card.");
    }









}
