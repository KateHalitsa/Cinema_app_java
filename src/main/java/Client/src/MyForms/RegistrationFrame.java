package Client.src.MyForms;

import javax.swing.*;

import Server.src.Data.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


//import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.Socket;
public class RegistrationFrame extends JFrame implements ActionListener, WindowListener{
    private JTextArea textLog;
    private JButton buttonBack;
    private JButton buttonReg;
    private JTextArea textPass;
    private JLabel labelLog;
    private JLabel labelPass;
    private JPanel mainPanel;
    Socket sock = null;
    InputStream is = null;
    OutputStream os = null;
    SigningInFrame reg=null;
    public RegistrationFrame(){


        setContentPane(mainPanel);
        setTitle("Регистрация");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        buttonBack.addActionListener(al2);
        buttonReg.addActionListener(al);
    }
    public RegistrationFrame(SigningInFrame reg,RegistrationFrame res){
        this.reg=reg;
        this.myFrame=res;
        setContentPane(mainPanel);
        setTitle("Регистрация");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        buttonBack.addActionListener(al2);
        buttonReg.addActionListener(al);
    }
    public static RegistrationFrame myFrame;
    public static void main(String[] args) throws IOException {
      myFrame=new RegistrationFrame();

    }

    ActionListener al = new ActionListener() { //событие на нажатие кнопки

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                sock=new Socket("localhost", 4004);

                is = sock.getInputStream(); // входной поток для чтения данных
                os = sock.getOutputStream();// выходной поток для записи данных
                int numOperation=2;
                os.write(numOperation);

                User user=new User(textLog.getText(),textPass.getText(),1);
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

                switch (answer)
                {
                    case 1:
                        myFrame.setVisible(false);
                        UserMenu userMenu = new UserMenu();
                        userMenu.setVisible(true);
                        userMenu.setLocationRelativeTo(null);

                        break;

                    default:
                    {
                        JOptionPane.showMessageDialog(null, "Такой логин уже занят",
                                "Логин занят!",JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }

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


        } };
    ActionListener al2 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            myFrame.setVisible(false);


            reg.setVisible(true);
            reg.setLocationRelativeTo(null);


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
