package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

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
    public Seat buySeat(@RequestBody Seat seat) {
        for(Seat seats: this.cinema.available_seats()) {
            if (seats.getRow() == seat.getRow() && seats.getColumn() == seat.getColumn()) {
                if (!seats.purchased) {
                    seats.purchased = true;
                    return seats;
                }
                throw new SeatUnavailableException(
                        "The ticket has been already purchased!");
            }
        }
        throw new SeatUnavailableException(
                "The number of a row or a column is out of bounds!");
    }

    @ExceptionHandler(SeatUnavailableException.class)
    public ResponseEntity<CustomErrorMessage> handleSeatUnavailable(
            SeatUnavailableException e) {

        CustomErrorMessage body = new CustomErrorMessage(
                e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}