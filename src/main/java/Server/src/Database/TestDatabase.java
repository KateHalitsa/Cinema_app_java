package Server.src.Database;



import java.sql.SQLException;

public class TestDatabase {
    public static void main(String[] argv) {

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            Database.JDBC.connect();
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return;
        }

        Database.JDBC.close();
    }

}
