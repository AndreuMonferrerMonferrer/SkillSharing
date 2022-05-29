package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.model.SkillType;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SkillTypeValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return SkillType.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        SkillType skillType=(SkillType) obj;
        if(skillType.getName()==null) {
            errors.rejectValue("name", "required", "you have to enter a value");
        }
        if(skillType.getName().trim().equals("")) {
            errors.rejectValue("name", "required", "you have to enter a value");
        }
        if(skillType.getName().length()>20) {
            errors.rejectValue("name", "lengthExceeded", "Length has to be less than 20");
        }
        if(skillType.getAbilitationState().trim().equals("")) {
            errors.rejectValue("ailitationState", "required", "you have to enter a value");
        }
        if(skillType.getDescription().trim().equals("")) {
            errors.rejectValue("description", "required", "you have to enter a value");
        }
        if(skillType.getDescription().length()>100) {
            errors.rejectValue("description", "lengthExceeded", "Length has to be <=100");
        }
    }
}