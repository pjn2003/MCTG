package at.technikum_wien.mtcgapp.models;


import lombok.Getter;
import lombok.Setter;

public abstract class Card {

    @Getter @Setter private int id;

    @Getter @Setter private String name;



    @Getter @Setter private int baseDamage;

    @Getter @Setter private Element element;


    public void describe()
    {
        System.out.println("This is a card.");
    }









}
