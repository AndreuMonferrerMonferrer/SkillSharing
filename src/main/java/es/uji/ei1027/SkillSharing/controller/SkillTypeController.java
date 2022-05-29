package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.SkillTypeDAO;
import es.uji.ei1027.SkillSharing.dao.StudentDAO;
import es.uji.ei1027.SkillSharing.model.SkillType;
import es.uji.ei1027.SkillSharing.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/skillType")
public class SkillTypeController {

    private SkillTypeDAO skillTypeDAO;

    private StudentDAO studentDAO;

    @Autowired
    public void setSkillTypeDAO(SkillTypeDAO skillTypeDAO){this.skillTypeDAO=skillTypeDAO;}

    @Autowired
    public void setStudentDAO(StudentDAO studentDAO){this.studentDAO=studentDAO;}

    @RequestMapping("/list")
    public String listSkillType(HttpSession session,Model model){
        session.setAttribute("nextUrl", "/skillType/list");
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }

        if (studentDAO.getStudent(user.getUsername()).getIsSkp().equals("N")){
            return "/user/profile";
        }
        model.addAttribute("skillType", skillTypeDAO.getSkillTypesAbilitados());
        return "skillType/list";
    }

    @RequestMapping(value = "/add")
    public String addSkillType(HttpSession session, Model model){
        session.setAttribute("nextUrl", "/skillType/add");
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }

        if (studentDAO.getStudent(user.getUsername()).getIsSkp().equals("N")){
            return "/user/profile";
        }
        model.addAttribute("skillType", new SkillType());
        return "skillType/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAndSubmit(@ModelAttribute("skillType") SkillType skillType,
                                   BindingResult bindingResult){
        SkillTypeValidator skillTypeValidator=new SkillTypeValidator();
        skillTypeValidator.validate(skillType, bindingResult);
        if (bindingResult.hasErrors())
            return "skillType/add";
        skillTypeDAO.addSkillType(skillType);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editSkillType(Model model, @PathVariable int id){
        model.addAttribute("skillType", skillTypeDAO.getSkillType(id));
        return "skillType/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("skillType") SkillType skillType,
            BindingResult bindingResult){
        SkillTypeValidator skillTypeValidator=new SkillTypeValidator();
        skillTypeValidator.validate(skillType, bindingResult);
        if (bindingResult.hasErrors())
            return "skillType/update";
        skillTypeDAO.updateSkillType(skillType);
        return "redirect:list";
    }
    @RequestMapping(value = "/delete/{id}")
    public String processDelete(@PathVariable int id){
        skillTypeDAO.deleteSkillType(id);
        return "redirect:../list";
    }

    @RequestMapping(value = "/disable/{id}")
    public String processDisable(@PathVariable int id){
        skillTypeDAO.disableSkillType(id);
        return "redirect:../list";
    }
}