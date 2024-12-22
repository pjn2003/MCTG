package technikum_wien;


import org.junit.jupiter.api.Test;
import technikum_wien.httpserver.server.Server;
import technikum_wien.httpserver.utils.Router;
import technikum_wien.mtcgapp.service.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class TestMain {



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

        UserServiceTest userService = new UserServiceTest();
        router.addService("/users", userService);

        router.addService("/sessions", new SessionServiceTest());

        router.addService("/packages", new PackagesServiceTest());

        router.addService("/transactions", new TransactionServiceTest());

        router.addService("/cards", new CardsServiceTest());

        router.addService("/deck", new DeckServiceTest());

        router.addService("/stats", userService);

        router.addService("/scoreboard", new ScoreboardServiceTest());

        router.addService("/battles", new BattleServiceTest());

        router.addService("/tradings", new TradingServiceTest());
        return router;
    }


    }
