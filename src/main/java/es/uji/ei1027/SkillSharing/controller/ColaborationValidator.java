package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.model.Colaboration;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ColaborationValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {return Colaboration.class.equals(cls);    }

    @Override
    public void validate(Object obj, Errors errors) {
        Colaboration colaboration = (Colaboration) obj;
        if (colaboration.getDescription().length() > 100) {
            errors.rejectValue("description", "length exceeded", "the length has to be less than 100");
        }
        if (colaboration.getProposalId()< 0){
            errors.rejectValue("proposalId","negative number","the number has to be positive");
        }
        if (colaboration.getRequestId()< 0){
            errors.rejectValue("requestId","negative number","the number has to be positive");
        }
        if(colaboration.getDateStart().isAfter(colaboration.getDateEnd())){
            errors.rejectValue("dateStart","temporal issue", "the start date has to be before the end date");
        }
        if(colaboration.getDateEnd().isBefore(colaboration.getDateStart())){
            errors.rejectValue("dateEnd","temporal issue", "the end date has to be after the start date");
        }

    }
}
