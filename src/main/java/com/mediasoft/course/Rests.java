package com.mediasoft.course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Rests {

    public static Object RestsList() throws SQLException, ClassNotFoundException  {

        Connector connector = new Connector();
        Statement statement1 = connector.ConnectorToBD();
        Statement statement2 = connector.ConnectorToBD();

        ResultSet resultSetCNT = statement1.executeQuery("SELECT COUNT(*) AS total FROM RestsTable");
        ResultSet resultSet = statement2.executeQuery("SELECT * FROM RestsTable");

        System.out.println("\n Таблица рестаранов Ульяновска:");

        connector.OutBD(resultSet, resultSetCNT);

        return null;
    }
}
