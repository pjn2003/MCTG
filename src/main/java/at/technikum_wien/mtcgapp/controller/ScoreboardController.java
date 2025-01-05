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
            String query = "SELECT * FROM mtcguser ORDER BY elo DESC";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int counter = 1;
            StringBuilder res = new StringBuilder();
            res.append("ELO - Username - W/L\n");
            while (rs.next()) {
                res.append(counter).append(". ").append(rs.getString("elo")).append(" - ").append(rs.getString("username")).append(" - ").append(rs.getInt("wins")).append("/").append(rs.getInt("losses")).append("\n");
                counter++;
            }
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
