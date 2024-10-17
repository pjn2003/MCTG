package at.technikum_wien.mtcgapp.dummydata;

import at.technikum_wien.mtcgapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDummyData {

    private List<User> users;

    //additionalUsers is only used for testing
    public UserDummyData(boolean additionalUsers) {
        users = new ArrayList<User>();
        users.add(new User("neuhold", "paul"));
        users.add(new User("testaccount", "testpw"));
        users.add(new User("admin2", "istrator2"));

        users.get(1).setCoins(0);

        users.get(0).addCardToInventory(1);
        users.get(0).addCardToInventory(5);
        users.get(0).addCardToInventory(4);
        users.get(0).addCardToInventory(2);

        ArrayList<Integer> userDeck = new ArrayList<>();
        userDeck.add(1);
        userDeck.add(5);
        userDeck.add(4);
        userDeck.add(2);
        users.get(0).setUserDeck(userDeck);

        users.get(0).setElo(1000);
        users.get(0).setWins(18);
        users.get(0).setLosses(2);

        users.get(1).setElo(967);
        users.get(2).setElo(9999);

        if (additionalUsers)
        {
            users.add(new User("kienboec", "daniel"));
            users.add(new User("altenhof", "markus"));
            users.add(new User("admin", "istrator"));
        }

    }

    public User getUser(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }

        return null;
    }

    public void editUser(String name, String newBio)
    {
        if (getUser(name) != null) {
            getUser(name).setBio(newBio);
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

}
