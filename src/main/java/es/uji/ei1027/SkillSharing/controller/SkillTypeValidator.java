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

    }
}