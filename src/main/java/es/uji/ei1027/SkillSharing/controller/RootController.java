package es.uji.ei1027.SkillSharing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class RootController {
    @RequestMapping(value="")
    public String homePage() {
        return "index3";
    }

}
