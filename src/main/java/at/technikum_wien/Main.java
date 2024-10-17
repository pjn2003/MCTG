package at.technikum_wien;


import at.technikum_wien.httpserver.server.Server;
import at.technikum_wien.httpserver.utils.Router;
import at.technikum_wien.mtcgapp.service.*;

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

    //Create router and add services
    private static Router configureRouter()
    {
        Router router = new Router();

        router.addService("/hello", new HelloService()); //Only for testing purposes

        UserService userService = new UserService();
        router.addService("/users", userService);

        router.addService("/sessions", new SessionService());

        router.addService("/packages", new PackagesService());

        router.addService("/transactions", new TransactionService());

        router.addService("/cards", new CardsService());

        router.addService("/deck", new DeckService());

        router.addService("/stats", userService);

        router.addService("/scoreboard", new ScoreboardService());

        router.addService("/battles", new BattleService());

        router.addService("/tradings", new TradingService());
        return router;
    }


    }
