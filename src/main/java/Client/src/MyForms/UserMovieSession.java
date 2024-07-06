package Client.src.MyForms;

import Client.src.ClientWork.HallTable;
import Client.src.ClientWork.*;
import Common.Data.TicketBase;
import Common.FindResults.LoadMovieSessionInfoResult;
import Common.Params.FindMovieSessionsParam;
import Common.Params.FindTicketsParam;
import Common.Params.UpdateMovieSessionTicketsParam;
import Common.ServerProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static Common.CommonUtils.*;

public class UserMovieSession  extends JFrame implements ActionListener, WindowListener, HallTableTicketsChanged {
    public UserMovieSession myFrame;
    private JPanel mainPanel;
    private JButton buttonClose;
    private JButton buttonSave;
    private JPanel panelTitle;
    private JPanel panelBody;
    private JPanel panelHallTable;
    private JLabel labelLegendTitle;
    private JLabel labelLegend1;
    private JLabel labelLegend2;
    private JLabel labelLegend3;
    private JLabel labelLegend4;
    private JPanel panelSessionFinished;
    private JLabel labelAddedTickets;
    private JLabel labelOrderedTicketsTitle;
    private JLabel labelOrderedTickets;
    private JLabel labelCancelledTicketsTitle;
    private JLabel labelCancelledTickets;
    private JLabel labelAddedTicketsTitle;
    private JLabel labelTicketPrice;
    private JLabel labelFilmName;
    private JLabel labelRoomName;
    private JLabel labelSessionTime;
    private HallTable hallTable;
    private LoadMovieSessionInfoResult movieSessionInfo;

    FindMovieSessionsParam returnContextParam = null;

