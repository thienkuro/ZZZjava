package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAttribute {

    @ModelAttribute
    public void addUsernameToModel(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
    }
}
