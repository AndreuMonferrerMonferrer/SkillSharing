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

    @RequestMapping(value = "/delete/{proposalId}/{requestId}")
    public String processDeleteColaboration(@PathVariable int proposalId,
                                       @PathVariable int requestId) {
        colaborationDAO.deleteColaboration(proposalId, requestId);
        return "redirect:../../listSKP";
    }

    class TupleHours{
        int proposalId;
        int requestId;
        int hours;
        public int getProposalId() {
            return proposalId;
        }

        public int getRequestId() {
            return requestId;
        }

        public int getHours() {
            return hours;
        }

        public void setProposalId(int proposalId) {
            this.proposalId = proposalId;
        }

        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }
    }

    @RequestMapping(value = "/end/{proposalId}/{requestId}")
    public String processEndColaboration(
            Model model,
            @PathVariable int proposalId,
            @PathVariable int requestId){
        TupleHours tupleHours=new TupleHours();
        tupleHours.setProposalId(proposalId);
        tupleHours.setRequestId(requestId);
        model.addAttribute("tuple",tupleHours);
        return "colaboration/end";

    }


    @RequestMapping(value = "/end", method = RequestMethod.POST)
    public String processUpdateAndSubmit(
            Model model,
            @ModelAttribute("tuple") TupleHours tuple
            ){
        colaborationDAO.endColaboration(tuple.getProposalId(), tuple.getRequestId(), tuple.getHours());
        return "redirect:listSKP";
    }





}
