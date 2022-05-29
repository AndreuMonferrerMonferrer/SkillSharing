package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.model.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/help")
public class helpController {
    @RequestMapping("/help")
    public String help(HttpSession session, Model model){
        session.setAttribute("nextUrl", "/help/help");
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        return "help/help";
    }
}
