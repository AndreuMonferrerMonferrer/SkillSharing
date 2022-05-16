package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.ColaborationProposalDAO;
import es.uji.ei1027.SkillSharing.dao.SkillTypeDAO;
import es.uji.ei1027.SkillSharing.dao.StudentDAO;
import es.uji.ei1027.SkillSharing.model.ColaborationProposal;
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
import java.util.List;


@Controller
@RequestMapping("/colaborationProposal")
public class ColaborationProposalController {

    private ColaborationProposalDAO colaborationProposalDAO;
    private SkillTypeDAO skillTypeDAO;
    private StudentDAO studentDAO;

    @Autowired
    public void setColaborationProposalDAO(ColaborationProposalDAO colaborationProposalDAO){
        this.colaborationProposalDAO=colaborationProposalDAO;
    }

    @Autowired
    public void setSkillTypeDAO(SkillTypeDAO skillTypeDAO){
        this.skillTypeDAO=skillTypeDAO;
    }

    @Autowired
    public void setStudentDAO(StudentDAO studentDAO){this.studentDAO=studentDAO;}

    @RequestMapping("/list")
    public String listColaborationProposals(Model model){
        model.addAttribute("colaborationProposals", colaborationProposalDAO.getColaborationProposals());
        return "colaborationProposal/list";
    }

    @RequestMapping(value="/listSinRegistrar")
    public String listColaborationProposalsSinRegistrar(Model model){
        model.addAttribute("colaborationProposals", colaborationProposalDAO.getColaborationProposals());
        model.addAttribute("skillList",skillTypeDAO.getSkillTypes());
        return "colaborationProposal/listSinRegistrar";
    }
    @RequestMapping(value = "/add")
    public String addColaborationProposal(Model model){
        model.addAttribute("colaborationProposal", new ColaborationProposal());
        List<Integer> idList = skillTypeDAO.getSkillTypesIds();
        model.addAttribute("idList", idList);
        List<String> emails = studentDAO.getEmails();
        model.addAttribute("emails",emails);
        return "colaborationProposal/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAndSubmit(@ModelAttribute("colaborationProposal") ColaborationProposal colaborationProposal,
                                   BindingResult bindingResult){
        ColaborationProposalValidator colaborationProposalValidator =new ColaborationProposalValidator(studentDAO);
        colaborationProposalValidator.validate(colaborationProposal,bindingResult);
        if (bindingResult.hasErrors())
            return "colaborationProposal/add";
        colaborationProposalDAO.addColaborationProposal(colaborationProposal);
        return "redirect:list";
    }

    @RequestMapping(value = "/addN")
    public String addColaborationProposalUser(HttpSession session, Model model){
        session.setAttribute("nextUrl","/user/list");
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("email", user.getUsername());
        model.addAttribute("colaborationProposal", new ColaborationProposal());
        List<Integer> idList = skillTypeDAO.getSkillTypesIds();
        model.addAttribute("idList", idList);
        return "colaborationProposal/addN";
    }

    @RequestMapping(value = "/update/{proposalId}", method = RequestMethod.GET)
    public String editColaborationProposal(Model model, @PathVariable int proposalId){
        model.addAttribute("colaborationProposal", colaborationProposalDAO.getColaborationProposal(proposalId));
        List<Integer> idList = skillTypeDAO.getSkillTypesIds();
        model.addAttribute("idList", idList);
        return "colaborationProposal/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("colaborationProposal") ColaborationProposal colaborationProposal,
            BindingResult bindingResult){
        ColaborationProposalValidator colaborationProposalValidator =new ColaborationProposalValidator(studentDAO);
        colaborationProposalValidator.validate(colaborationProposal,bindingResult);
        if (bindingResult.hasErrors())
            return "colaborationProposal/update";
        colaborationProposalDAO.updateColaborationProposal(colaborationProposal);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{proposalId}")
    public String processDelete(@PathVariable int proposalId){
        colaborationProposalDAO.deleteColaborationProposal(proposalId);
        return "redirect:../list";
    }
}