package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.StudentDAO;
import es.uji.ei1027.SkillSharing.dao.UserDao;
import es.uji.ei1027.SkillSharing.model.Student;
import es.uji.ei1027.SkillSharing.model.UserDetails;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/student")
public class StudentController {


    private StudentDAO studentDAO;

    @Autowired
    public void setStudentDAO(StudentDAO studentDAO){
        this.studentDAO=studentDAO;
    }

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao){
        this.userDao=userDao;
    }

    @RequestMapping("/list")
    public String listStudents(Model model){
        model.addAttribute("students", studentDAO.getStudents());
        return "student/list";
    }

    @RequestMapping("/listTrue")
    public String listUsers(HttpSession session, Model model){
        session.setAttribute("nextUrl", "/student/listTrue");
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }

        if (studentDAO.getStudent(user.getUsername()).getIsSkp().equals("N")){
            return "/user/profile";
        }
        model.addAttribute("students", studentDAO.getStudents());
        return "student/listTrue";
    }

    @RequestMapping(value = "/add")
    public String addStudentNormal(Model model){
        model.addAttribute("student", new Student());
        return "student/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAndSubmitNormal(@ModelAttribute("student") Student student,
                                   BindingResult bindingResult) {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        StudentNormalValidator studentNormalValidator = new StudentNormalValidator();
        studentNormalValidator.validate(student, bindingResult);
        if (bindingResult.hasErrors())
            return "student/add";
        student.setPwd(passwordEncryptor.encryptPassword(student.getPwd()));
        student.setIsSkp("N");
        student.setAbilitationState("S");
        userDao.addUser(student);
        studentDAO.addStudentNormal(student);
        return "redirect:../user/profile";
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

    @RequestMapping(value = "/disable/{email}")
    public String processDisable(@PathVariable String email){
        boolean disabled = userDao.deleteUser(email);
        if (disabled){
            studentDAO.disableStudent(email);
        }
        return "redirect:../listTrue";
    }
}
