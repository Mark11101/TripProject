package com.mediasoft.course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TCenters {

    public static Object TCentersList() throws SQLException, ClassNotFoundException  {

        Connector connector = new Connector();
        Statement statement1 = connector.ConnectorToBD();
        Statement statement2 = connector.ConnectorToBD();

        ResultSet resultSetCNT = statement1.executeQuery("SELECT COUNT(*) AS total FROM TCTable");
        ResultSet resultSet = statement2.executeQuery("SELECT * FROM TCTable");

        System.out.println("\n Таблица торговых центров Ульяновска:");

        connector.OutBD(resultSet, resultSetCNT);

        return null;
    }
}