package org.tufuteca.hotelwebsitejava.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tufuteca.hotelwebsitejava.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/delete-profile")
    public String deleteProfile(@RequestParam("id") Long id){
        userService.deleteUser(id);
        return "redirect:/admin-panel";
    }




}
