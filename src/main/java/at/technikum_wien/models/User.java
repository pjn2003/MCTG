package at.technikum_wien.models;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class User {


    @Setter @Getter
    private String username;

    @Setter @Getter
    private String password;

    @Setter @Getter private Integer coins;

    @Setter @Getter private String bio;

    @Getter @Setter private int elo;
    @Getter @Setter private int wins;
    @Getter @Setter private int losses;

    private ArrayList<Integer> userCards = new ArrayList<Integer>();
    private ArrayList<Integer> userDeck = new ArrayList<Integer>();






    public User(String uname, String pass) {
        setUsername(uname);
        setPassword(pass);
        coins = 20;
        bio = "";
    }

    public void addCardToInventory(int id)
    {
        userCards.add(id);
    }

    public Integer getCardFromInv(int id)
    {
        return userCards.get(id);
    }

    public void makeDeck(int [] ids)
    {
        ArrayList<Integer> tempDeck = new ArrayList<Integer>();
        for (int i = 0; i < ids.length; i++) //For each id, check if the user owns that card
        {
            if (getCardFromInv(ids[i])!=null)
            {
                tempDeck.add(ids[i]);
            }
            else
            {
                return;
            }
        }
        userDeck = tempDeck;
    }




}
