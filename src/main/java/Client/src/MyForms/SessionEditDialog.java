package Client.src.MyForms;

import Common.CommonUtils;
import Common.Data.*;
import Common.ServerProxy;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;

import static Common.CommonUtils.*;

public class SessionEditDialog extends JDialog {
    private SessionFilmBase filmSession = null;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBoxHall;
    private JPanel panelSessionTime;
    private JFormattedTextField textTicketPrice;
    private DateTimePicker sessionTimePicker;

    public SessionEditDialog(int filmId, int filmSessionId) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setTitle("Сеанс фильма");
        buttonOK.setText("Сохранить");
        buttonCancel.setText("Отменить");
        setIconImage(new ImageIcon("images/ButtonIcons/schedule.png").getImage());
        tuneEditors();

        if (filmSessionId > 0)
        {
            filmSession = ServerProxy.Server().loadFilmSession(filmSessionId);
        }
        else
        {
            filmSession = new SessionFilmBase();
            filmSession.filmId = filmId;
        }
        fillControls();

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

    private void tuneEditors()
    {
        NumberFormat nf = NumberFormat.getIntegerInstance(); // Specify specific format here.
        NumberFormatter nff = new NumberFormatter(nf);
        nff.setAllowsInvalid(true);
        nff.setMinimum(0);
        DefaultFormatterFactory factory = new DefaultFormatterFactory(nff);
        textTicketPrice.setFormatterFactory(factory);

        ArrayList<HallBase> halls = ServerProxy.Server().loadHalls();
        halls.add(0, null); // First Empty Hall
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        comboBoxModel.addAll(halls);
        comboBoxHall.setModel(comboBoxModel);

        DatePickerSettings dateSettings = new DatePickerSettings();
        TimePickerSettings timeSettings = new TimePickerSettings();
        dateSettings.setAllowEmptyDates(false);
        timeSettings.setAllowEmptyTimes(false);
        sessionTimePicker = new DateTimePicker(dateSettings, timeSettings);

        try
        {   // Добавить кнопочку с картинкой
            URL dateImageURL = new File("images/DateTimePicker/calendar.png").toURI().toURL();
            Image dateExampleImage = Toolkit.getDefaultToolkit().getImage(dateImageURL);
            ImageIcon dateExampleIcon = new ImageIcon(dateExampleImage);
            JButton datePickerButton = sessionTimePicker.datePicker.getComponentToggleCalendarButton();
            datePickerButton.setText("");
            datePickerButton.setIcon(dateExampleIcon);
        }
        catch (Exception e)
        {
        }

        try
        {
            URL timeIconURL = new File("images/DateTimePicker/clock.png").toURI().toURL();
            Image timeExampleImage = Toolkit.getDefaultToolkit().getImage(timeIconURL);
            ImageIcon timeExampleIcon = new ImageIcon(timeExampleImage);

            JButton timePickerButton = sessionTimePicker.timePicker.getComponentToggleTimeMenuButton();
            timePickerButton.setText("");
            timePickerButton.setIcon(timeExampleIcon);
        }
        catch (Exception e)
        {
        }

        panelSessionTime.setLayout(new GridLayout());
        panelSessionTime.add(sessionTimePicker);

    }

    private void fillControls()
    {
        if (filmSession.hallId > 0) {
            ComboBoxModel comboBoxModel = comboBoxHall.getModel();
            for (int i=0; i <  comboBoxModel.getSize(); i++)
            {
                Object obj = comboBoxModel.getElementAt(i);
                if (obj != null)
                {
                    HallBase hall = (HallBase)obj;
                    if (hall.id == filmSession.hallId)
                    {
                        comboBoxModel.setSelectedItem(obj);
                        break;
                    }
                }
            }
        }

        if (filmSession.sessionTime != null)
        {
            sessionTimePicker.setDateTimePermissive(convertToLocalDateTimeViaInstant(filmSession.sessionTime));
        }

        if (filmSession.ticketPrice != null)
        {
            textTicketPrice.setText(filmSession.ticketPrice.toBigInteger().toString());
        }

    }
    private void fillObject()
    {
        ComboBoxModel model = comboBoxHall.getModel();
        Object obj = model.getSelectedItem();
        if (obj != null)
        {
            HallBase hall = (HallBase)obj;
            filmSession.hallId = hall.id;
        }

        filmSession.sessionTime = convertToDateViaSqlTimestamp(sessionTimePicker.getDateTimePermissive());
        filmSession.ticketPrice = new BigDecimal(strToInt(textTicketPrice.getText()));
    }

    private void onOK() {
        fillObject();
        filmSession = ServerProxy.Server().saveFilmSession(filmSession);
        dispose();
    }

    private void onCancel() {
        filmSession = null;
        dispose();
    }

    public static SessionFilmBase Open(int filmId, int sessionId)
    {
        SessionEditDialog dialog = new SessionEditDialog(filmId, sessionId);
        dialog.pack();
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - dialog.getWidth()) / 2;
        final int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);
        dialog.setVisible(true);
        return dialog.filmSession;
    }

    public static void main(String[] args)
    {
        CommonUtils.InitDafaultTheme();
        Open(1, 0);
        System.exit(0);
    }
}
