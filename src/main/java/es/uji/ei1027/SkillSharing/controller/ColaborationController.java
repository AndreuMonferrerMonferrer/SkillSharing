package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.ColaborationDAO;
import es.uji.ei1027.SkillSharing.dao.ColaborationProposalDAO;
import es.uji.ei1027.SkillSharing.dao.ColaborationRequestDAO;
import es.uji.ei1027.SkillSharing.model.Colaboration;
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
@RequestMapping("/colaboration")
public class ColaborationController {

    private ColaborationDAO colaborationDAO;
    private ColaborationProposalDAO colaborationProposalDAO;
    private ColaborationRequestDAO colaborationRequestDAO;

    @Autowired
    public void setColaborationDAO(ColaborationDAO colaborationDAO){
        this.colaborationDAO=colaborationDAO;
    }

    @Autowired
    public void setColaborationProposalDAO(ColaborationProposalDAO colaborationProposalDAO){this.colaborationProposalDAO=colaborationProposalDAO;}

    @Autowired
    public void setColaborationRequestDAO(ColaborationRequestDAO colaborationRequestDAO){this.colaborationRequestDAO = colaborationRequestDAO;}

    @RequestMapping("/list")
    public String listColaborations(Model model){
        model.addAttribute("colaborations",colaborationDAO.getColaborations());
        return "colaboration/list";
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
        return "redirect:list";
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
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{proposalId}/{requestId}")
    public String processDeleteColaboration(@PathVariable int proposalId,
                                       @PathVariable int requestId) {
        colaborationDAO.deleteColaboration(proposalId, requestId);
        return "redirect:../../list";
    }





}
