package Common.FindResults;

import java.util.ArrayList;
import java.util.Date;

public class FindFilmsResult {
    public int id;
    public String title;
    public int year;
    public String director;
    public int length;
    public String description;
    public ArrayList<String> actors = new ArrayList<>();
    public ArrayList<String> categories = new ArrayList<>();;
    public Date nearestPastSessionDate;
    public Date nearestFutureSessionDate;
}
