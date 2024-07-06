package Client.src.MyForms;

import Common.CommonUtils;
import Common.Data.FilmBase;
import Common.ServerProxy;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

import static Common.CommonUtils.strToInt;

public class FilmEditDialog extends JDialog {
    private FilmBase film = null;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldTitle;
    private JFormattedTextField textFieldYear;
    private JFormattedTextField textFieldLength;
    private JTextField textFieldDirector;
    private JTextArea textAreaDescrption;
    private JScrollPane scrollPanelDescription;


    public FilmEditDialog(int filmId) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setTitle("Фильм");
        buttonOK.setText("Сохранить");
        buttonCancel.setText("Отменить");
        setIconImage(new ImageIcon("images/ButtonIcons/film.png").getImage());
       // setSize(880,500);
        setMinimumSize(new Dimension(700, 400));

        tuneEditors();

        if (filmId > 0)
        {
            film = ServerProxy.Server().loadFilm(filmId);
        }
        else
        {
            film = new FilmBase();
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

    private void fillControls()
    {
        textFieldTitle.setText(film.title);
        if (film.year > 0)
        {
            textFieldYear.setText(Integer.toString(film.year));
        }
        if (film.length > 0)
        {
            textFieldLength.setText(Integer.toString(film.length));
        }
        textFieldDirector.setText(film.director);
        textAreaDescrption.setText(film.description);
    }
    private void fillObject()
    {
        film.title = textFieldTitle.getText().trim();

        String str = textFieldYear.getText().trim();
        if(!str.isEmpty()){
            film.year = strToInt(str);
        }
        str = textFieldLength.getText().trim();
        if(!str.isEmpty()){
            film.length = strToInt(str);
        }

        film.director = textFieldDirector.getText().trim();
        film.description = textAreaDescrption.getText().trim();
    }


    private void tuneEditors()
    {
        scrollPanelDescription.setBorder(BorderFactory.createEmptyBorder());

        NumberFormat nf = NumberFormat.getIntegerInstance(); // Specify specific format here.
        NumberFormatter nff = new NumberFormatter(nf);
        nff.setAllowsInvalid(true);
        nff.setMinimum(0);
        DefaultFormatterFactory factory = new DefaultFormatterFactory(nff);
        textFieldLength.setFormatterFactory(factory);
        textFieldYear.setFormatterFactory(factory);
    }

    public static FilmBase Open(int filmId)
    {
        FilmEditDialog dialog = new FilmEditDialog(filmId);
        dialog.pack();
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - dialog.getWidth()) / 2;
        final int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);
        dialog.setVisible(true);
        return dialog.film;
    }

    private void onOK() {
        fillObject();
        film = ServerProxy.Server().saveFilm(film);
        dispose();
    }

    private void onCancel() {
        film = null;
        dispose();
    }

    public static void main(String[] args) {
        CommonUtils.InitDafaultTheme();
        Open(1);
        System.exit(0);
    }
}
