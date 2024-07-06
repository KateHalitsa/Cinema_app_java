package Server.src.Data;

public class Hall {
   protected String name;
   protected int rows,seats;
public Hall(String name, int rows, int seats){
    this.name=name;
    this.rows=rows;
    this.seats=seats;
}

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }

    public int getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "name='" + name + "'" +
                ", rows=" + rows +
                ", seats=" + seats+'}' ;
    }
}
