package at.technikum_wien.models;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {
    String username;

    String password;

    Integer coins;


    public User(String uname, String pass) {
        username = uname;
        password = pass;
        coins = 20;
    }


    public void login(String pass) {
        if (!password.equals(pass)) {
            System.out.println("Password does not match!");
        }
        else
        {
            System.out.println("Verification successful!");
            //Log in
        }
    }




}
