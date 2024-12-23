package at.technikum_wien.mtcgapp.models;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Getter @Setter private Integer elo;
    @Getter @Setter private Integer wins;
    @Getter @Setter private Integer losses;

    @Getter @Setter private boolean isAdmin=false;

    private ArrayList<Integer> userCards = new ArrayList<Integer>();
    private ArrayList<Integer> userDeck = new ArrayList<Integer>();

    private Integer[] cardsInStore;





    public User(String uname, String pass) {
        setUsername(uname);
        setPassword(pass);
        coins = 20;
        bio = "";
    }
    public User(String uname, Integer coins, String bio, Integer elo, Integer wins, Integer losses, boolean isAdmin, Integer[] userCards, Integer[] userDeck) {
        setUsername(uname);
        setCoins(coins);
        setBio(bio);
        setElo(elo);
        setWins(wins);
        setLosses(losses);
        setAdmin(isAdmin);
        setUserCards(new ArrayList<>(Arrays.asList(userCards)));
        setUserDeck(new ArrayList<>(Arrays.asList(userDeck)));


    }

    public void describeUser()
    {
        System.out.println("Username: " + getUsername() + "\nCoins: " + getCoins()+ "\nBio: " + getBio() + "\nElo: " +getElo() + "\nWins: " + getWins() + "\nLosses: " + getLosses() + "\nAdmin: " + isAdmin() + "\nCards: " + getUserCards() + "\nDeck: " + getUserDeck());
    }

    public void addCardToInventory(int id)
    {
        userCards.add(id);
    }
    public void addCardToStore(int id)
    {
        cardsInStore[cardsInStore.length-1] = id;
    }
    public void removeCardFromStore(int id)
    {
        for (int i = 0; i < cardsInStore.length; i++)
        {
            if (cardsInStore[i] == id)
                cardsInStore[i]=null;
        }
    }
    public boolean isCardForTrade(int id)
    {
        for (int i = 0; i < cardsInStore.length; i++)
        {
            if (cardsInStore[i] == id)
                return true;
        }
        return false;
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
            if (getCardFromInv(ids[i])!=null && !isCardForTrade(ids[i]))
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
