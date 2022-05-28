package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.StudentDAO;
import es.uji.ei1027.SkillSharing.model.ManageSkills;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class ManageSkillsValidator implements Validator {

    private StudentDAO studentDAO;

    public ManageSkillsValidator(StudentDAO studentDAO){
        super();
        this.studentDAO=studentDAO;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return ManageSkills.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ManageSkills manageSkills = (ManageSkills) obj;
        if (!studentDAO.getEmails().contains(manageSkills.getEmail())){
            errors.rejectValue("email", "email nonexistent", "there is no student with that email");
        }
        if(manageSkills.getIdSkillType()<0){
            errors.rejectValue("idSkillType","negative number","the number has to be positive");
        }
    }
}
