package Server.src.ServerWork;


import Server.src.Database.MyDatabase;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ThreadServer extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private MyDatabase database;
private OutputStream os;
    public ThreadServer(Socket s) throws IOException, ClassNotFoundException{
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                .getOutputStream())), true);
        os=s.getOutputStream();
        try {
            database = new MyDatabase("com.mysql.cj.jdbc.Driver",
                    "jdbc:mysql://localhost:3306/cinemas?useUnicode=true&characterEncoding=UTF-8", "root", "sa2003"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Сервер соединен с базой данных");

        start();
    }

    public void run() {

        ServerWork obj = new ServerWork(in, out,os, database);
        try {int idOperation=-1;
            while (true) {
                 idOperation = in.read();
                if (idOperation==-1) {

                    database.close();
                    System.out.println("Сервер отсоединен от базы данных");
                    break;
                }

                obj.getId(idOperation);
            }
            in.close();
            System.out.println("Клиент был отсоединен");
        }
        catch (IOException ex) {
            System.err.println("IO Exception");
        } catch (SQLException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException ex) {
                System.err.println("Socket not closed");
            }
        }
  /*  */ }
}
