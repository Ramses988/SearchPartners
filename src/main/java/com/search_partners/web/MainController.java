package com.search_partners.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/feedback")
    public String feedback(Model model) {
        model.addAttribute("title", "Ваше сообщение успешно отправлено");
        model.addAttribute("headline", "Ваше сообщение успешно отправлено");
        model.addAttribute("text", "");
        return "account/info";
    }

}