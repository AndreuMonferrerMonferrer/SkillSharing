package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class StudentValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Student.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Student student=(Student) obj;
        if(student.getName().trim().equals("")) {
            errors.rejectValue("name", "required", "you have to enter a value");
        }
        if(student.getPwd().trim().equals("")) {
            errors.rejectValue("pwd", "required", "you have to enter a value");
        }
        if(student.getEmail().trim().equals("")) {
            errors.rejectValue("email", "required", "you have to enter a value");
        }
        if(student.getDegree().trim().equals("")) {
            errors.rejectValue("degree", "required", "you have to enter a value");
        }
        if(String.valueOf(student.getTelNumber()).length()!=9) {
            errors.rejectValue("telNumber", "length incorrect", "The number must have nine digits");
        }
        if(!student.getIsSkp().equals("N") && !student.getIsSkp().equals("S")) {
            errors.rejectValue("isSkp", "incorrectValue", "the value has to be S or N");
        }
        if(student.getName().length()>50) {
            errors.rejectValue("name", "length exceeded", "the length has to be less than 50");
        }
        if(student.getEmail().length()>50) {
            errors.rejectValue("email", "length exceeded", "the length has to be less than 50");
        }
        if(student.getPwd().length()>30) {
            errors.rejectValue("pwd", "length exceeded", "the length has to be less than 30");
        }
        if(student.getDegree().length()>50) {
            errors.rejectValue("degree", "length exceeded", "the length has to be less than 50");
        }
        /*if(!student.getAbilitationState().equals("N")||!student.getAbilitationState().equals("S")){
            errors.rejectValue("abilitationState","incorrectValue","has to be an S or an N");
        }*/

    }
}