    public UserMovieSession(int movieSessionId, FindMovieSessionsParam returnContextParam) {

        this.returnContextParam = returnContextParam;

        setContentPane(mainPanel);

        setTitle("Заказ билетов на сеанс");
        setIconImage(new ImageIcon("images/ButtonIcons/ticket.png").getImage());
        setSize(950, 520);
        setMinimumSize(new Dimension(700, 400));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        myFrame = this;
        myFrame.addWindowListener(this);

        FindTicketsParam param = new FindTicketsParam();
        param.movieSessionId = movieSessionId;
        movieSessionInfo = ServerProxy.Server().loadMovieSessionInfo(param);
        initInterface();

        setLocationRelativeTo(null);
        setVisible(true);

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndReloadTickets();
            }
        });
        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                myFrame.setVisible(false);
                returnToFindMovieSession();
            }
        });
    }

    private void initInterface() {
        boolean sessionFinished = timeIsOver(movieSessionInfo.sessionTime);

        buttonSave.setVisible(!sessionFinished);

        if (hallTable == null)
        {
            panelHallTable.setLayout(new GridLayout());
            hallTable = new HallTable(panelHallTable, movieSessionInfo.roomRows, movieSessionInfo.roomSeats);
            hallTable.addListener(this);
        }
        hallTable.setReadOnly(sessionFinished);

        fillTableCells();
        ShowTicketStatistics();

        panelSessionFinished.setVisible(sessionFinished);
        tuneLegendLabels(sessionFinished);
    }
    private void fillTableCells()
    {
        JTable table = hallTable.table;
        for (int i = 0; i < table.getColumnCount(); i++)
        {
            for (int j = 0; j < table.getRowCount(); j++)
            {
               table.setValueAt(null, j, i);
            }
        }

        ArrayList<TicketBase> tickets = movieSessionInfo.ticktes;
        for (int i = 0; i < tickets.size() ; i++)
        {
            TicketBase ticket = tickets.get(i);
            ImageIcon icon;
            if(isMyTicket(ticket)){
              icon = hallTable.myIcon;
            }else{
              icon = hallTable.otherIcon;
            }
            table.setValueAt(icon, ticket.row - 1, ticket.seat - 1);
        }

    }
    private void saveAndReloadTickets()
    {
        if (!timeIsOver(movieSessionInfo.sessionTime))
        {
            UpdateMovieSessionTicketsParam param = new UpdateMovieSessionTicketsParam();
            param.movieSessionId = movieSessionInfo.sessionId;
            param.userId = getCurrentUserID();
            fillTicketsUpdate(param.insertTickets, param.deleteTickets);
            param.needReturnAllTickets = true;
            movieSessionInfo.ticktes = ServerProxy.Server().updateMovieSessionTickets(param);
        }
        else
        {
            FindTicketsParam param = new FindTicketsParam();
            param.movieSessionId = movieSessionInfo.sessionId;
            movieSessionInfo.ticktes = ServerProxy.Server().findTickets(param);
        }
        initInterface();
    }

    private void fillTicketsUpdate(ArrayList<UpdateMovieSessionTicketsParam.SeatRow> insertTickets, ArrayList<Integer> deleteTickets)
    {
        JTable table = hallTable.table;
        for (int i = 0; i < table.getColumnCount(); i++)
        {
            for (int j = 0; j < table.getRowCount(); j++)
            {
                Object val = table.getValueAt(j, i);
                if (val != null)
                {
                    if (val == hallTable.plusIcon)
                    {
                        UpdateMovieSessionTicketsParam.SeatRow info = new UpdateMovieSessionTicketsParam.SeatRow();
                        info.Row = j + 1;
                        info.Seat = i + 1;
                        insertTickets.add(info);
                    } else if (val == hallTable.minusIcon)
                    {
                        int ticketId = getTicketIdByRowSeat(movieSessionInfo.ticktes, j + 1, i + 1);
                        deleteTickets.add(ticketId);
                    }
                }
            }
        }
    }

    private int getTicketIdByRowSeat(ArrayList<TicketBase> ticktes, int row, int seat)
    {
        int result = -1;
        for (int i = 0; i < ticktes.size(); i++)
        {
            TicketBase ticket = ticktes.get(i);
            if ((ticket.row == row) && ((ticket.seat == seat)))
            {
                result = ticket.id;
                break;
            }
        }
        return result;
    }

    private boolean isMyTicket(TicketBase ticket)
    {
        return ticket.userId == getCurrentUserID();
    }

    private void tuneLegendLabels(boolean sessionFinished)
    {
        labelLegend1.setIcon(hallTable.otherIcon);
        labelLegend1.setText("- билеты других зрителей");

        labelLegend2.setIcon(hallTable.myIcon);
        labelLegend2.setText("- закаазанные мною");

        labelLegend3.setIcon(hallTable.minusIcon);
        labelLegend3.setText("- отмененные сейчас");
        labelLegend3.setVisible(!sessionFinished);

        labelLegend4.setIcon(hallTable.plusIcon);
        labelLegend4.setText("- добавленные сейчас");
        labelLegend4.setVisible(!sessionFinished);

        labelCancelledTicketsTitle.setVisible(!sessionFinished);
        labelCancelledTickets.setVisible(!sessionFinished);

        labelAddedTicketsTitle.setVisible(!sessionFinished);
        labelAddedTickets.setVisible(!sessionFinished);

        Format format = NumberFormat.getCurrencyInstance(new Locale("ru", "by"));
        labelTicketPrice.setText(format.format(movieSessionInfo.ticketPrice) + " ");

        labelFilmName.setText(movieSessionInfo.filmName);
        labelRoomName.setText(movieSessionInfo.roomName);
        labelSessionTime.setText(formatDateTime( movieSessionInfo.sessionTime));

    }

    public static void main(String[] args)
    {
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

        new UserMovieSession(1, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        returnToFindMovieSession();
    }

    private void returnToFindMovieSession()
    {
        if (returnContextParam != null) {
            UserFindMovieSession frame = new UserFindMovieSession(0, returnContextParam);
            returnContextParam = null;
        }
    }

    @Override
    public void windowClosed(WindowEvent e)
    {

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

    private void ShowTicketStatistics()
    {

        HallTable.CellStats ststs = hallTable.getHalltickets();

        labelOrderedTickets.setText(Integer.toString(ststs.myOrderedTickets));
        labelCancelledTickets.setText(Integer.toString(ststs.cancelledTickets));
        labelAddedTickets.setText(Integer.toString(ststs.addedtickets));
    }

    @Override
    public void tiketsChanged()
    {
        ShowTicketStatistics();
    }
}
