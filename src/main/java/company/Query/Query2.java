package company.Query;

import company.Connection.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Query2 {
    public static void main(String[] args) {
        Statement stmt = null;
        try{

            JDBC.connect();

            stmt = JDBC.connection.createStatement();
            String query2Author = "SELECT * FROM authors ORDER BY firstName ASC, lastName ASC ";

            System.out.println("Show all authors sorted by firstName and lastName");

            ResultSet rs1 = stmt.executeQuery(query2Author);
            while (rs1.next()) {
                int id = rs1.getInt("authorID");
                String pubName = rs1.getString("firstName");
                String pubName2 = rs1.getString("lastName");
                System.out.println(id + "\t" + pubName+" "+pubName2);
            }

        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            //finally block used to close resources
            JDBC.close();
        }
    }
}
