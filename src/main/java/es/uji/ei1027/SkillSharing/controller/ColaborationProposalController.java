package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.ColaborationProposalDAO;
import es.uji.ei1027.SkillSharing.model.ColaborationProposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/colaborationProposal")
public class ColaborationProposalController {

    private ColaborationProposalDAO colaborationProposalDAO;

    @Autowired
    public void setColaborationProposalDAO(ColaborationProposalDAO colaborationProposalDAO){
        this.colaborationProposalDAO=colaborationProposalDAO;
    }

    @RequestMapping("/list")
    public String listColaborationProposals(Model model){
        model.addAttribute("colaborationProposals", colaborationProposalDAO.getColaborationProposals());
        return "colaborationProposal/list";
    }

    @RequestMapping(value = "/add")
    public String addColaborationProposal(Model model){
        model.addAttribute("colaborationProposal", new ColaborationProposal());
        return "colaborationProposal/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAndSubmit(@ModelAttribute("colaborationProposal") ColaborationProposal colaborationProposal,
                                   BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "colaborationProposal/add";
        colaborationProposalDAO.addColaborationProposal(colaborationProposal);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{proposalId}", method = RequestMethod.GET)
    public String editColaborationProposal(Model model, @PathVariable int proposalId){
        model.addAttribute("colaborationProposal", colaborationProposalDAO.getColaborationProposal(proposalId));
        return "colaborationProposal/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("colaborationProposal") ColaborationProposal colaborationProposal,
            BindingResult bindingResult){
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