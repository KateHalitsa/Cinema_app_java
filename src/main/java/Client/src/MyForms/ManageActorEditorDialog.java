package Client.src.MyForms;

import Common.CommonUtils;
import Common.Data.ActorBase;
import Common.ServerProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ManageActorEditorDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldActorName;
    private JTextField textFieldActorLastName;
    private ActorBase actor;
    public ManageActorEditorDialog(ActorBase actor) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        if (actor == null)
        {
            actor = new ActorBase();
        }
        this.actor = actor;
        fillControls();

        setTitle("Актер");
        buttonOK.setText("Сохранить");
        buttonCancel.setText("Отменить");
        setIconImage(new ImageIcon("images/ButtonIcons/actors.png").getImage());

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void fillControls()
    {
        textFieldActorName.setText(actor.name);
        textFieldActorLastName.setText(actor.lastName);
    }
    private void fillObject()
    {
        actor.name = textFieldActorName.getText().trim();
        actor.lastName = textFieldActorLastName.getText().trim();
    }

    private void onOK() {
        fillObject();
        actor = ServerProxy.Server().saveActor(actor);
        dispose();
    }

    private void onCancel() {
        actor = null;
        dispose();
    }

    public static ActorBase Open(ActorBase actor)
    {
        ManageActorEditorDialog dialog = new ManageActorEditorDialog(actor);
        dialog.pack();
        dialog.setResizable(false);
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - dialog.getWidth()) / 2;
        final int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);
        dialog.setVisible(true);
        return dialog.actor;
    }

    public static void main(String[] args)
    {
        CommonUtils.InitDafaultTheme();
        Open(null);
        System.exit(0);
    }
}
