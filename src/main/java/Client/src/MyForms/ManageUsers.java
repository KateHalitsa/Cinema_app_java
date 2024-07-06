package Client.src.MyForms;

import Common.Data.User;
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

public class ManageUsers extends JFrame implements ActionListener, WindowListener {
    private JLabel labelLog;
    private JTextArea textLog;
    private JTextArea textPass;
    private JLabel labelPass;
    private JButton buttonBack;
    private JButton buttonAdd;
    private JComboBox comboBox1;
    private JList list1;
    private JButton buttonDelete;
    private JButton buttonRedact;
    private JLabel labelRole;
    private JLabel labelList;
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    Socket sock = null;
    InputStream is = null;
    OutputStream os = null;
    static BufferedReader in = null;
    private DefaultListModel mylist=new DefaultListModel<>();

    protected List<User> lcs;
    protected int index=-1;
    static String[] box1 = {"Зритель", "Сотрудник"};
    private void upDateList(){
        try {
            sock=new Socket("localhost", 4004);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            is = sock.getInputStream(); // входной поток для чтения данных
            os = sock.getOutputStream();// выходной поток для записи данных

            int numOperation=3;
            os.write(numOperation);

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                    .create();
            // отправляем введенные данные. Тип string переводим в byte
            //byte[] bytes = new byte[3000];

            String str=in.readLine();

            java.lang.reflect.Type collectionType = new TypeToken<List<User>>(){}.getType();
            lcs =  new Gson().fromJson( str , collectionType);
            //  list1=new JList((ListModel) lcs);
            String[] list;

            DefaultListModel listModel = new DefaultListModel();
            mylist=listModel;
            for (int i=0;i<lcs.size();i++){
                String s=lcs.get(i).getLogin();

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
    public ManageUsers(){

        setContentPane(mainPanel);
        setTitle("Управление пользователями");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        upDateList();
        comboBox1.addItem(box1[0]);
        comboBox1.addItem(box1[1]);
        buttonBack.addActionListener(al);
        buttonAdd.addActionListener(al2);
        buttonRedact.addActionListener(al3);
        buttonDelete.addActionListener(al4);
        list1.addListSelectionListener(listLisener);
        myFrame=this;
       /* buttonBack.addActionListener(al2);
        buttonReg.addActionListener(al);*/
    }
    public static ManageUsers myFrame;
    public static void main(String[] args) throws IOException {
        myFrame=new ManageUsers();

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

                is = sock.getInputStream(); // входной поток для чтения данных
                os = sock.getOutputStream();// выходной поток для записи данных
                int numOperation=2;
                Server.src.Data.User user;
                os.write(numOperation);
if(comboBox1.getSelectedIndex()==0){
                 user=new Server.src.Data.User(textLog.getText(),textPass.getText(),1);
}
else{
     user=new Server.src.Data.User(textLog.getText(),textPass.getText(),2);
}
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                        .create();
                String json = gson.toJson(user).replace("\n","")+"\n";

                os.write(json.getBytes()); // отправляем введенные данные. Тип string переводим в byte
                /*byte[] bytes = new byte[1024];
                is.read(bytes); //получаем назад информацию,которую послал сервер
                String str = new String(bytes, "UTF-8"); // переводим тип byte в String*/
                int answer=is.read();

                if (answer==-1)
                {

                        JOptionPane.showMessageDialog(null, "Такой логин уже занят",
                                "Логин занят!",JOptionPane.ERROR_MESSAGE);

                }
                upDateList();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    os.close();//закрытие выходного потока
                    is.close();//закрытие входного потока
                    sock.close();//закрытие сокета, выделенного для работы с сервером
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }


        }

    };
    ActionListener al3 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                sock=new Socket("localhost", 4004);
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                is = sock.getInputStream(); // входной поток для чтения данных
                os = sock.getOutputStream();// выходной поток для записи данных

                String query1 = " UPDATE actor SET `first_name`= '" + textLog.getText()+"',`last_name`='"+textPass.getText()
                        +"' WHERE `first_name`= '"+lcs.get(index).getLogin()+"' AND`last_name`='"+lcs.get(index).getPassword()+"'\n";
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


                int numOperation=6;
                User user=lcs.get(index);
                os.write(numOperation);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                        .create();
                String json = gson.toJson(user).replace("\n","")+"\n";

                os.write(json.getBytes());

                upDateList();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


    };
    ListSelectionListener listLisener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent listSelectionEvent) {


            index=list1.getAnchorSelectionIndex();

            if(index<lcs.size()) {
                textLog.setText(lcs.get(index).getLogin());
                textPass.setText(lcs.get(index).getPassword());
                switch (lcs.get(index).getStatus())
                {
                    case 1:
                        comboBox1.setSelectedIndex(0);
                        break;
                    case 2:
                        comboBox1.setSelectedIndex(1);
                        break;
                    default: break;
                }
            }
            else {
                textLog.setText("");
                textPass.setText("");
            }
        }
    };
}
