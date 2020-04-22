package nehe.houseservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HouseServiceController {

    @GetMapping("/")
    public String hello(){
        return "hello";
    }
}
