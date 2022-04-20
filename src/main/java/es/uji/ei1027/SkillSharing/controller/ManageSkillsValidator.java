package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.model.ManageSkills;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class ManageSkillsValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return ManageSkills.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ManageSkills manageSkills = (ManageSkills) obj;
        if (manageSkills.getEmail().trim().equals("")) {
            errors.rejectValue("email", "required", "you have to enter a value");
        }
        if(manageSkills.getIdSkillType()<0){
            errors.rejectValue("idSkillType","negative number","the number has to be positive");
        }
        //FALTA COMPROBAR SI EL EMAIL YA ESTABA
    }
}
