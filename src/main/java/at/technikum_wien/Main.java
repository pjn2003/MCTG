package at.technikum_wien;


import at.technikum_wien.businesslogic.BattleManager;
import at.technikum_wien.httpserver.server.Server;
import at.technikum_wien.httpserver.utils.Router;
import at.technikum_wien.models.*;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {



    public static void main(String[] args) {

        Server server = new Server(10001, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Router configureRouter()
    {
        Router router = new Router();
        //router.addService("/users", new UserService());


        return router;
    }


    }
