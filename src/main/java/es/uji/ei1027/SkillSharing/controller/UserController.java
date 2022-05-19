package es.uji.ei1027.SkillSharing.controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.SkillSharing.dao.StudentDAO;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.SkillSharing.dao.UserDao;
import es.uji.ei1027.SkillSharing.model.UserDetails;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private StudentDAO studentDAO;

    @Autowired
    public void setStudentDAO(StudentDAO studentDAO){
        this.studentDAO = studentDAO;
    }

    @RequestMapping("/list")
    public String listUsers(HttpSession session, Model model) {
        session.setAttribute("nextUrl", "/user/list");
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        model.addAttribute("users", userDao.listAllUsers());

        return (String) session.getAttribute("nextUrl");

    }

    @RequestMapping("/profile")
    public String listProfile(HttpSession session, Model model){
        session.setAttribute("nextUrl","/user/list");
        if(session.getAttribute("user")==null){
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("email", user.getUsername());
        Student student = studentDAO.getStudent(user.getUsername());
        model.addAttribute("student",student);
        return "profile/profile";
    }
}

