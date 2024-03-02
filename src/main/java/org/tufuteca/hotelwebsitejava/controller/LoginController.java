package org.tufuteca.hotelwebsitejava.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/loginPage")
    private String loginPage(Model model){
        return("loginPage");
    }

}
