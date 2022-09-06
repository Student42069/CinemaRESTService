package cinema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    private final Cinema cinema;

    Controller() {
        this.cinema = new Cinema(9,9);
    }

    @GetMapping("/seats")
    public Cinema getSeats() {
        return this.cinema;
    }

    @PostMapping("/purchase")
    public Receipt buySeat(@RequestBody Seat seat) {
        for(Seat seats: this.cinema.available_seats()) {
            if (seats.getRow() == seat.getRow() && seats.getColumn() == seat.getColumn()) {
                if (!seats.isPurchased()) {
                    seats.setPurchased(true);
                    Receipt receipt = new Receipt(seats);
                    this.cinema.addReceipt(receipt);
                    return receipt;
                }
                throw new SeatUnavailableException(
                        "The ticket has been already purchased!");
            }
        }
        throw new SeatUnavailableException(
                "The number of a row or a column is out of bounds!");
    }

    @PostMapping("/return")
    @JsonIgnoreProperties("fieldname")
    public Refund returnTicket(@RequestBody Return receipt) {
        for(Receipt purchasedTicket: this.cinema.getPurchasedTickets()){
            if(purchasedTicket.getToken().equals(receipt.getToken())){
                return new Refund(purchasedTicket.getTicket());
            }
        }
        throw new SeatUnavailableException("Wrong token!");
    }

    @ExceptionHandler(SeatUnavailableException.class)
    public ResponseEntity<CustomErrorMessage> handleSeatUnavailable(
            SeatUnavailableException e) {

        CustomErrorMessage body = new CustomErrorMessage(
                e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}