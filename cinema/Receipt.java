package cinema;

import static java.util.UUID.randomUUID;

public class Receipt {
    private String token;
    private Seat ticket;

    public Receipt(Seat seat) {
        this.ticket = seat;
        this.token = randomUUID().toString();
    }

    public Receipt() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }
}
