package com.mediasoft.course;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {
    public Statement ConnectorToBD() throws SQLException, ClassNotFoundException {

        String connectionURL = "jdbc:mysql://localhost:3306/Bars_schema";
        String userName = "roman";
        String password = "I1l1l1i1o1n";

        Class.forName("com.mysql.cj.jdbc.Driver");
        java.sql.Connection conn = DriverManager.getConnection(connectionURL, userName, password);

        return conn.createStatement();
    }

    static int y;
    static String[] elements = null;

    public void OutBD(ResultSet resultSet, ResultSet resultSetCNT) throws SQLException {

        resultSetCNT.next();
        y = resultSetCNT.getInt("total");

        Menu menu = new Menu();
        menu.Header();

        elements = new String[y];

        int i=0;
        while (resultSet.next()) {

            elements[i] = " " + resultSet.getInt(1) + " | "
                              + resultSet.getString(2) + " |  "
                              + resultSet.getString(3) + "  | "
                              + resultSet.getString(4);

            System.out.println(elements[i]);
            i++;
        }

        System.out.println("\n ----------------------------------------------------------------------------");
    }
}
