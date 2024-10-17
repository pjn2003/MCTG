package at.technikum_wien.models;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {


    @Setter @Getter
    private String username;

    @Setter @Getter
    private String password;

    @Setter @Getter private Integer coins;

    @Setter @Getter private String bio;

    public User(String uname, String pass) {
        setUsername(uname);
        setPassword(pass);
        coins = 20;
        bio = "";
    }






}
