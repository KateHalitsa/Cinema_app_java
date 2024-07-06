package Common;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class CommonUtils
{
    static public int getCurrentUserID()
    {
        return 37; // Алиса
    }
    static private SimpleDateFormat getDateFormat()
    {
        return new SimpleDateFormat("dd MMMM yyyy", new Locale("ru"));
    }
    static private SimpleDateFormat getTimeFormat()
    {
        return new SimpleDateFormat("HH:mm", new Locale("ru"));
    }

    static private SimpleDateFormat getDateTimeFormat()
    {
        return new SimpleDateFormat("dd MMMM yyyy HH:mm", new Locale("ru"));
    }

    static public String formatDate(Date value)
    {
        return getDateFormat().format(value);
    }
    static public String formatTime(Date value)
    {
        return getTimeFormat().format(value);
    }
    static public String  formatDateTime(Date value)
    {
        return getDateTimeFormat().format(value);
    }
    static public boolean timeIsOver(Date value)
    {
        Date date = java.sql.Date.valueOf(LocalDate.now());
        return value.compareTo(date) < 0;
    }
    static public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    static public LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    static public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    static public int strToInt(String value)
    {
         String str = value.replaceAll("[^0-9]", "");
         if(!str.isEmpty())
         {
             return Integer.parseInt(str);
         }
         else
         {
             return 0;
         }
    }
    static public void InitDafaultTheme()
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            if (defaults.get("Table.alternateRowColor") == null)
                defaults.put("Table.alternateRowColor", new Color(240, 240, 240)); // Set Zebra Color
        } catch (Exception e) {
            //  Block of code to handle errors
        }
    }


}
