package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
        for(Seat seats: this.cinema.allSeats()) {
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
    public Refund returnTicket(@RequestBody Return receipt) {
        for(Receipt purchasedTicket: this.cinema.getPurchasedTickets()){
            if(purchasedTicket.getToken().equals(receipt.getToken())){
                int row = purchasedTicket.getTicket().getRow();
                int column = purchasedTicket.getTicket().getColumn();
                for (Seat seat: this.cinema.allSeats()) {
                    if (seat.getRow() == row && seat.getColumn() == column) {
                        seat.setPurchased(false);
                    }
                }
                return new Refund(purchasedTicket.getTicket());
            }
        }
        throw new SeatUnavailableException("Wrong token!");
    }

    @PostMapping("/stats")
    public Statistics statistics(@RequestParam String password) {
        if (password.equals("super_secret")) {
            return new Statistics(this.cinema);
        }
        throw new WrongPasswordException("The password is wrong!");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CustomErrorMessage> handleMissingParams(MissingServletRequestParameterException ex) {
        CustomErrorMessage body = new CustomErrorMessage("The password is wrong!");
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<CustomErrorMessage> handleWrongPassword(
            WrongPasswordException e) {

        CustomErrorMessage body = new CustomErrorMessage(
                e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SeatUnavailableException.class)
    public ResponseEntity<CustomErrorMessage> handleSeatUnavailable(
            SeatUnavailableException e) {

        CustomErrorMessage body = new CustomErrorMessage(
                e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}