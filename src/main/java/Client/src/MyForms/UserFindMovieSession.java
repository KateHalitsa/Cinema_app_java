package Client.src.MyForms;

import Common.FindResults.*;
import Common.FindResults.FindMovieSessionResult;
import Common.Params.FindFilmsParam;
import Common.Params.FindMovieSessionsParam;
import Common.ServerProxy;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static Common.CommonUtils.*;



public class UserFindMovieSession extends JFrame implements ActionListener, WindowListener {
    private FindMovieSessionsParam contextParam;
    private JButton buttonFind;
    private JPanel mainPanel;
    private JPanel panelDateFrom;
    private JPanel panelDateTo;
    private JPanel panelTimeFrom;
    private JPanel panelTimeTo;
    private JTable filmsTable;
    private JLabel labelFilmYear;
    private JLabel labelFilmLength;
    private JLabel labelFilmActors;
    private JLabel labelFilmDirector;
    private JLabel labelFilmDescription;
    private JLabel labelFilmCategories;
    private JScrollPane scrollBoxDescription;
    private JLabel labelFilmTitle;
    private JScrollPane filmsScrollPanel;
    private JButton buttonAddSession;
    private JButton buttonEditSession;
    private JButton buttonDeleteSession;
    private JButton buttonSessionTickets;
    private JToolBar toolbarEditors;
    private JPanel panelFindByDay;
    private DatePicker dateFromPicker;
    private DatePicker dateToPicker;
    private TimePicker timeFromPicker;
    private TimePicker timeToPicker;

    private static UserFindMovieSession myFrame;
    private boolean inOpenMovieSession = false;

