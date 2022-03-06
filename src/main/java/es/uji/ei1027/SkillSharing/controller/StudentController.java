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

import javax.naming.Binding;

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
        model.addAttribute("Students", studentDAO.getStudents());
        return "Student/list";
    }

    @RequestMapping(value = "/add")
    public String addStudent(Model model){
        model.addAttribute("Student", new Student());
        return "Student/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAndSubmit(@ModelAttribute("Student") Student student,
                                   BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "Student/add";
        studentDAO.addStudent(student);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{email}", method = RequestMethod.GET)
    public String editStudent(Model model, @PathVariable String email){
        model.addAttribute("Student", studentDAO.getStudent(email));
        return "Student/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("Student") Student student,
            BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "Student/update";
        studentDAO.updateStudent(student);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{email}")
    public String processDelete(@PathVariable String email){
        studentDAO.deleteStudent(email);
        return "redirect:../list";
    }
}
