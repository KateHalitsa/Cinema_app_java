package Common.Data;

public class Ticket {
    protected int row;
    protected int seat;
    protected SessionFilm sessionFilm;
    public Ticket(int row,int seat,SessionFilm sessionFilm){
        this.row=row;
        this.seat=seat;
        this.sessionFilm=sessionFilm;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setSessionFilm(SessionFilm sessionFilm) {
        this.sessionFilm = sessionFilm;
    }

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public SessionFilm getSessionFilm() {
        return sessionFilm;
    }
    @Override
    public String toString() {
        return "Ticket{" +
                ", row=" + row +
                ", seat=" + seat+
                ","+sessionFilm+
                "}" ;
    }
}