    public UserFindMovieSession(int filmId, FindMovieSessionsParam context)
    {
        super();
        if (context != null) {
            contextParam = context;
        }else
        {
            contextParam = new FindMovieSessionsParam();
            contextParam.filmId = filmId;
        }

        setContentPane(mainPanel);
        tuneFilmSessionsTable();
        TuneEditors();
        TuneFilmDescription();
        tuneToolBar();
        loadSessions();

        setTitle("Выбор сеанса для фильма");
        setIconImage(new ImageIcon("images/ButtonIcons/schedule.png").getImage());
        setSize(910,450);
        setMinimumSize(new Dimension(910, 360));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        myFrame = this;
        myFrame.addWindowListener(this);

        buttonFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loadSessions();
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
                    FindMovieSessionResult value = (FindMovieSessionResult)table.getValueAt(row, 0);
                    if (value != null)
                    {
                        new UserMovieSession(value.movieSessionId, contextParam);
                        inOpenMovieSession = true;
                        myFrame.dispose(); // setVisible(false);
                    }
                }
            }
        });

        buttonAddSession.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSession(0);
            }
        });
    }

    private void TuneFilmDescription()
    {
        FindFilmsParam param = new FindFilmsParam();
        param.filmId = contextParam.filmId;
        List<FindFilmsResult> result = ServerProxy.Server().findFilms(param);
        if (result.size() > 0)
        {
            FindFilmsResult film = result.get(0);
            labelFilmTitle.setText(film.title);

            labelFilmYear.setText(Integer.toString(film.year));
            labelFilmLength.setText(Integer.toString(film.length));

            String categoriesStr = film.categories.toString();
            categoriesStr = categoriesStr.substring(1, categoriesStr.length() - 1);
            labelFilmCategories.setText(categoriesStr);

            String actorsStr = film.actors.toString();
            actorsStr = actorsStr.substring(1, actorsStr.length() - 1);
            labelFilmActors.setText(actorsStr);

            labelFilmDirector.setText(film.director);
            labelFilmDescription.setText("<html><body style='width: 270px'>" + film.description + "</body></html>");

            scrollBoxDescription.getViewport().setBackground(Color.WHITE);
            scrollBoxDescription.setBorder(BorderFactory.createEmptyBorder());

        }
    }

    private void tuneToolBar()
    {
        if (AdminMenu.IsOpened()) {
            buttonAddSession.setIcon(new ImageIcon("images/ButtonIcons/add.png"));
            buttonEditSession.setIcon(new ImageIcon("images/ButtonIcons/edit.png"));
            buttonDeleteSession.setIcon(new ImageIcon("images/ButtonIcons/delete.png"));
            buttonSessionTickets.setIcon(new ImageIcon("images/ButtonIcons/ticket.png"));
        }
        else
        {
            toolbarEditors.setVisible(false);
        }
    }

    private void loadSessions()
    {
        if (dateFromPicker.getDate() != null)
        {
            contextParam.fromDate = java.sql.Date.valueOf(dateFromPicker.getDate());
        }

        if (dateToPicker.getDate() != null)
        {
            contextParam.toDate = java.sql.Date.valueOf(dateToPicker.getDate());
        }

        if (timeFromPicker.getTime() != null)
        {
            contextParam.fromTime = timeFromPicker.getText();
        }

        if (timeToPicker.getTime() != null)
        {
            contextParam.toTime = timeToPicker.getText();
        }

        List<FindMovieSessionResult> movieSessions = ServerProxy.Server().findMovieSessions(contextParam);

        DefaultTableModel tableModel = (DefaultTableModel)filmsTable.getModel();
        tableModel.setRowCount(0);
        for (FindMovieSessionResult session : movieSessions)
        {
            String dateStr = formatDate(session.sessionTime);
            String timeStr =  formatTime(session.sessionTime);
            tableModel.addRow(new Object[]{session, dateStr, timeStr, session.roomName, session.ticketPrice, session.freeSeats});
        }
    }

    private void editSession(int sessionId)
    {
        SessionEditDialog.Open(contextParam.filmId, sessionId);
    }

    public static void main(String[] args)
    {
        /*
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            if (defaults.get("Table.alternateRowColor") == null)
                defaults.put("Table.alternateRowColor", new Color(240, 240, 240)); // Set Zebra Color
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }

        myFrame = new UserFindMovieSession();
         */
    }

    private void TuneEditors()
    {
        dateFromPicker =  addDatePicker(panelDateFrom);
        dateToPicker =  addDatePicker(panelDateTo);

        timeFromPicker = addTimePicker(panelTimeFrom, 0, 0);
        timeToPicker =addTimePicker(panelTimeTo, 23, 59);
    }

    private DatePicker addDatePicker(JPanel panel) {
        Locale locale = new Locale("ru");
        DatePickerSettings settings = new DatePickerSettings(locale);
        // Set a minimum size for the localized date pickers, to improve the look of the demo.
        //settings.setSizeTextFieldMinimumWidth(125);
        //settings.setSizeTextFieldMinimumWidthDefaultOverride(true);

        DatePicker datePicker = new DatePicker(settings);
        //datePicker.setDateToToday();

        try
        {   // Добавить кнопочку с картинкой
            URL dateImageURL = new File("images/DateTimePicker/calendar.png").toURI().toURL();
            Image dateExampleImage = Toolkit.getDefaultToolkit().getImage(dateImageURL);
            ImageIcon dateExampleIcon = new ImageIcon(dateExampleImage);
            JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
            datePickerButton.setText("");
            datePickerButton.setIcon(dateExampleIcon);
        }
        catch (Exception e)
        {
        }

        panel.setLayout(new GridLayout());
        panel.add(datePicker);

        return datePicker;
    }

    private TimePicker addTimePicker(JPanel panel, int hour, int minute)
    {
        // Create the time picker, and apply the image icon.
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.use24HourClockFormat();
        timeSettings.initialTime = LocalTime.of(hour, minute);
        //timeSettings.setSizeTextFieldMinimumWidth(125);
        //timeSettings.setSizeTextFieldMinimumWidthDefaultOverride(true);
        //timeSettings.setDisplaySpinnerButtons(true);
        TimePicker timePicker = new TimePicker(timeSettings);

        try
        {
            URL timeIconURL = new File("images/DateTimePicker/clock.png").toURI().toURL();
            Image timeExampleImage = Toolkit.getDefaultToolkit().getImage(timeIconURL);
            ImageIcon timeExampleIcon = new ImageIcon(timeExampleImage);

            JButton timePickerButton = timePicker.getComponentToggleTimeMenuButton();
            timePickerButton.setText("");
            timePickerButton.setIcon(timeExampleIcon);
        }
        catch (Exception e)
        {
        }

        panel.setLayout(new GridLayout());
        panel.add(timePicker);

        return timePicker;
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
                if (table.getColumnName(column).contains("Дата Сеанса")) {

                    FindMovieSessionResult session = (FindMovieSessionResult) table.getValueAt(row, 0);
                    if (session != null) {
                        Date date = java.sql.Date.valueOf(LocalDate.now());
                        if (session.sessionTime.compareTo(date) < 0) {
                            c.setForeground(Color.RED);
                        }
                    }
                }
            }
            return c;
        }
    }
    private void tuneFilmSessionsTable()
    {
        DefaultTableModel tableModel = (DefaultTableModel)filmsTable.getModel();
        tableModel.addColumn("Object");
        tableModel.addColumn("Дата Сеанса");
        tableModel.addColumn("Время Сеанса");
        tableModel.addColumn("Зал");
        tableModel.addColumn("Цена билета");
        tableModel.addColumn("Свободных мест");

        TableColumn column;
        column = filmsTable.getColumn("Object");
        column.setMaxWidth(0);
        column.setMinWidth(0);
        column.setPreferredWidth(0);
        //column = filmsTable.getColumn("Свободных мест");
        //column.setMaxWidth(160);

        filmsTable.setDefaultEditor(Object.class, null); // Запретить все редакторы ячеек
        filmsTable.setGridColor(new Color(220, 220, 220));
        filmsTable.setIntercellSpacing(new Dimension(3, 3));
        filmsTable.setRowHeight(20);
        filmsTable.setSelectionBackground(new Color(233, 227, 247));

        filmsTable.setDefaultRenderer(Object.class, new CustomTableRenderer());
        filmsScrollPanel.getViewport().setBackground(Color.WHITE);
    }

    private void AddMediaPlayer()
    {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
      if (!inOpenMovieSession)
      {
        UserFindFilms.Open();
      }
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
