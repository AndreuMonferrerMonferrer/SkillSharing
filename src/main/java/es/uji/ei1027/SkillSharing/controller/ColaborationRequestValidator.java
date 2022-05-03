package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.StudentDAO;
import es.uji.ei1027.SkillSharing.model.ColaborationRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ColaborationRequestValidator implements Validator {

    private StudentDAO studentDAO;

    public ColaborationRequestValidator(StudentDAO studentDAO){
        super();
        this.studentDAO=studentDAO;
    }

    public boolean supports(Class<?> cls) {return ColaborationRequest.class.equals(cls);    }

    @Override
    public void validate(Object obj, Errors errors) {
        ColaborationRequest request = (ColaborationRequest) obj;
        if (!studentDAO.getEmails().contains(request.getEmailStudent())){
            errors.rejectValue("emailStudent", "email nonexistent", "there is no student with that email");
        }
        if (request.getDescription().length() > 100){
            errors.rejectValue("description", "length exceeded", "the length has to be <= 100");
        }
        if (request.getRequestId()< 0){
            errors.rejectValue("requestId","negative number","the number has to be positive");
        }
        if(request.getDateStart().isAfter(request.getDateEnd())){
            errors.rejectValue("dateStart","temporal issue", "the start date has to be before the end date");
        }
        if(request.getDateEnd().isBefore(request.getDateStart())){
            errors.rejectValue("dateEnd","temporal issue", "the end date has to be after the start date");
        }
    }

}
