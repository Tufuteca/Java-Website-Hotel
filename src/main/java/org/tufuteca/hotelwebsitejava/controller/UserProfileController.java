package org.tufuteca.hotelwebsitejava.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class UserProfileController {

    @GetMapping("/user-profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getProfilePage(Model model) {
        model.addAttribute("username", "user");
        model.addAttribute("surname", "Фамилия");
        model.addAttribute("name", "Имя");
        model.addAttribute("patronymic", "Отчество");
        model.addAttribute("phone", "+7(999)999-99-99");
        model.addAttribute("email", "example@ex.com");
        return "/user-profile";
    }
    @GetMapping("/login")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getLoginPage(Model model){
        return "/login";
    }
}
