package at.technikum_wien.mtcgapp.controller;

import at.technikum_wien.mtcgapp.dummydata.DummyPackages;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.mtcgapp.models.CardPackage;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;


public class PackagesController extends Controller{

    private DummyPackages dummyPackages;
    public PackagesController() {
        this.dummyPackages = new DummyPackages();
    }


    //Obsolete
    public Response getPacks()
    {
        String result = this.dummyPackages.printPackages();
        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"message\" : \"These packs are currently available:\"\n%s }".formatted(result)
        );

    }

    public Response createPack(CardPackage pack)
    {
        try {


            Integer maxId = 0;

            String firstquery = "SELECT MAX(id) FROM cardpacks";
            PreparedStatement fps = connect().prepareStatement(firstquery);
            ResultSet rs = fps.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt(1)+1;
            }
            else
            {
                maxId = 1;
            }

            String query = "INSERT INTO cardpacks (id, packname, card_list) VALUES (?, ?, ?)";
            Connection con = connect();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, maxId);
            ps.setString(2, pack.getPackName());
            ps.setObject(3,pack.getCardList());

            ps.executeUpdate();

            return new Response(
                    HttpStatus.CREATED,
                    ContentType.JSON,
                    "{ \"message\" : \"Pack created successfully.\" }"
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }




}
