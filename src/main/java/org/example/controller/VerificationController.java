package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.impl.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {
    private final UserService userService;

    @GetMapping
    public String getNotes(@RequestParam("code") String code, Model model, Authentication auth) {
        boolean success = userService.verifyUser(code);
        model.addAttribute("isSuccess", success);

        return "verification";
    }
}
