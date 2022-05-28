package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.SkillTypeDAO;
import es.uji.ei1027.SkillSharing.model.SkillType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/skillType")
public class SkillTypeController {

    private SkillTypeDAO skillTypeDAO;

    @Autowired
    public void setSkillTypeDAO(SkillTypeDAO skillTypeDAO){this.skillTypeDAO=skillTypeDAO;}

    @RequestMapping("/list")
    public String listSkillType(Model model){
        model.addAttribute("skillType", skillTypeDAO.getSkillTypesAbilitados());
        return "skillType/list";
    }

    @RequestMapping(value = "/add")
    public String addSkillType(Model model){
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