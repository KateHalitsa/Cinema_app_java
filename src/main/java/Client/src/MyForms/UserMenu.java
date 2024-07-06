package Client.src.MyForms;

import Common.CommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class UserMenu extends JFrame implements ActionListener, WindowListener {
    private JPanel mainPanel;
    private JButton buttonFindFilms;
    private JButton выходButton;
    public static UserMenu myFrame;

    public UserMenu() {
        setContentPane(mainPanel);
        setTitle("Меню пользователя");
        setIconImage(new ImageIcon("images/ButtonIcons/film.png").getImage());
        buttonFindFilms.setIcon(new ImageIcon("images/ButtonIcons/film.png"));
        setSize(300, 120);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        myFrame=this;

        buttonFindFilms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserFindFilms.Open();
            }
        });

        выходButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.setVisible(false);
                myFrame.dispose();
            }
        });
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
        myFrame=new UserMenu();

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
}
