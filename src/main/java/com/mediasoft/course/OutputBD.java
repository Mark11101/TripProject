package com.mediasoft.course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OutputBD {
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
