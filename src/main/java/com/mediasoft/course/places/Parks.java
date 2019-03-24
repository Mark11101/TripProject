package com.mediasoft.course.places;

import com.mediasoft.course.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Parks {

    public static Object ParksList() throws SQLException, ClassNotFoundException  {

        Connector connector = new Connector();
        Statement statement1 = connector.ConnectorToBD();
        Statement statement2 = connector.ConnectorToBD();

        ResultSet resultSetCNT = statement1.executeQuery("SELECT COUNT(*) AS total FROM ParkTable");
        ResultSet resultSet = statement2.executeQuery("SELECT * FROM ParkTable");

        System.out.println("\n Таблица парков Ульяновска:");

        connector.OutBD(resultSet, resultSetCNT);

        return null;
    }
}
