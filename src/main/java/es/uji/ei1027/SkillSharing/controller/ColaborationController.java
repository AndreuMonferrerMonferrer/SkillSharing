package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.*;
import es.uji.ei1027.SkillSharing.model.Colaboration;
import es.uji.ei1027.SkillSharing.model.ColaborationProposal;
import es.uji.ei1027.SkillSharing.model.ColaborationRequest;
import es.uji.ei1027.SkillSharing.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/colaboration")
public class ColaborationController {

    private ColaborationDAO colaborationDAO;
    private ColaborationProposalDAO colaborationProposalDAO;
    private ColaborationRequestDAO colaborationRequestDAO;
    private SkillTypeDAO skillTypeDAO;
    private StudentDAO studentDAO;

    @Autowired
    public void setColaborationDAO(ColaborationDAO colaborationDAO){
        this.colaborationDAO=colaborationDAO;
    }

    @Autowired
    public void setColaborationProposalDAO(ColaborationProposalDAO colaborationProposalDAO){this.colaborationProposalDAO=colaborationProposalDAO;}

    @Autowired
    public void setColaborationRequestDAO(ColaborationRequestDAO colaborationRequestDAO){this.colaborationRequestDAO = colaborationRequestDAO;}

    @Autowired
    public void setSkillTypeDAO(SkillTypeDAO skillTypeDAO){this.skillTypeDAO=skillTypeDAO;}

    @Autowired
    public void setStudentDAO(StudentDAO studentDAO){this.studentDAO=studentDAO;}

    @RequestMapping("/listSKP")
    public String listColaborations(HttpSession session, Model model){
        session.setAttribute("nextUrl", "/colaboration/listSKP");
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }

        if (studentDAO.getStudent(user.getUsername()).getIsSkp().equals("N")){
            return "/user/profile";
        }
        class tupleColab {
            private final Colaboration colabo;
            private final ColaborationRequest request;
            private final ColaborationProposal proposal;

            tupleColab (Colaboration colabo, ColaborationRequest request, ColaborationProposal proposal){
                this.colabo=colabo;
                this.request=request;
                this.proposal=proposal;
            }

            public Colaboration getColabo() {
                return colabo;
            }

            public ColaborationRequest getRequest() {
                return request;
            }

            public ColaborationProposal getProposal() {
                return proposal;
            }
        }

        List<Colaboration> colabos =  colaborationDAO.getColaborationsNotEnded();
        List<tupleColab> colabs = new ArrayList<>();
        for (Colaboration colabo:colabos) {
            colabs.add(new tupleColab(colabo,colaborationRequestDAO.getColaborationRequest(colabo.getRequestId()),colaborationProposalDAO.getColaborationProposal(colabo.getProposalId())));
        }

        model.addAttribute("colaborations",colabs);

        model.addAttribute("skillTypes",  skillTypeDAO.getSkillTypes());

        return "colaboration/listSKP";
    }


    @RequestMapping("/add")
    public String addColaboration(Model model){
        model.addAttribute("colaboration",new Colaboration());
        List<Integer> requestIdList = colaborationRequestDAO.getRequestId();
        model.addAttribute("requestIdList", requestIdList);
        List<Integer> proposalIdList = colaborationProposalDAO.getProposalId();
        model.addAttribute("proposalIdList", proposalIdList);
        return "colaboration/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAndSubmit(@ModelAttribute("colaboration") Colaboration colaboration,
                                   BindingResult bindingResult){
        ColaborationValidator colaborationValidator=new ColaborationValidator();
        colaborationValidator.validate(colaboration,bindingResult);
        if (bindingResult.hasErrors())
            return "colaboration/add";
        colaborationDAO.addColaboration(colaboration);
        return "redirect:listSKP";
    }



    @RequestMapping(value ="/update/{proposalId}/{requestId}", method = RequestMethod.GET)
    public String editColaboration(Model model,
                                   @PathVariable int proposalId,
                                   @PathVariable int requestId){
        model.addAttribute("colaboration",colaborationDAO.getColaboration(proposalId,requestId));
        return "colaboration/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateAndSubmit(
            @ModelAttribute("colaboration") Colaboration colaboration,
            BindingResult bindingResult){
        ColaborationValidator colaborationValidator=new ColaborationValidator();
        colaborationValidator.validate(colaboration,bindingResult);
        if (bindingResult.hasErrors())
            return "colaboration/update";
        colaborationDAO.updateColaboration(colaboration);
        return "redirect:listSKP";
    }

    @RequestMapping(value = "/delete/{proposalId}/{requestId}")
    public String processDeleteColaboration(@PathVariable int proposalId,
                                       @PathVariable int requestId) {
        colaborationDAO.deleteColaboration(proposalId, requestId);
        return "redirect:../../listSKP";
    }

    @RequestMapping(value = "/end/{proposalId}/{requestId}")
    public String processEndColaboration(HttpSession session, @PathVariable int proposalId, @PathVariable int requestId){
        colaborationDAO.endColaboration(proposalId, requestId);
        UserDetails user = (UserDetails) session.getAttribute("user");
        if(user.isSkp()){
            return "redirect:../../listSKP";
        }
        return "redirect:../.././user/listPersonal";
    }





}
