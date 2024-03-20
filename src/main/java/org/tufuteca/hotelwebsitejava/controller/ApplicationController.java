package org.tufuteca.hotelwebsitejava.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tufuteca.hotelwebsitejava.model.User;
import org.tufuteca.hotelwebsitejava.service.UserService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class ApplicationController {
    private static final String PHOTO_DIR = "static/photos/slider/";
    private final UserService userService;

    public ApplicationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        try {
            List<String> photoNames = loadPhotos();
            model.addAttribute("photoNames", photoNames);
            return "index";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
    private List<String> loadPhotos() throws IOException {
        Resource resource = new ClassPathResource(PHOTO_DIR);
        Path photoPath = Paths.get(resource.getURI());

        return Arrays.stream(photoPath.toFile().list())
                .filter(file -> file.toLowerCase().endsWith(".jpg") || file.toLowerCase().endsWith(".png"))
                .collect(Collectors.toList());
    }
    @GetMapping("/user-profile")
    @PreAuthorize("hasRole('USER')")
    public String getUserProfilePage() {
        return "/user-profile";
    }

    @GetMapping("/admin-panel")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminPanelPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "/admin-panel";
    }
    @GetMapping("/dashboard")
    public String getDashboardPage(Authentication authentication, HttpServletResponse response) throws IOException {
        if (authentication == null || !authentication.isAuthenticated()) {
            response.sendRedirect("/login");
        } else {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            if (roles.contains("ROLE_ADMIN")) {
                response.sendRedirect("/admin-panel");
            } else if (roles.contains("ROLE_USER")) {
                response.sendRedirect("/user-profile");
            } else {
                response.sendRedirect("/");
            }
        }
        return null;
    }


    @GetMapping("/edit-profile/{id}")
    public String editProfile(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit-profile";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "/login";
    }
    @GetMapping("/registration")
    public String getregistrationPage(){
        return "/registration";
    }
}
