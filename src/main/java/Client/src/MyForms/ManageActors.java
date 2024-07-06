package Client.src.MyForms;

import Server.src.Data.Actor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ManageActors extends JFrame implements ActionListener, WindowListener, ItemListener {

    private JPanel mainPanel;
    private JLabel labelName;
    private JTextArea textLog;
    private JTextArea textPass;
    private JLabel labelLastName;
    private JLabel labelList;
    protected List<Actor> lcs;
    private DefaultListModel mylist=new DefaultListModel<>();
    private JList list1;


    private JList<String> list2;
    private  JButton buttonBack;
    private  JButton buttonAdd;
    private  JButton buttonDelete;
    private  JButton buttonRedact;
    private JScrollPane scrollPane;
    static Socket sock = null;
    static InputStream is = null;
    static OutputStream os = null;
    static BufferedReader in = null;
    private PrintWriter out= null;
    public static ManageActors myFrame;

   private void upDateList(){
       try {
           sock=new Socket("localhost", 4004);
           in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

           is = sock.getInputStream(); // входной поток для чтения данных
           os = sock.getOutputStream();// выходной поток для записи данных

           int numOperation=7;
           os.write(numOperation);


           Gson gson = new GsonBuilder()
                   .setPrettyPrinting()
                   .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                   .create();
           // отправляем введенные данные. Тип string переводим в byte
           byte[] bytes = new byte[3000];
              /* is.read(bytes); //получаем назад информацию,которую послал сервер
                String str = new String(bytes, "UTF-8"); // переводим тип byte в String
            */
           String str=in.readLine();
           /* java.lang.reflect.Type collectionType = new TypeToken<Actor>(){}.getType();
            Actor actor = (Actor) new Gson().fromJson( str , collectionType);*/
           java.lang.reflect.Type collectionType = new TypeToken<List<Actor>>(){}.getType();
           lcs =  new Gson().fromJson( str , collectionType);
           //  list1=new JList((ListModel) lcs);
           String[] list;

           DefaultListModel listModel = new DefaultListModel();
           mylist=listModel;
           for (int i=0;i<lcs.size();i++){
               String s=lcs.get(i).getName()+" "+lcs.get(i).lastName();

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
    public ManageActors(){
        setContentPane(mainPanel);
        setTitle("Управление актерами");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        upDateList();
        //list1 = new JList<>(mylist);
         /**/ buttonBack.addActionListener(al);
        buttonAdd.addActionListener(al2);
        buttonRedact.addActionListener(al3);
        buttonDelete.addActionListener(al4);
        list1.addListSelectionListener(listLisener);
        myFrame=this;

    }
    public static void main(String[] args) throws IOException {
        myFrame=new ManageActors();

    }
    protected int index=-1;
    ListSelectionListener listLisener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent listSelectionEvent) {


            index=list1.getAnchorSelectionIndex();

            if(index<lcs.size()) {
                textLog.setText(lcs.get(index).getName());
                textPass.setText(lcs.get(index).lastName());
            }
            else {
                textLog.setText("");
                textPass.setText("");
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
                String query1 = "insert into actor(first_name,last_name) " + "values(\'"+textLog.getText()+"\',\'"+textPass.getText()+"\')"+"\n";
            int numOperation=4;

                os.write(numOperation);
                os.write(query1.getBytes()); // отправляем введенные данные. Тип string переводим в byte

                upDateList();

            } catch (IOException ex) {
            throw new RuntimeException(ex);
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
            /* String sql= "UPDATE fixed_assets SET `name`='"+name1+"', `price`='"+price1+"', `term_of_use`='"+term1+
                    "' WHERE `name` ='"+name+"' AND `price` = '"+price+"' AND `term_of_use`='"+term+"'";
                    UPDATE tovar SET price=500 WHERE id=5*/
                String query1 = " UPDATE actor SET `first_name`= '" + textLog.getText()+"',`last_name`='"+textPass.getText()
                        +"' WHERE `first_name`= '"+lcs.get(index).getName()+"' AND`last_name`='"+lcs.get(index).lastName()+"'\n";
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
                Actor actor=lcs.get(index);
                os.write(numOperation);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                        .create();
                String json = gson.toJson(actor).replace("\n","")+"\n";

                os.write(json.getBytes());

                upDateList();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


    };

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
