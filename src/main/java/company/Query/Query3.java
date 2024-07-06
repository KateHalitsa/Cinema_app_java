package company.Query;

import company.Connection.JDBC;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class Query3 {
    public static void main(String[] args) {
        Statement stmt = null;
        try{

            JDBC.connect();

            stmt = JDBC.connection.createStatement();
            Scanner insert = new Scanner(System.in);
            System.out.println("Enter Last Name");
            String publisher=insert.nextLine();



            String query2Author ="INSERT INTO Publishers (publisherName)" + "VALUES ('" + publisher + "')";

            System.out.println("Show all authors sorted by firstName and lastName");
            stmt.executeUpdate(query2Author);
            //ResultSet rs1 = stmt.executeQuery(query2Author);
          /*  while (rs1.next()) {
                int id = rs1.getInt("authorID");
                String pubName = rs1.getString("firstName");
                String pubName2 = rs1.getString("lastName");
                System.out.println(id + "\t" + pubName+" "+pubName2);
            }*/
            Query1 query1=new Query1();

        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            //finally block used to close resources
            JDBC.close();
        }
    }
}
