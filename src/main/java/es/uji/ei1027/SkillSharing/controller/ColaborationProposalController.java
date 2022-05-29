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
@RequestMapping("/colaborationProposal")
public class ColaborationProposalController {

    private ColaborationProposalDAO colaborationProposalDAO;
    private ColaborationRequestDAO colaborationRequestDAO;


    private SkillTypeDAO skillTypeDAO;
    private StudentDAO studentDAO;
    private EmailService emailService;
    @Autowired
    public void setColaborationProposalDAO(ColaborationProposalDAO colaborationProposalDAO){
        this.colaborationProposalDAO=colaborationProposalDAO;
    }
    @Autowired
    public void setColaborationRequestDAO(ColaborationRequestDAO colaborationRequestDAO){
        this.colaborationRequestDAO=colaborationRequestDAO;
    }
    @Autowired
    public void setSkillTypeDAO(SkillTypeDAO skillTypeDAO){
        this.skillTypeDAO=skillTypeDAO;
    }

    @Autowired
    public void setStudentDAO(StudentDAO studentDAO){this.studentDAO=studentDAO;}

    @Autowired
    public void setEmailService(EmailService emailService){this.emailService=emailService;}

    @RequestMapping("/list")
    public String listColaborationProposals(HttpSession session,Model model){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("colaborationProposals", colaborationProposalDAO.getColaborationProposalsByOtherUsers(user.getUsername()));
        model.addAttribute("skillTypes",skillTypeDAO.getSkillTypes());
        return "colaborationProposal/list";
    }

    @RequestMapping("/listN")
    public String listNoRegistradoColaborationProposals(HttpSession session,Model model){
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("colaborationProposals", colaborationProposalDAO.getProposalAbilitated());
        model.addAttribute("skillTypes",skillTypeDAO.getSkillTypes());
        return "colaborationProposal/listN";
    }

    @RequestMapping("/listSKP")
    public String listSKPColaborationProposals(HttpSession session,Model model){
        model.addAttribute("colaborationProposals", colaborationProposalDAO.getProposalAbilitated());
        model.addAttribute("skillTypes",skillTypeDAO.getSkillTypes());
        return "colaborationProposal/listSKP";
    }

    @RequestMapping(value = "/add/{requestId}")
    public String addColaborationProposal(HttpSession session,Model model, @PathVariable int requestId){
        session.setAttribute("nextUrl", "/user/list");
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("email", user.getUsername());
        ColaborationRequest request=colaborationRequestDAO.getColaborationRequest(requestId);
        ColaborationProposal proposal=new ColaborationProposal();
        proposal.setIdSkill(request.getIdSkill());
        proposal.setDateEnd(request.getDateEnd());
        proposal.setDateStart(request.getDateStart());
        model.addAttribute("colaborationProposal",proposal);
        List<Integer> idList = skillTypeDAO.getSkillTypesIds();
        model.addAttribute("idList", idList);
        List<SkillType> skillTypes = skillTypeDAO.getSkillTypesAbilitados();
        model.addAttribute("skillTypes", skillTypes);
        return "colaborationProposal/add";
    }

    @RequestMapping(value = "/add")
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
        List<SkillType> skillTypes = skillTypeDAO.getSkillTypesAbilitados();
        model.addAttribute("skillTypes", skillTypes);
        return "colaborationProposal/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("colaborationProposal") ColaborationProposal colaborationProposal,
                                    BindingResult bindingResult,HttpSession session, Model model){
        ColaborationProposalValidator colaborationProposalValidator =new ColaborationProposalValidator(studentDAO);
        colaborationProposalValidator.validate(colaborationProposal,bindingResult);
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
            return "colaborationProposal/add";
        }
        List<String> emailList =colaborationRequestDAO.getColaborationRequestEmailsByTimeAndTime(
                colaborationProposal.getIdSkill(),
                colaborationProposal.getDateStart(),
                colaborationProposal.getDateEnd());
        SkillType skill=skillTypeDAO.getSkillType(colaborationProposal.getIdSkill());

        for(String email:emailList) {
            emailService.sendEmail(
                    email,
                    "Nueva propuesta de colaboración",
                    "Se ha creado una propuesta de colaboración de la habilidad "
                            + skill.getName()+"-"+skill.getLevel()
                            + " entre " + colaborationProposal.getDateStart()
                            + " y " + colaborationProposal.getDateEnd()
            );
        }
        colaborationProposalDAO.addColaborationProposal(colaborationProposal);
        return "redirect:list";
    }


    @RequestMapping(value = "/update/{proposalId}", method = RequestMethod.GET)
    public String editColaborationProposal(Model model, @PathVariable int proposalId){
        model.addAttribute("colaborationProposal", colaborationProposalDAO.getColaborationProposal(proposalId));
        List<Integer> idList = skillTypeDAO.getSkillTypesIds();
        model.addAttribute("idList", idList);
        List<SkillType> skillTypes = skillTypeDAO.getSkillTypesAbilitados();
        model.addAttribute("skillTypes", skillTypes);
        return "colaborationProposal/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(Model model,
            @ModelAttribute("colaborationProposal") ColaborationProposal colaborationProposal,
            BindingResult bindingResult){
        ColaborationProposalValidator colaborationProposalValidator =new ColaborationProposalValidator(studentDAO);
        colaborationProposalValidator.validate(colaborationProposal,bindingResult);
        if (bindingResult.hasErrors()) {
            List<Integer> idList = skillTypeDAO.getSkillTypesIds();
            model.addAttribute("idList", idList);
            List<SkillType> skillTypes = skillTypeDAO.getSkillTypesAbilitados();
            model.addAttribute("skillTypes", skillTypes);
            return "colaborationProposal/update";
        }
        colaborationProposalDAO.updateColaborationProposal(colaborationProposal);
        return "redirect:../user/listPersonal";
    }

    @RequestMapping(value = "/delete/{proposalId}")
    public String processDelete(@PathVariable int proposalId){
        colaborationProposalDAO.deleteColaborationProposal(proposalId);
        return "redirect:../list";
    }

    @RequestMapping("/end/{proposalId}")
    public String processEndProposal(@PathVariable int proposalId){
        colaborationProposalDAO.endProposal(proposalId);
        return "redirect:../listSKP";
    }
}