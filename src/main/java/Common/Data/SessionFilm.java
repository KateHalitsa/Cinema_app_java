package Common.Data;

import java.math.BigDecimal;
import java.util.Date;

public class SessionFilm {
    protected Date sessionTime;
    protected BigDecimal ticketPrice;
    protected Hall hall;
    protected Film film;

    public SessionFilm(Date sessionTime,BigDecimal ticketPrice,Hall hall,Film film){
        this.sessionTime=sessionTime;
        this.film=film;
        this.ticketPrice=ticketPrice;
        this.hall=hall;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setSessionTime(Date sessionTime) {
        this.sessionTime = sessionTime;
    }

    public Film getFilm() {
        return film;
    }

    public Date getSessionTime() {
        return sessionTime;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public Hall getHall() {
        return hall;
    }
    @Override
    public String toString() {
        return "SessionFilm{" +
                "ticketPrice='" + ticketPrice + '\'' +
                ", sessionTime=" + sessionTime +
                hall+", " +film+"}" ;
    }
}
