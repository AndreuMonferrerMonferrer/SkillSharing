package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.ManageSkillsDAO;
import es.uji.ei1027.SkillSharing.dao.SkillTypeDAO;
import es.uji.ei1027.SkillSharing.dao.StudentDAO;
import es.uji.ei1027.SkillSharing.model.Colaboration;
import es.uji.ei1027.SkillSharing.model.ManageSkills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/manageSkills")
public class ManageSkillsController {

    private ManageSkillsDAO manageSkillsDAO;
    private SkillTypeDAO skillTypeDAO;
    private StudentDAO studentDAO;

    @Autowired
    public void setManageSkillsDAO(ManageSkillsDAO manageSkillsDAO){this.manageSkillsDAO=manageSkillsDAO;}

    @Autowired
    public void setSkillTypeDAO(SkillTypeDAO skillTypeDAO){
        this.skillTypeDAO=skillTypeDAO;
    }

    @Autowired
    public void setStudentDAO(StudentDAO studentDAO){this.studentDAO=studentDAO;}

    @RequestMapping("/list")
    public String listManageSkills(Model model){
        model.addAttribute("manageSkills",manageSkillsDAO.getManageSkillsList());
        return "manageSkills/list";
    }

    @RequestMapping(value = "/add")
    public String addManageSkills(Model model){
        model.addAttribute("manageSkill", new ManageSkills());
        List<Integer> idList = skillTypeDAO.getSkillTypesIds();
        model.addAttribute("idList", idList);
        return "manageSkills/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAndSubmit(@ModelAttribute("manageSkill") ManageSkills manageSkill,
                                   BindingResult bindingResult){
        ManageSkillsValidator manageSkillsValidator=new ManageSkillsValidator(studentDAO);
        manageSkillsValidator.validate(manageSkill,bindingResult);
        if (bindingResult.hasErrors())
            return "manageSkills/add";
        manageSkillsDAO.addManageSkills(manageSkill);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{idSkillType}/{email}")
    public String processDeleteColaboration(@PathVariable int idSkillType,
                                            @PathVariable String email) {
        manageSkillsDAO.deleteManageSkills(idSkillType, email);
        return "redirect:../../list";
    }
}
