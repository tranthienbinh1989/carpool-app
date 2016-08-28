package com.mum.carpooling.ultil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Asaad Saad
 * @25 Aug 2016 A very simple example of JDBC connection to a MySQL
 * database. Uses same technique taught in the FPP course. Does not do any
 * connection management or pooling, just opens and closes the connection for
 * each request.
 */
public class DBConnection {

    String dburl = "jdbc:mysql://localhost:3306/carpoolingdb";

    public DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("MySQL JDBC driver not found in DBConnection\n" + ex);
            System.exit(0);
        }
    }

    String retrieveUserFullname(String email) {
        String readQuery = "SELECT fullname from users where email = '" + email + "';";
        String fullname = "No information found for the requested user: " + email;

        try (Connection con = DriverManager.getConnection(dburl, "root", "mumsql");
                Statement stmt = con.createStatement();) {

            System.out.println("the query: " + readQuery);
            ResultSet rs = stmt.executeQuery(readQuery);
            while (rs.next()) {
                fullname = rs.getString("fullname");
                System.out.println("User Fullname: " + fullname);
            }
            stmt.close();

        } catch (SQLException s) {
            System.out.println("Exception thrown in retrieveUser ....");
            s.printStackTrace();
        }

        return fullname;

    }

    public String mockRetrieveUserFullname(String email) {
        String fullname = "no definition found";
        switch (email) {
            case "asaad@mum.edu":
                fullname = "Asaad Saad";
                break;
            default: fullname = "CS472 Student";
                break;
        }
        return fullname;
    }

}