package com.mediasoft.course.places;

import com.mediasoft.course.Connector;
import com.mediasoft.course.OutputBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cinemas {

    public static void CinemasList() throws SQLException, ClassNotFoundException  {

        Connector connector = new Connector();
        Statement statement1 = connector.ConnectorToBD();
        Statement statement2 = connector.ConnectorToBD();

        ResultSet resultSetCNT = statement1.executeQuery("SELECT COUNT(*) AS total FROM CinemasTable");
        ResultSet resultSet = statement2.executeQuery("SELECT * FROM CinemasTable");

        System.out.println("\n Таблица кинотеатров Ульяновска:");

        OutputBD outputBD = new OutputBD();
        outputBD.OutBD(resultSet, resultSetCNT);
    }
}