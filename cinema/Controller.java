package cinema;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/seats")
    public Cinema getSeats() {
        return new Cinema(9,9);
    }
}