package Client.src.MyForms;


import Common.Data.Hall;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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

public class ManageHalls extends JFrame implements ActionListener, WindowListener {

    private JTextField textField1;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JList list1;
    private JButton buttonAdd;
    private JButton buttonRedact;
    private JButton buttonDelete;
    private JButton buttonBack;
    private JLabel labelName;
    private JLabel labelRows;
    private JLabel labelSeats;
    private JLabel labelList;
    Socket sock = null;
    InputStream is = null;
    OutputStream os = null;
    static BufferedReader in = null;
    private JPanel mainPanel;
    private JPanel scrollPane;
    public static ManageHalls myFrame;
    private DefaultListModel mylist=new DefaultListModel<>();
    protected List<Hall> lcs;
    private void upDateList(){
        try {
            sock=new Socket("localhost", 4004);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            is = sock.getInputStream(); // входной поток для чтения данных
            os = sock.getOutputStream();// выходной поток для записи данных

            int numOperation=10;
            os.write(numOperation);


            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                    .create();
            // отправляем введенные данные. Тип string переводим в byte
            //byte[] bytes = new byte[3000];

            String str=in.readLine();

            java.lang.reflect.Type collectionType = new TypeToken<List<Hall>>(){}.getType();
            lcs =  new Gson().fromJson( str , collectionType);
            //  list1=new JList((ListModel) lcs);
            String[] list;

            DefaultListModel listModel = new DefaultListModel();
            mylist=listModel;
            for (int i=0;i<lcs.size();i++){
                String s=lcs.get(i).getName();

                mylist.addElement(s);

            }

            list1.setModel(mylist);


        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                os.close();//закрытие выходного потока
                is.close();//закрытие входного потока
                in.close();
                sock.close();//закрытие сокета, выделенного для работы с сервером
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }/**/
    }
    public ManageHalls(){
        setContentPane(mainPanel);
        setTitle("Управление залами");
        setSize(450,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        upDateList();
        buttonBack.addActionListener(al);
        buttonAdd.addActionListener(al2);
        buttonRedact.addActionListener(al3);
        buttonDelete.addActionListener(al4);
        list1.addListSelectionListener(listLisener);
        myFrame=this;
    }

    public static void main(String[] args) throws IOException {
        myFrame=new ManageHalls();

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
               /* String updateTitlesTable = "INSERT INTO Titles (isbn, title, editionNumber, year, publisherID, price)"
                        + "VALUES ('" + isbn[i] + "', '" + title[i] + "', " + editionNumber[i] + ",'" + year[i] + "',"
                        + publisherID[i] + "," + price[i] + ")";*/
                String query1 = "INSERT INTO cinema_room(name,`rows`,seats)" + "VALUES ('"+textField1.getText()+"', "+spinner1.getValue()+", "+spinner2.getValue()+")\n";
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
                String query1 = " UPDATE cinema_room SET name= '" + textField1.getText()+"',`rows`="+spinner1.getValue()+
                        ", seats="+spinner2.getValue()
                        +" WHERE name= '"+lcs.get(index).getName()+
                        "'\n";
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


                int numOperation=11;
                Hall hall=lcs.get(index);
                os.write(numOperation);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                        .create();
                String json = gson.toJson(hall).replace("\n","")+"\n";

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
                textField1.setText(lcs.get(index).getName());
                spinner1.setValue(lcs.get(index).getRows());
                spinner2.setValue(lcs.get(index).getSeats());

            }
            else {
                textField1.setText("");
                spinner1.setValue(0);
                spinner2.setValue(0);
            }
        }
    };
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
}
