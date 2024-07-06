package Common.Params;
import java.util.ArrayList;

public class UpdateMovieSessionTicketsParam {
    public static class SeatRow
    {
        public int Seat;
        public int Row;
    }
    public ArrayList<SeatRow> insertTickets = new ArrayList<>();
    public ArrayList<Integer> deleteTickets = new ArrayList<>();

    public int movieSessionId;
    public int userId;
    public boolean needReturnAllTickets = false;
}
