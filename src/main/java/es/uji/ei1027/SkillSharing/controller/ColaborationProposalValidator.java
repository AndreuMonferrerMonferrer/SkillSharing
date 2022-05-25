package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.SkillTypeDAO;
import es.uji.ei1027.SkillSharing.dao.StudentDAO;
import es.uji.ei1027.SkillSharing.model.ColaborationProposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ColaborationProposalValidator implements Validator {

    private StudentDAO studentDAO;

    public ColaborationProposalValidator(StudentDAO studentDAO){
        super();
        this.studentDAO=studentDAO;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return ColaborationProposal.class.equals(cls);
    }



    @Override
    public void validate(Object obj, Errors errors) {
        ColaborationProposal proposal = (ColaborationProposal) obj;
        if (!studentDAO.getEmails().contains(proposal.getEmailStudent())){
            errors.rejectValue("emailStudent", "email nonexistent", "there is no student with that email");
        }
        if (proposal.getDescription().length() > 100){
            errors.rejectValue("description", "length exceeded", "the length has to be <= 100");
        }
        if (proposal.getProposalId()< 0){
            errors.rejectValue("proposalId","negative number","the number has to be positive");
        }
        if(proposal.getDateStart().isAfter(proposal.getDateEnd())){
            errors.rejectValue("dateStart","temporal issue", "the start date has to be before the end date");
        }
        if(proposal.getDateEnd().isBefore(proposal.getDateStart())){
            errors.rejectValue("dateEnd","temporal issue", "the end date has to be after the start date");
        }

    }
}
