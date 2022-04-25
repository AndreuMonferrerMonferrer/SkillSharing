package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.ColaborationRequestDAO;
import es.uji.ei1027.SkillSharing.dao.SkillTypeDAO;
import es.uji.ei1027.SkillSharing.model.ColaborationRequest;
import es.uji.ei1027.SkillSharing.model.SkillType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/colaborationRequest")
public class ColaborationRequestController {

    private ColaborationRequestDAO colaborationRequestDAO;
    private SkillTypeDAO skillTypeDAO;
    @Autowired
    public void setColaborationRequestDAO(ColaborationRequestDAO colaborationRequestDAO){
        this.colaborationRequestDAO=colaborationRequestDAO;
    }
    @Autowired
    public void setSkillTypeDAO(SkillTypeDAO skillTypeDAO){
        this.skillTypeDAO=skillTypeDAO;
    }


    @RequestMapping("/list")
    public String listColaboratioRequests(Model model){
        model.addAttribute("colaborationRequests", colaborationRequestDAO.getColaborationRequests());
        return "colaborationRequest/list";
    }

    @RequestMapping(value = "/add")
    public String addColaborationRequest(Model model){
        model.addAttribute("colaborationRequest", new ColaborationRequest());
        List<Integer> idList = skillTypeDAO.getSkillTypesIds();
        model.addAttribute("idList", idList);
        return "colaborationRequest/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAndSubmit(@ModelAttribute("colaborationRequest") ColaborationRequest colaborationRequest,
                                   BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "colaborationRequest/add";
        colaborationRequestDAO.addColaborationRequest(colaborationRequest);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{requestId}", method = RequestMethod.GET)
    public String editColaborationRequest(Model model, @PathVariable int requestId){
        model.addAttribute("colaborationRequest", colaborationRequestDAO.getColaborationRequest(requestId));
        return "colaborationRequest/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("colaborationRequest") ColaborationRequest colaborationRequest,
            BindingResult bindingResult){
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