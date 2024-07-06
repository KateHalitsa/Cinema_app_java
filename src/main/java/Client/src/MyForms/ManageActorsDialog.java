package Client.src.MyForms;

import Common.CommonUtils;
import Common.Data.ActorBase;
import Common.Params.LoadActorsParam;
import Common.ServerProxy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ManageActorsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JTable actorsTable;
    private JButton buttonAddActor;
    private JButton buttonEditActor;
    private JButton buttonDeleteActor;
    private JToolBar toolBarEditors;
    private JScrollPane actorsScrollPanel;
    private JButton buttonFind;
    private boolean findActorMode;
    private int findActorsForFilmId;

    public ManageActorsDialog(int findActorsForFilmId) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.findActorMode = findActorsForFilmId > 0;
        this.findActorsForFilmId = findActorsForFilmId;

        setMinimumSize(new Dimension(500, 300));
        setTitle("Актеры");
        if (findActorMode)
        {
            buttonOK.setText("Выбрать актера для фильма");
        }else{
            buttonOK.setVisible(false);
        }
        buttonCancel.setText("Закрыть");

        setIconImage(new ImageIcon("images/ButtonIcons/actors.png").getImage());

        tuneActorsTable();
        tuneToolBar();
        loadActors();

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
        buttonFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadActors();
            }
        });
        buttonAddActor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAddActor();
            }
        });
        buttonEditActor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onEditActor();
            }
        });
    }

    private void onEditActor()
    {
        ActorBase actor = getSelectedActor();
        if (actor != null)
        {
            actor = ManageActorEditorDialog.Open(actor);
            if (actor != null)
            {
                DefaultTableModel tableModel = (DefaultTableModel)actorsTable.getModel();
                int row = actorsTable.getSelectedRow();
                tableModel.setValueAt(actor, row, 0);
                tableModel.setValueAt(actor.name, row, 1);
                tableModel.setValueAt(actor.lastName, row, 2);
            }
        }
    }

    private void onAddActor()
    {
        ActorBase actor = ManageActorEditorDialog.Open(null);
        if (actor != null)
        {
            DefaultTableModel tableModel = (DefaultTableModel)actorsTable.getModel();
            tableModel.insertRow(0, new Object[]{actor, actor.name, actor.lastName});
        }
    }

    private void tuneToolBar()
    {
        toolBarEditors.setVisible(!findActorMode);
        buttonAddActor.setIcon(new ImageIcon("images/ButtonIcons/add.png"));
        buttonEditActor.setIcon(new ImageIcon("images/ButtonIcons/edit.png"));
        buttonDeleteActor.setIcon(new ImageIcon("images/ButtonIcons/delete.png"));
    }

    private ActorBase getSelectedActor()
    {
        ActorBase result = null;
        int row = actorsTable.getSelectedRow();
        if (row >= 0)
        {
            result = (ActorBase)actorsTable.getValueAt(row, 0);
        }
        return result;
    }

    private void tuneActorsTable()
    {
        DefaultTableModel tableModel = (DefaultTableModel)actorsTable.getModel();
        tableModel.addColumn("Object");
        tableModel.addColumn("Имя Актера");
        tableModel.addColumn("Фамилия Актера");

        TableColumn column;
        column = actorsTable.getColumn("Object");
        column.setMaxWidth(0);
        column.setMinWidth(0);
        column.setPreferredWidth(0);

        actorsTable.setDefaultEditor(Object.class, null); // Запретить все редакторы ячеек
        actorsTable.setGridColor(new Color(220, 220, 220));
        actorsTable.setIntercellSpacing(new Dimension(3, 3));
        actorsTable.setRowHeight(20);
        actorsTable.setSelectionBackground(new Color(233, 227, 247));
        actorsTable.getTableHeader().setFont(actorsTable.getFont());
        actorsTable.setSelectionForeground(Color.BLACK);

        actorsScrollPanel.getViewport().setBackground(Color.WHITE);
    }

    private void loadActors()
    {
        LoadActorsParam param = new LoadActorsParam();
        param.name = textFieldFirstName.getText().trim();
        param.lastName = textFieldLastName.getText().trim();
        param.withoutActorsFromFilmId = findActorsForFilmId;

        ArrayList<ActorBase> actors = ServerProxy.Server().loadActors(param);

        DefaultTableModel tableModel = (DefaultTableModel)actorsTable.getModel();
        tableModel.setRowCount(0);
        for (ActorBase actor : actors)
        {
            tableModel.addRow(new Object[]{actor, actor.name, actor.lastName});
        }
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        actorsTable.clearSelection();
        dispose();
    }

    public static ActorBase Open(int findActorsForFilmId)
    {
        ManageActorsDialog dialog = new ManageActorsDialog(findActorsForFilmId);
        dialog.pack();
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - dialog.getWidth()) / 2;
        final int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);
        dialog.setVisible(true);
        return dialog.getSelectedActor();
    }

    public static void main(String[] args)
    {
        CommonUtils.InitDafaultTheme();
        Open(0);
        System.exit(0);
    }
}
