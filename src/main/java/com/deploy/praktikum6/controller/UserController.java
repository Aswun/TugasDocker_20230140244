package com.deploy.praktikum6.controller;

import com.deploy.praktikum6.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final String USERNAME = "admin";
    private final String PASSWORD = "20230140244";

    private List<User> userList = new ArrayList<>();

    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("loggedIn") != null) {
            return "redirect:/home";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        if (username.equals(USERNAME) && (password.equals("2201010101") || password.equals(PASSWORD))) {
            session.setAttribute("loggedIn", true);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Username atau Password salah!");
            return "login";
        }
    }

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("userList", userList);
        return "home";
    }

    @GetMapping("/input")
    public String inputPage(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", new User());
        return "input";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute User user, HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        userList.add(user);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
