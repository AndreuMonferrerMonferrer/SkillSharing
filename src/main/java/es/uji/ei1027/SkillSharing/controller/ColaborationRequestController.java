package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.ColaborationProposalDAO;
import es.uji.ei1027.SkillSharing.dao.ColaborationRequestDAO;
import es.uji.ei1027.SkillSharing.dao.SkillTypeDAO;
import es.uji.ei1027.SkillSharing.dao.StudentDAO;
import es.uji.ei1027.SkillSharing.model.ColaborationProposal;
import es.uji.ei1027.SkillSharing.model.ColaborationRequest;
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
@RequestMapping("/colaborationRequest")
public class ColaborationRequestController {

    private ColaborationRequestDAO colaborationRequestDAO;
    private ColaborationProposalDAO colaborationProposalDAO;
    private SkillTypeDAO skillTypeDAO;
    private StudentDAO studentDAO;
    @Autowired
    public void setColaborationRequestDAO(ColaborationRequestDAO colaborationRequestDAO){
        this.colaborationRequestDAO=colaborationRequestDAO;
    }

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
    public String listColaboratioRequests(HttpSession session, Model model){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("colaborationRequests", colaborationRequestDAO.getColaborationRequestByOtherUsers(user.getUsername()));
        List<SkillType> skillTypes = skillTypeDAO.getSkillTypes();
        model.addAttribute("skillTypes", skillTypes);
        model.addAttribute("logged",session.getAttribute("user")!=null);
        return "colaborationRequest/list";
    }

    @RequestMapping("/listSKP")
    public String listSKPColaboratioRequests(HttpSession session, Model model){
        model.addAttribute("colaborationRequests", colaborationRequestDAO.getRequestAbilitated());
        List<SkillType> skillTypes = skillTypeDAO.getSkillTypes();
        model.addAttribute("skillTypes", skillTypes);
        return "colaborationRequest/listSKP";
    }

    @RequestMapping(value = "/add/{proposalId}")
    public String addColaborationRequest(HttpSession session,Model model, @PathVariable int proposalId){
        session.setAttribute("nextUrl", "/user/list");
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("email", user.getUsername());
        ColaborationProposal proposal=colaborationProposalDAO.getColaborationProposal(proposalId);
        ColaborationRequest request=new ColaborationRequest();
        request.setIdSkill(proposal.getIdSkill());
        request.setDateEnd(proposal.getDateEnd());
        request.setDateStart(proposal.getDateStart());
        model.addAttribute("colaborationRequest",request);
        List<Integer> idList = skillTypeDAO.getSkillTypesIds();
        model.addAttribute("idList", idList);
        List<SkillType> skillTypes = skillTypeDAO.getSkillTypesAbilitados();
        model.addAttribute("skillTypes", skillTypes);
        return "colaborationRequest/add";
    }


    @RequestMapping(value = "/add")
    public String addColaborationRequestUser(HttpSession session, Model model){
        session.setAttribute("nextUrl", "/user/list");
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("email", user.getUsername());
        model.addAttribute("colaborationRequest", new ColaborationRequest());
        List<Integer> idList = skillTypeDAO.getSkillTypesIds();
        model.addAttribute("idList", idList);
        List<SkillType> skillTypes = skillTypeDAO.getSkillTypesAbilitados();
        model.addAttribute("skillTypes", skillTypes);
        return "colaborationRequest/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddNSubmit(@ModelAttribute("colaborationRequest") ColaborationRequest colaborationRequest,
                                   BindingResult bindingResult, HttpSession session, Model model){
        ColaborationRequestValidator colaborationRequestValidator =new ColaborationRequestValidator(studentDAO);
        colaborationRequestValidator.validate(colaborationRequest,bindingResult);
        if (bindingResult.hasErrors()) {
            session.setAttribute("nextUrl","/user/list");
            if(session.getAttribute("user") == null){
                model.addAttribute("user", new UserDetails());
                return "login";
            }
            UserDetails user = (UserDetails) session.getAttribute("user");
            model.addAttribute("email", user.getUsername());
            List<SkillType> skillTypes = skillTypeDAO.getSkillTypesAbilitados();
            model.addAttribute("skillTypes", skillTypes);
            return "colaborationRequest/add";
        }
        colaborationRequestDAO.addColaborationRequest(colaborationRequest);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{requestId}", method = RequestMethod.GET)
    public String editColaborationRequest(Model model, @PathVariable int requestId){
        model.addAttribute("colaborationRequest", colaborationRequestDAO.getColaborationRequest(requestId));
        List<Integer> idList = skillTypeDAO.getSkillTypesIds();
        model.addAttribute("idList", idList);
        List<SkillType> skillTypes = skillTypeDAO.getSkillTypesAbilitados();
        model.addAttribute("skillTypes", skillTypes);
        return "colaborationRequest/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("colaborationRequest") ColaborationRequest colaborationRequest,
            BindingResult bindingResult){
        ColaborationRequestValidator colaborationRequestValidator =new ColaborationRequestValidator(studentDAO);
        colaborationRequestValidator.validate(colaborationRequest,bindingResult);
        if (bindingResult.hasErrors())
            return "colaborationRequest/update";
        colaborationRequestDAO.updateColaborationRequest(colaborationRequest);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{requestId}")
    public String processDelete(@PathVariable int requestId){
        colaborationRequestDAO.deleteColaborationRequest(requestId);
        return "redirect:../list";
    }
}