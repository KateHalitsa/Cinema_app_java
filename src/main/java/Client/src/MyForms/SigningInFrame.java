package Client.src.MyForms;


import Common.CommonUtils;
import Server.src.Data.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.lang.reflect.Modifier;

import java.net.Socket;


public class SigningInFrame extends JFrame implements ActionListener, WindowListener {

    private JLabel LabelLogin;
    private JLabel LabelPassword;
    private JTextField textLogin;
    private JTextField textPassword;
    private JButton buttonEnter;
    private JButton buttonRegistrate;
    private JPanel mainPanel;
    Socket sock = null;
    InputStream is = null;
    OutputStream os = null;
    static SigningInFrame myFrame=null;
    public SigningInFrame() {

        setContentPane(mainPanel);
        setTitle("Авторизация");
        setIconImage(new ImageIcon("images/ButtonIcons/film.png").getImage());
        setMinimumSize(new Dimension(340, 134));
        setResizable(false);

        // Расположить в центре экрана
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - this.getWidth()) / 2;
        final int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        buttonEnter.addActionListener(al);
        buttonRegistrate.addActionListener(al2);
        myFrame=this;
    }

    public static void main(String[] args) throws IOException
    {
        CommonUtils.InitDafaultTheme();
        myFrame = new SigningInFrame();
    }

    ActionListener al = new ActionListener() { //событие на нажатие кнопки

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                sock=new Socket("localhost", 4004);

                is = sock.getInputStream(); // входной поток для чтения данных
                os = sock.getOutputStream();// выходной поток для записи данных
                int numOperation=1;
                os.write(numOperation);

                User user=new User(textLogin.getText(),textPassword.getText(),0);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                        .create();
                String json = gson.toJson(user).replace("\n","")+"\n";

                os.write(json.getBytes()); // отправляем введенные данные. Тип string переводим в byte

                int answer=is.read();

switch (answer)
{
    case 1:
        myFrame.setVisible(false);
        UserMenu userMenu = new UserMenu();
        userMenu.setVisible(true);
        userMenu.setLocationRelativeTo(null);

    break;
    case 2:
        myFrame.setVisible(false);
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.setVisible(true);
        adminMenu.setLocationRelativeTo(null);
    break;
    default:
    {
        JOptionPane.showMessageDialog(null, "Неверно введены логин/пароль!",
                "Ошибка авторизации!",JOptionPane.ERROR_MESSAGE);
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
            RegistrationFrame reg0 = new RegistrationFrame();
            reg0.setVisible(false);
            RegistrationFrame reg = new RegistrationFrame(myFrame,reg0);
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

    public class ButtonActionListener implements ActionListener  {



        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                sock=new Socket("localhost", 4004);
                is = sock.getInputStream(); // входной поток для чтения данных
                os = sock.getOutputStream();// выходной поток для записи данных

                User user=new User(textLogin.getText(),textPassword.getText(),0);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                        .create();
                String json = gson.toJson(user);

                os.write(json.getBytes()); // отправляем введенные данные. Тип string переводим в byte
                byte[] bytes = new byte[1024];
                is.read(bytes); //получаем назад информацию,которую послал сервер
                String str = new String(bytes, "UTF-8"); // переводим тип byte в String

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

      /*  private void ButtonActionListener() {//GEN-FIRST:event_jButton2ActionPerformed
            try {
                sock=new Socket("localhost", 4004);
                is = sock.getInputStream(); // входной поток для чтения данных
                os = sock.getOutputStream();// выходной поток для записи данных

                User user=new User(textLogin.getText(),textPassword.getText(),0);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                        .create();
                String json = gson.toJson(user);

                os.write(json.getBytes()); // отправляем введенные данные. Тип string переводим в byte
                byte[] bytes = new byte[1024];
                is.read(bytes); //получаем назад информацию,которую послал сервер
                String str = new String(bytes, "UTF-8"); // переводим тип byte в String

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
            }*/
          /*  try {
                SocketStream server = new SocketStream();
                server.sendInt(1);
                server.sendString(jTextField1.getText());
                server.sendString(new String(jPasswordField1.getPassword()));
                String rez = server.getString();
                if(rez.equals("error")){
                    JOptionPane.showMessageDialog(null, "Неверно введены логин/пароль!",
                            "Ошибка авторизации!",JOptionPane.ERROR_MESSAGE);
                }else{
                    switch(rez){
                        case "0":
                            this.setVisible(false);
                            MainUserFrame user = new MainUserFrame();
                            user.setVisible(true);
                            user.setLocationRelativeTo(null);
                            break;
                        case "1":
                            this.setVisible(false);
                            MainAdminFrame admin = new MainAdminFrame();
                            admin.setVisible(true);
                            admin.setLocationRelativeTo(null);
                            break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(SigningInFrame.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }//GEN-LAST:event_jButton2ActionPerformed

    };












