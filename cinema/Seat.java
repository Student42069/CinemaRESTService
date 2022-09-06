package cinema;

public class Seat {
    private int row;
    private int column;
    boolean purchased = false;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Seat() {
    }

    public Seat(String s) {
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        if (this.row <= 4) {
            return 10;
        } else {
            return 8;
        }
    }
}