package org.tufuteca.hotelwebsitejava.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class ApplicationController {
    private static final String PHOTO_DIR = "static/photos/slider/";

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
    @PreAuthorize("hasAuthority('permission:read')")
    public String getProfilePage(Model model) {
        return "/user-profile";
    }
    @GetMapping("/login")
    public String getLoginPage(Model model){
        return "/login";
    }
}
