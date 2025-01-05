package at.technikum_wien.mtcgapp.controller;

import at.technikum_wien.mtcgapp.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.mtcgapp.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ScoreboardController extends Controller {


    public Response getScoreboard()
    {
        try {
            Connection con = connect();
            System.out.println("Scoreboard: ");
            String query = "SELECT * FROM mtcguser ORDER BY elo DESC";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("ELO - Username - W/L\n");
            int counter = 1;
            StringBuilder res = new StringBuilder();
            while (rs.next()) {
                res.append(counter).append(". ").append(rs.getString("elo")).append(" - ").append(rs.getString("username")).append(" - ").append(rs.getInt("wins")).append("/").append(rs.getInt("losses")).append("\n");
                counter++;
            }
            System.out.println(res);
            rs.close();

            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    "{ \"message\" : \"Scoreboard:\"\n%s }".formatted(res)
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
