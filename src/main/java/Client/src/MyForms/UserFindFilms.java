package Client.src.MyForms;

import Common.CommonUtils;
import Common.Data.ActorBase;
import Common.Data.Category;
import Common.Data.FilmBase;
import Common.FindResults.FindFilmsResult;
import Common.Params.FindFilmsParam;
import Common.Params.LoadActorsParam;
import Common.Params.LoadCategoriesParam;
import Common.ServerProxy;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import static Common.CommonUtils.*;

public class UserFindFilms extends JFrame implements ActionListener, WindowListener {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel resultsPanel;
    private JTextField textFilmName;
    private JButton buttonFind;
    private JComboBox comboBoxCategories;
    private JTable filmsTable;
    private JFormattedTextField textFromYear;
    private JFormattedTextField textToYear;
    private JScrollPane filmsScrollPanel;
    private JButton buttonAddFilm;
    private JButton buttonEditFilm;
    private JButton buttonDeleteFilm;
    private JButton buttonFilmSchedule;
    private JButton buttonFilmActors;
    private JButton buttonFilmCategories;
    private JToolBar toolbarEditors;
    private JScrollPane scrollTablePanel;
    public static UserFindFilms myFrame;

    public UserFindFilms(boolean forTestOnly)
    {

        setContentPane(mainPanel);
        TuneFilmsTable();
        tuneEditors();
        tuneToolBar();
        loadFilms();

        setTitle("Фильмы");
        setIconImage(new ImageIcon("images/ButtonIcons/film.png").getImage());
        setSize(880,400);
        setMinimumSize(new Dimension(600, 300));
        if(forTestOnly) {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }else
        {
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
        setLocationRelativeTo(null);
        setVisible(true);
        myFrame = this;

        buttonFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFilms();
            }
        });

        filmsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //super.mousePressed(e);
                JTable table =(JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    FindFilmsResult value = (FindFilmsResult)table.getValueAt(row, 0);
                    if (value != null)
                    {
                        OpenFindMovieSessions(value.id);
                    }
                }
            }
        });
        buttonAddFilm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ediFilm(0);
            }
        });
        buttonFilmCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onFilmCategoriesClick();
            }
        });
        buttonFilmSchedule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onFilmScheduleClick();
            }
        });
        buttonFilmActors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onFilmActors();
            }
        });
        buttonEditFilm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onEditFilm();
            }
        });
    }

    private void onEditFilm()
    {
        FindFilmsResult film = getSelectedFilm();
        if (film != null)
        {
            ediFilm(film.id);
        }
    }

    private void onFilmActors()
    {
        FindFilmsResult film = getSelectedFilm();
        if (film != null)
        {
            FilmActorsDialog.Open(film.id, film.title);
            LoadActorsParam param = new LoadActorsParam();
            param.actorsForFilmId = film.id;
            ArrayList<ActorBase> actors = ServerProxy.Server().loadActors(param);

            String actorsStr =  actors.toString();
            actorsStr = actorsStr.substring(1, actorsStr.length() - 1);

            int row = filmsTable.getSelectedRow();
            DefaultTableModel tableModel = (DefaultTableModel) filmsTable.getModel();
            tableModel.setValueAt(actorsStr, row, 2);
        }
    }

    private void OpenFindMovieSessions(int filmId)
    {
        new UserFindMovieSession(filmId, null);
        myFrame.setVisible(false);
    }

    private void onFilmScheduleClick()
    {
        FindFilmsResult film = getSelectedFilm();
        if (film != null)
        {
            OpenFindMovieSessions(film.id);
        }
    }

    private void onFilmCategoriesClick()
    {
        FindFilmsResult film = getSelectedFilm();
        if (film != null)
        {
            FilmCategoriesDialog.Open(film.id, film.title);

            LoadCategoriesParam param = new LoadCategoriesParam();
            param.filmId = film.id;
            ArrayList<Category> categories = ServerProxy.Server().loadCategories(param);

            String categoriesStr =  categories.toString();
            categoriesStr = categoriesStr.substring(1, categoriesStr.length() - 1);

            int row = filmsTable.getSelectedRow();
            DefaultTableModel tableModel = (DefaultTableModel) filmsTable.getModel();
            tableModel.setValueAt(categoriesStr, row, 3);
        }
    }

    private void ediFilm(int filmId)
    {
        FilmBase film = FilmEditDialog.Open(filmId);
        if (film != null)
        {
            FindFilmsParam param = new FindFilmsParam();
            param.filmId = film.id;
            List<FindFilmsResult> films = ServerProxy.Server().findFilms(param);

            if(!films.isEmpty())
            {
                DefaultTableModel tableModel = (DefaultTableModel) filmsTable.getModel();
                if (filmId == 0)
                {
                    tableModel.insertRow(0, filmToObjectArray(films.get(0)));
                }
                else
                {
                    int row = filmsTable.getSelectedRow();
                    Object[] data = filmToObjectArray(films.get(0));
                    for(int i = 0; i < tableModel.getColumnCount(); i++)
                    {
                        tableModel.setValueAt(data[i], row, i);
                    }
                }
            }

        }
    }

    private void tuneToolBar()
    {
        if (AdminMenu.IsOpened())
        {
            buttonAddFilm.setIcon(new ImageIcon("images/ButtonIcons/add.png"));
            buttonEditFilm.setIcon(new ImageIcon("images/ButtonIcons/edit.png"));
            buttonDeleteFilm.setIcon(new ImageIcon("images/ButtonIcons/delete.png"));
            buttonFilmSchedule.setIcon(new ImageIcon("images/ButtonIcons/schedule.png"));
            buttonFilmActors.setIcon(new ImageIcon("images/ButtonIcons/actors.png"));
            buttonFilmCategories.setIcon(new ImageIcon("images/ButtonIcons/categories.png"));
        }
        else
        {
            toolbarEditors.setVisible(false);
        }

    }

    private FindFilmsResult getSelectedFilm()
    {
        FindFilmsResult result = null;
        int row = filmsTable.getSelectedRow();
        if (row >= 0)
        {
            result = (FindFilmsResult)filmsTable.getValueAt(row, 0);
        }
        return result;
    }

    private void loadFilms()
    {
        FindFilmsParam param = new FindFilmsParam();
        param.filmName = textFilmName.getText().trim();
        if (!textFromYear.getText().trim().isEmpty())
        {
            param.fromYear = strToInt(textFromYear.getText());
        }
        if (!textToYear.getText().trim().isEmpty())
        {
            param.toYear = strToInt(textToYear.getText());
        }
        Object selectedCategory = comboBoxCategories.getSelectedItem();
        if (selectedCategory!= null)
        {
            Category category = (Category)selectedCategory;
            param.filmCategory = category.id;
        }
        List<FindFilmsResult> films = ServerProxy.Server().findFilms(param);

        DefaultTableModel tableModel = (DefaultTableModel)filmsTable.getModel();
        tableModel.setRowCount(0);

        for (FindFilmsResult film : films)
        {
            tableModel.addRow(filmToObjectArray(film));
        }

        if (tableModel.getRowCount() > 0)
        {
            filmsTable.setRowSelectionInterval(0, 0);
        }
        updateToolbarButtonsEnabled();
    }

    private Object[] filmToObjectArray(FindFilmsResult film)
    {
        String actorsStr = film.actors.toString();
        actorsStr = actorsStr.substring(1, actorsStr.length() - 1);

        String categoriesStr = film.categories.toString();
        categoriesStr = categoriesStr.substring(1, categoriesStr.length() - 1);

        String sessionDateStr = "";
        if (film.nearestFutureSessionDate != null)
        {
            sessionDateStr = formatDateTime(film.nearestFutureSessionDate);
        }
        else if (film.nearestPastSessionDate != null)
        {
            sessionDateStr = formatDateTime(film.nearestPastSessionDate);
        }
        return new Object[]{film, film.title, actorsStr, categoriesStr, film.year, sessionDateStr};
    }

    public static void Open()
    {
        if ((myFrame != null) && myFrame.isVisible())
        {
            if (myFrame.getState() == JFrame.ICONIFIED) {
                myFrame.setState(JFrame.NORMAL);
            }
            myFrame.toFront();
            myFrame.repaint();
        }
        else {
            boolean forTestOnly = !AdminMenu.IsOpened() && !UserMenu.IsOpened();
            myFrame = new UserFindFilms(forTestOnly);
        }
    }

    public static void main(String[] args)
    {
        CommonUtils.InitDafaultTheme();
        Open();
    }

    private static class CustomTableRenderer extends DefaultTableCellRenderer {

        // You should override getTableCellRendererComponent
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);

            c.setForeground(Color.BLACK);

            if (row >= 0) {
                if (table.getColumnName(column).contains("сеанс")) {

                    FindFilmsResult film = (FindFilmsResult) table.getValueAt(row, 0);
                    if (film != null) {
                        if (film.nearestFutureSessionDate == null) {
                            c.setForeground(Color.RED);
                        }
                    }
                }
            }
            return c;
        }
    }
    private void TuneFilmsTable()
    {
        DefaultTableModel tableModel = (DefaultTableModel)filmsTable.getModel();
        tableModel.addColumn("Object");
        tableModel.addColumn("Имя фильма");
        tableModel.addColumn("Актеры");
        tableModel.addColumn("Жанр фильма");
        tableModel.addColumn("Год выпуска");
        tableModel.addColumn("Ближайший сеанс");

        TableColumn column;
        column = filmsTable.getColumn("Object");
        column.setMaxWidth(0);
        column.setMinWidth(0);
        column.setPreferredWidth(0);
        column = filmsTable.getColumn("Год выпуска");
        column.setMaxWidth(80);
        column.setMinWidth(80);
        column.setPreferredWidth(80);
        column = filmsTable.getColumn("Ближайший сеанс");
        column.setMaxWidth(140);
        column.setMinWidth(140);
        column.setPreferredWidth(140);

        filmsTable.setDefaultEditor(Object.class, null); // Запретить все редакторы ячеек
        filmsTable.setGridColor(new Color(220, 220, 220));
        filmsTable.setIntercellSpacing(new Dimension(3, 3));
        filmsTable.setRowHeight(20);
        filmsTable.setSelectionBackground(new Color(233, 227, 247));

        filmsTable.setDefaultRenderer(Object.class, new CustomTableRenderer());
        filmsScrollPanel.getViewport().setBackground(Color.WHITE);

        filmsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = filmsTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                handleTableSelectionEvent(e);
            }
        });

    }

    private void handleTableSelectionEvent(ListSelectionEvent e)
    {   if (e.getValueIsAdjusting())
            return;
        updateToolbarButtonsEnabled();
    }

    private void updateToolbarButtonsEnabled()
    {
         boolean rowSeleted = filmsTable.getSelectedRow() >= 0;
         buttonEditFilm.setEnabled(rowSeleted);
         buttonDeleteFilm.setEnabled(rowSeleted);
         buttonFilmSchedule.setEnabled(rowSeleted);
         buttonFilmActors.setEnabled(rowSeleted);
         buttonFilmCategories.setEnabled(rowSeleted);
    }

    private void tuneEditors()
    {
        NumberFormat nf = NumberFormat.getIntegerInstance(); // Specify specific format here.
        NumberFormatter nff = new NumberFormatter(nf);
        nff.setAllowsInvalid(true);
        nff.setMinimum(0);
        DefaultFormatterFactory factory = new DefaultFormatterFactory(nff);
        textFromYear.setFormatterFactory(factory);
        textToYear.setFormatterFactory(factory);

        List<Category> categories = ServerProxy.Server().loadCategories(new LoadCategoriesParam());
        categories.add(0, null); // First Empty Category
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        comboBoxModel.addAll(categories);
        comboBoxCategories.setModel(comboBoxModel);

    }


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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
