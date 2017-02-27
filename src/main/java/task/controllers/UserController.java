package task.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    public static final String USER_HTML = "user.html";

    @RequestMapping("/page")
    public String index() {
        return USER_HTML;
    }
}