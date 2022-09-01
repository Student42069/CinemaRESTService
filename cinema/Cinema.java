package cinema;
import java.util.*;


public class Cinema {

    private int total_rows;
    private int total_columns;
     List<Seat> available_seats = new ArrayList<>();

    public Cinema(int rows, int columns) {
        this.total_rows = rows;
        this.total_columns = columns;

        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                available_seats.add(new Seat(row,column));
            }
        }
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public List<Seat> getAvailable_seats() {
        return available_seats;
    }
}