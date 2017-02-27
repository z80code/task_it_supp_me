package task.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LibrarianController {
    @RequestMapping("/lib")
    public String index() {
        return "library.html";
    }

}