package Client.src.MyForms;

import Common.Data.Category;
import Common.Params.LoadCategoriesParam;
import Common.ServerProxy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.util.List;

public class RedactCategories extends JFrame implements ActionListener, WindowListener {
    private JButton buttonAdd;
    private JButton buttonRedect;
    private JButton buttonDelete;
    private JList list1;
    private JButton buttonBack;
    private JPanel mainPanel;
    private JTextField textField1;
    private JLabel labelName;
    private JLabel labelList;
    private JScrollPane scrollPane;
    Socket sock = null;
    InputStream is = null;
    OutputStream os = null;
    static BufferedReader in = null;

    private DefaultListModel mylist=new DefaultListModel<>();
    protected List<Category> lcs;
    public static RedactCategories myFrame;
    public RedactCategories(){

        setContentPane(mainPanel);
        setTitle("Управление категорями");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        upDateList();
       buttonBack.addActionListener(al);
        buttonAdd.addActionListener(al2);
        buttonRedect.addActionListener(al3);
        buttonDelete.addActionListener(al4);
        list1.addListSelectionListener(listLisener);
        myFrame=this;
    }
    private void upDateList(){
        lcs = ServerProxy.Server().loadCategories(new LoadCategoriesParam());
        DefaultListModel listModel = new DefaultListModel();
        listModel.addAll(lcs);

        mylist=listModel;
        list1.setModel(mylist);
    }
    public static void main(String[] args) throws IOException {
        myFrame=new RedactCategories();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent we) {
        if (sock != null && !sock.isClosed()) { // если сокет не пустой и сокет открыт
            try {
                sock.close(); // сокет  закрывается
            } catch (IOException e) {
            }
        }
        this.dispose();
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
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.setVisible(true);
            adminMenu.setLocationRelativeTo(null);
        }

    };
    ActionListener al2 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                sock=new Socket("localhost", 4004);
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                is = sock.getInputStream(); // входной поток для чтения данных
                os = sock.getOutputStream();// выходной поток для записи данных
            /* String sql= "UPDATE fixed_assets SET `name`='"+name1+"', `price`='"+price1+"', `term_of_use`='"+term1+
                    "' WHERE `name` ='"+name+"' AND `price` = '"+price+"' AND `term_of_use`='"+term+"'";*/
                String query1 = "insert into category(name) " + "values(\'"+textField1.getText()+"\')"+"\n";
                int numOperation=4;

                os.write(numOperation);
                os.write(query1.getBytes()); // отправляем введенные данные. Тип string переводим в byte

                upDateList();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    };
    int index=-1;
    ActionListener al3 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                sock=new Socket("localhost", 4004);
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                is = sock.getInputStream(); // входной поток для чтения данных
                os = sock.getOutputStream();// выходной поток для записи данных
            /* String sql= "UPDATE fixed_assets SET `name`='"+name1+"', `price`='"+price1+"', `term_of_use`='"+term1+
                    "' WHERE `name` ='"+name+"' AND `price` = '"+price+"' AND `term_of_use`='"+term+"'";
                    UPDATE tovar SET price=500 WHERE id=5*/
                String query1 = " UPDATE category SET `name`= '" + textField1.getText()
                        +"' WHERE `name`= '" + lcs.get(index).name + "'\n";
                int numOperation=5;

                os.write(numOperation);
                os.write(query1.getBytes()); // отправляем введенные данные. Тип string переводим в byte
                upDateList();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    };
    ActionListener al4 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                sock=new Socket("localhost", 4004);
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                is = sock.getInputStream(); // входной поток для чтения данных
                os = sock.getOutputStream();// выходной поток для записи данных


                int numOperation=8;
                Category category=lcs.get(index);
                os.write(numOperation);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                        .create();
                String json = gson.toJson(category).replace("\n","")+"\n";

                os.write(json.getBytes());

                upDateList();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


    };
    ListSelectionListener listLisener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            // index=-1;

            index=list1.getAnchorSelectionIndex();

            if(index<lcs.size()) {
                textField1.setText(lcs.get(index).name);

            }
            else {
                textField1.setText("");

            }
        }
    };

}
