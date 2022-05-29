package es.uji.ei1027.SkillSharing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/help")
public class helpController {
    @RequestMapping("/help")
    public String help(Model model){
        return "help/help";
    }
}
