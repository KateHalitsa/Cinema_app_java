package Client.src.MyForms;

import Common.CommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class AdminMenu extends JFrame implements ActionListener, WindowListener {
    private JButton buttonUsers;
    private JButton buttonFilm;
    private JButton buttonSession;
    private JButton buttonHall;
    private JButton buttonActor;
    private JPanel mainPanel;
    private JButton buttonCategories;
    private JButton buttonReport1;
    private JButton buttonReport;
    private JButton buttonExite;
    private static AdminMenu myFrame=null;
    public AdminMenu(){

        setContentPane(mainPanel);
        setTitle("Меню администратора");
        setIconImage(new ImageIcon("images/ButtonIcons/film.png").getImage());
        setSize(450,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buttonActor.setIcon(new ImageIcon("images/ButtonIcons/actors.png"));
        buttonFilm.setIcon(new ImageIcon("images/ButtonIcons/film.png"));
        setVisible(true);

       /**/ buttonUsers.addActionListener(al);
        buttonFilm.addActionListener(al2);
        buttonSession.setVisible(false); //buttonSession.addActionListener(al3);
        buttonHall.addActionListener(al4);
        buttonActor.addActionListener(al5);
        buttonCategories.addActionListener(al6);
        buttonExite.addActionListener(al7);
        myFrame=this;
    }
    public static boolean IsOpened()
    {
        if (myFrame != null)
        {
          return myFrame.isVisible();
        }
        else
        {
            return false;
        }
    }

    public static void main(String[] args) throws IOException
    {
        CommonUtils.InitDafaultTheme();
        myFrame=new AdminMenu();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            myFrame.setVisible(false);
            ManageUsers manageUsers = new ManageUsers();
            manageUsers.setVisible(true);
            manageUsers.setLocationRelativeTo(null);
        }

        };
    ActionListener al2 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            UserFindFilms.Open();
            /*
            myFrame.setVisible(false);
            ManageFilms manageFilms = new ManageFilms();
            manageFilms.setVisible(true);
            manageFilms.setLocationRelativeTo(null);
             */
        }

    };
    ActionListener al3 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            myFrame.setVisible(false);
            ManageSessions manageSessions = new ManageSessions();
            manageSessions.setVisible(true);
            manageSessions.setLocationRelativeTo(null);
        }

    };
    ActionListener al4 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            myFrame.setVisible(false);
            ManageHalls manageHalls = new ManageHalls();
            manageHalls.setVisible(true);
            manageHalls.setLocationRelativeTo(null);
        }

    };
    ActionListener al5 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            ManageActorsDialog.Open(0);
            /*
            myFrame.setVisible(false);
            ManageActors manageActors = new ManageActors();
            manageActors.setVisible(true);
            manageActors.setLocationRelativeTo(null);
             */
            //myFrame.dispose();
        }


    };

    ActionListener al6 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.setVisible(false);
                RedactCategories redactCategories = new RedactCategories();
                redactCategories.setVisible(true);
                redactCategories.setLocationRelativeTo(null);
            } };
    ActionListener al7 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            myFrame.setVisible(false);
            myFrame.dispose();
        } };

}
