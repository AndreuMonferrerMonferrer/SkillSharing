package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.StudentDAO;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentDAO studentDAO;

    @Autowired
    public void setStudentDAO(StudentDAO studentDAO){
        this.studentDAO=studentDAO;
    }

    @RequestMapping("/list")
    public String listStudents(Model model){
        model.addAttribute("students", studentDAO.getStudents());
        return "student/list";
    }

    @RequestMapping(value = "/add")
    public String addStudent(Model model){
        model.addAttribute("student", new Student());
        return "student/add";
    }

    @RequestMapping(value = "/addNormal")
    public String addStudentNormal(Model model){
        model.addAttribute("student", new Student());
        return "student/addNormal";
    }

    @RequestMapping(value = "/addNormal", method = RequestMethod.POST)
    public String processAndSubmitNormal(@ModelAttribute("student") Student student,
                                   BindingResult bindingResult){
        StudentNormalValidator studentNormalValidator=new StudentNormalValidator();
        studentNormalValidator.validate(student,bindingResult);
        if (bindingResult.hasErrors())
            return "student/addNormal";
        studentDAO.addStudentNormal(student);
        return "redirect:list";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAndSubmit(@ModelAttribute("student") Student student,
                                   BindingResult bindingResult){
        StudentValidator studentValidator=new StudentValidator();
        studentValidator.validate(student,bindingResult);
        if (bindingResult.hasErrors())
            return "student/add";
        studentDAO.addStudent(student);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{email}", method = RequestMethod.GET)
    public String editStudent(Model model, @PathVariable String email){
        model.addAttribute("student", studentDAO.getStudent(email));
        return "student/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("student") Student student,
            BindingResult bindingResult){
        StudentValidator studentValidator=new StudentValidator();
        studentValidator.validate(student,bindingResult);
        if (bindingResult.hasErrors())
            return "student/update";
        studentDAO.updateStudent(student);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{email}")
    public String processDelete(@PathVariable String email){
        studentDAO.deleteStudent(email);
        return "redirect:../list";
    }

    @RequestMapping(value = "/disable/{email}")
    public String processDisable(@PathVariable String email){
        studentDAO.deleteStudent(email);
        return "redirect:../listTrue";
    }
}
