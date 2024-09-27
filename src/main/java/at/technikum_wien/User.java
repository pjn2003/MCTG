package at.technikum_wien;

public class User {
    String username;
    String password;

    int coins;

    public User(String uname, String pass) {
        username = uname;
        password = pass;
        coins = 20;
    }


    public void Login(String pass) {
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
