package Client.src.MyForms;

import Common.CommonUtils;
import Common.Data.ActorBase;
import Common.Data.FilmActorBase;
import Common.Params.LoadActorsParam;
import Common.ServerProxy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FilmActorsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JToolBar toolBarEditors;
    private JButton buttonAddActor;
    private JButton buttonDeleteActor;
    private JScrollPane actorsScrollPanel;
    private JTable actorsTable;
    private JLabel labelFilmName;
    private int filmId;

    public FilmActorsDialog(int filmId, String filmName) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setTitle("Актеры фильма");
        //buttonOK.setText("Сохранить");
        buttonOK.setVisible(false);
        buttonCancel.setText("Закрыть");
        setIconImage(new ImageIcon("images/ButtonIcons/actors.png").getImage());

        this.filmId = filmId;
        labelFilmName.setText(filmName);

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

        buttonDeleteActor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               onDeleteActor();
            }
        });
        buttonAddActor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAddActor();
            }
        });
        buttonDeleteActor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDeleteActor();
            }
        });
    }

    private void onAddActor()
    {
       ActorBase actor = ManageActorsDialog.Open(filmId);
       if (actor != null)
        {
            FilmActorBase filmActor = new FilmActorBase();
            filmActor.filmId = filmId;
            filmActor.actorId = actor.id;
            ServerProxy.Server().saveFilmActor(filmActor);

            DefaultTableModel tableModel = (DefaultTableModel)actorsTable.getModel();
            tableModel.insertRow(0, new Object[]{actor, actor.name, actor.lastName});
        }
    }

    private void onDeleteActor()
    {
        ActorBase actor = getSelectedActor();
        if (actor != null)
        {
            FilmActorBase filmActor = new FilmActorBase();
            filmActor.filmId = filmId;
            filmActor.actorId = actor.id;
            ServerProxy.Server().deleteFilmActor(filmActor);

            int row = actorsTable.getSelectedRow();
            DefaultTableModel tableModel = (DefaultTableModel)actorsTable.getModel();
            tableModel.removeRow(row);
        }
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

    private void loadActors()
    {
        LoadActorsParam param = new LoadActorsParam();
        param.actorsForFilmId = filmId;

        ArrayList<ActorBase> actors = ServerProxy.Server().loadActors(param);

        DefaultTableModel tableModel = (DefaultTableModel)actorsTable.getModel();
        tableModel.setRowCount(0);
        for (ActorBase actor : actors)
        {
            tableModel.addRow(new Object[]{actor, actor.name, actor.lastName});
        }
    }

    private void tuneToolBar()
    {
        buttonAddActor.setIcon(new ImageIcon("images/ButtonIcons/add.png"));
        buttonDeleteActor.setIcon(new ImageIcon("images/ButtonIcons/delete.png"));
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

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void Open(int filmId, String filmName)
    {
        FilmActorsDialog dialog = new FilmActorsDialog(filmId, filmName);
        dialog.pack();
        dialog.setResizable(false);
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - dialog.getWidth()) / 2;
        final int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        CommonUtils.InitDafaultTheme();
        Open(1, "Ходячий замок");
        System.exit(0);
    }
}
