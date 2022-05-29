package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.*;
import es.uji.ei1027.SkillSharing.model.*;
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
    private ColaborationDAO colaborationDAO;
    private EmailService emailService;
    @Autowired
    public void setColaborationRequestDAO(ColaborationRequestDAO colaborationRequestDAO){
        this.colaborationRequestDAO=colaborationRequestDAO;
    }
    @Autowired
    public void setColaborationDAO(ColaborationDAO colaborationDAO){
        this.colaborationDAO=colaborationDAO;
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
    @Autowired
    public void setEmailService(EmailService emailService){this.emailService=emailService;}

    @RequestMapping("/list")
    public String listColaboratioRequests(HttpSession session, Model model){
        session.setAttribute("nextUrl", "/colaborationRequest/list");
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
        session.setAttribute("nextUrl", "/colaborationRequest/listSKP");
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }

        if (studentDAO.getStudent(user.getUsername()).getIsSkp().equals("N")){
            return "/user/profile";
        }
        model.addAttribute("colaborationRequests", colaborationRequestDAO.getRequestAbilitated());
        List<SkillType> skillTypes = skillTypeDAO.getSkillTypes();
        model.addAttribute("skillTypes", skillTypes);
        return "colaborationRequest/listSKP";
    }

    @RequestMapping(value = "/add/{proposalId}")
    public String addColaborationRequest(HttpSession session,Model model, @PathVariable int proposalId){
        session.setAttribute("nextUrl", "colaborationRequest/add");
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        UserDetails user = (UserDetails) session.getAttribute("user");

        ColaborationProposal proposal=colaborationProposalDAO.getColaborationProposal(proposalId);
        ColaborationRequest request=new ColaborationRequest();
        request.setIdSkill(proposal.getIdSkill());
        request.setDateEnd(proposal.getDateEnd());
        request.setDateStart(proposal.getDateStart());
        request.setDescription(String.valueOf(proposalId));
        request.setEmailStudent(user.getUsername());
        colaborationRequestDAO.addColaborationRequest(request);
        int requestId = colaborationRequestDAO.getRequestWithDescription(String.valueOf(proposalId)).getRequestId();
        Colaboration colaboration = new Colaboration();
        colaboration.setDateEnd(proposal.getDateEnd());
        colaboration.setDateStart(proposal.getDateStart());
        colaboration.setRequestId(requestId);
        colaboration.setProposalId(proposalId);
        colaboration.setDescription(proposal.getDescription());
        colaborationDAO.addColaboration(colaboration);
        colaborationRequestDAO.endRequest(requestId);
        colaborationProposalDAO.endProposal(proposalId);

        emailService.sendEmail(request.getEmailStudent(),
                "Nueva Colaboración",
                "Nueva colaboración creada entre usted y "+
                        proposal.getEmailStudent()
                        + " /n Las fechas de inicio y fin son: "+
                        colaboration.getDateStart() + " y " + colaboration.getDateEnd());

        emailService.sendEmail(proposal.getEmailStudent(),
                "Nueva Colaboración",
                "Nueva colaboración creada entre usted y "+
                        request.getEmailStudent()
                        + " /n Las fechas de inicio y fin son: "+
                        colaboration.getDateStart() + " y " + colaboration.getDateEnd());

        return "help/éxito";
    }


    @RequestMapping(value = "/add")
    public String addColaborationRequestUser(HttpSession session, Model model){
        session.setAttribute("nextUrl", "colaborationRequest/add");
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
            session.setAttribute("nextUrl","/colaborationRequest/add");
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
        List<String> emailList =colaborationProposalDAO.getColaborationProposalEmailsByTimeAndTime(
                colaborationRequest.getIdSkill(),
                colaborationRequest.getDateStart(),
                colaborationRequest.getDateEnd());
        SkillType skill=skillTypeDAO.getSkillType(colaborationRequest.getIdSkill());

        for(String email:emailList) {
            emailService.sendEmail(
                    email,
                    "Nueva propuesta de colaboración ",
                    "Se ha creado una propuesta de colaboración de la habilidad "
                            + skill.getName()+"-"+skill.getLevel()
                            + " entre " + colaborationRequest.getDateStart()
                            + " y " + colaborationRequest.getDateEnd()
            );
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
    public String processUpdateSubmit(Model model,
            @ModelAttribute("colaborationRequest") ColaborationRequest colaborationRequest,
            BindingResult bindingResult){
        ColaborationRequestValidator colaborationRequestValidator =new ColaborationRequestValidator(studentDAO);
        colaborationRequestValidator.validate(colaborationRequest,bindingResult);
        if (bindingResult.hasErrors()){
            List<Integer> idList = skillTypeDAO.getSkillTypesIds();
            model.addAttribute("idList", idList);
            List<SkillType> skillTypes = skillTypeDAO.getSkillTypesAbilitados();
            model.addAttribute("skillTypes", skillTypes);
            return "colaborationRequest/update";
        }

        colaborationRequestDAO.updateColaborationRequest(colaborationRequest);
        return "redirect:../user/listPersonal";
    }

    @RequestMapping(value = "/delete/{requestId}")
    public String processDelete(@PathVariable int requestId){
        colaborationRequestDAO.deleteColaborationRequest(requestId);
        return "redirect:../list";
    }

    @RequestMapping("/end/{requestId}")
    public String processEndRequest(@PathVariable int requestId){
        colaborationRequestDAO.endRequest(requestId);
        return "redirect:../listSKP";
    }
}