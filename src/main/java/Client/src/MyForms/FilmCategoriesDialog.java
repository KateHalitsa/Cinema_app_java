package Client.src.MyForms;

import Client.src.ClientWork.JCheckBoxList;
import Common.CommonUtils;
import Common.Data.Category;
import Common.Params.LoadCategoriesParam;
import Common.Params.UpdateFilmCategoriesParam;
import Common.ServerProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class FilmCategoriesDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel panelFilmCategories;
    private JLabel labelFilmName;
    private int filmId;
    private ArrayList<Integer> filmCategories = null;
    private JCheckBoxList checkBoxList = null;

    public FilmCategoriesDialog(int filmId, String filmName)
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setTitle("Жанры фильма");
        buttonOK.setText("Сохранить");
        buttonCancel.setText("Отменить");
        setIconImage(new ImageIcon("images/ButtonIcons/categories.png").getImage());
        // setSize(880,500);
        setMinimumSize(new Dimension(300, 200));

        this.filmId = filmId;
        labelFilmName.setText(filmName);

        tuneControls();

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

    private void tuneControls()
    {
        ArrayList<Category> categories = ServerProxy.Server().loadCategories(new LoadCategoriesParam());
        filmCategories = ServerProxy.Server().loadFilmCategories(filmId);

        DefaultListModel<JCheckBox> model = new DefaultListModel<JCheckBox>();
        checkBoxList = new JCheckBoxList(model);
        for (int i = 0; i < categories.size(); i++)
        {
            Category category = categories.get(i);
            JCheckBox checkBox = new JCheckBox(category.name);

            boolean isChecked = filmCategories.contains(category.id);
            checkBox.setSelected(isChecked);
            checkBox.setActionCommand(Integer.toString(category.id));

            model.addElement(checkBox);
        }

        panelFilmCategories.setLayout(new GridLayout());
        panelFilmCategories.add(checkBoxList);
    }

    private void onOK() {

        ArrayList<Integer> addedIds = new ArrayList<>();
        ArrayList<Integer> deletedIds = new ArrayList<>();

        ListModel<JCheckBox> model = checkBoxList.getModel();
        for (int i = 0; i < model.getSize(); i++)
        {
            JCheckBox checkBox = model.getElementAt(i);
            int categoryId = Integer.parseInt(checkBox.getActionCommand());
            if (checkBox.isSelected())
            {
                if (!filmCategories.contains(categoryId))
                {
                    addedIds.add(categoryId);
                }
            }
            else
            {
                if (filmCategories.contains(categoryId))
                {
                    deletedIds.add(categoryId);
                }
            }
        }

        if (!addedIds.isEmpty() || !deletedIds.isEmpty())
        {
            UpdateFilmCategoriesParam param = new UpdateFilmCategoriesParam();
            param.filmId = filmId;
            param.addedCategoryIds = addedIds;
            param.deletedCategoryIds = deletedIds;
            ServerProxy.Server().updateFilmCategories(param);
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static int Open(int filmId, String filmName)
    {
        FilmCategoriesDialog dialog = new FilmCategoriesDialog(filmId, filmName);
        dialog.pack();
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - dialog.getWidth()) / 2;
        final int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);
        dialog.setVisible(true);
        return 0;
    }

    public static void main(String[] args)
    {
        CommonUtils.InitDafaultTheme();
        Open(1, "Ходячий замок");
        System.exit(0);
    }
}
