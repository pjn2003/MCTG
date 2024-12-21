package at.technikum_wien.mtcgapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Controller {

    private String connString = "jdbc:postgresql://localhost:5432/mtcgdb?user=postgres&password=postgres";

    public Connection connect() throws SQLException{
        return DriverManager.getConnection(connString);
    }

    private ObjectMapper objectMapper;

    public Controller() {
        this.objectMapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
