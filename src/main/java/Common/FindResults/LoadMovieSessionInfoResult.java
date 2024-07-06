package Common.FindResults;

import Common.Data.TicketBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoadMovieSessionInfoResult
{
    public int sessionId;
    public Date sessionTime;
    public BigDecimal ticketPrice;
    public int filmId;
    public String filmName;
    public int roomId;
    public String roomName;
    public int roomRows;
    public int roomSeats;
    public ArrayList<TicketBase> ticktes = new ArrayList<>();

}
