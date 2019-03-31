package com.mediasoft.course;

import java.sql.DriverManager;
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
}
