package Client.src.MyForms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ManageFilms extends JFrame implements ActionListener, WindowListener {
    private JTextField textField1;
    private JList list1;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JLabel labelTitle;
    private JLabel labelYear;
    private JLabel labelMinLegth;
    private JLabel labelCategory;
    private JLabel labelDirector;
    private JLabel labelActors;
    private JTextArea textArea1;
    private JLabel labelDiscription;
    private JLabel labelMin;
    private JList list2;
    private JLabel labelList;
    private JList list3;
    private JComboBox comboBox1;
    private JButton buttonAddFilm;
    private JButton buttonRedact;
    private JButton buttonDelete;
    private JButton buttonAddCategory;
    private JButton buttonAddActor;
    private JButton buttonBack;
    private JList list4;
    Socket sock = null;
    InputStream is = null;
    OutputStream os = null;
    private JPanel mainPanel;
    public static ManageFilms myFrame;
    public ManageFilms(){
        setContentPane(mainPanel);
        setTitle("Управление фильмами");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        myFrame=this;
    }
    public static void main(String[] args) throws IOException {
        myFrame=new ManageFilms();

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
