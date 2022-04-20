package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.ManageSkillsDAO;
import es.uji.ei1027.SkillSharing.model.ManageSkills;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class ManageSkillsValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return Student.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ManageSkills manageSkills = (ManageSkills) obj;
        if (manageSkills.getEmail().trim().equals("")) {
            errors.rejectValue("email", "required", "you have to enter a value");
        }
        //FALTA COMPROBAR SI EL EMAIL YA ESTABA
    }
}
