package Server.src.Database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyDatabase implements Database.DatabaseInterface {
    private String drivName ;
    private Connection connect;
    private Statement statemnt;
    public MyDatabase(String drivName, String url, String name, String pass) throws ClassNotFoundException, SQLException {
        this.drivName = drivName;
        Class.forName(drivName);
        this.connect = DriverManager.getConnection
                (url,name,pass);
        this.statemnt=this.connect.createStatement();
        this.statemnt.execute("set character set utf8");
        this.statemnt.execute("set names utf8");
    }

    @Override
    public void insert(String sql) {
        try {

            statemnt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MyDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(String sql) {
        try {
            statemnt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MyDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(String sql) {
        try {
            statemnt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MyDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ResultSet select(String sql) {
        ResultSet rs = null;
        try {
           // rs.next();
            rs = statemnt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MyDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }


    public ResultSet select(PreparedStatement ps)  {

        ResultSet rs= null;
        try {
            rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rs;
    }

    @Override
    public void close() {
        try {
            connect.close();
            statemnt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MyDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Connection getConnection(){
        return this.connect;
    }
}
