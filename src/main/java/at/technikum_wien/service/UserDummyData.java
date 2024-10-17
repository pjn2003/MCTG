package at.technikum_wien.service;

import at.technikum_wien.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDummyData {

    private List<User> users;


    public UserDummyData() {
        users = new ArrayList<User>();
        users.add(new User("kienboec", "daniel"));
        users.add(new User("altenhof", "markus"));
        users.add(new User("admin", "istrator"));

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
