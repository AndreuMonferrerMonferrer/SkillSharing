package es.uji.ei1027.SkillSharing.controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.SkillSharing.dao.*;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    private ColaborationRequestDAO colaborationRequestDAO;

    @Autowired
    public void setColaborationRequestDAO(ColaborationRequestDAO colaborationRequestDAO){
        this.colaborationRequestDAO=colaborationRequestDAO;
    }

    private ColaborationProposalDAO colaborationProposalDAO;

    @Autowired
    public void setColaborationProposalDAO(ColaborationProposalDAO colaborationProposalDAO){
        this.colaborationProposalDAO=colaborationProposalDAO;
    }

    private ColaborationDAO colaborationDAO;

    @Autowired
    public void setColaborationDAO(ColaborationDAO colaborationDAO){
        this.colaborationDAO=colaborationDAO;
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

    @RequestMapping("/listPersonal")
    public String listPersonal(HttpSession session, Model model){
        session.setAttribute("nextUrl","/user/listPersonal");
        if(session.getAttribute("user")==null){
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        UserDetails user = (UserDetails) session.getAttribute("user");
        String email = user.getUsername();
        model.addAttribute("email", email);

        model.addAttribute("colaborationRequests", colaborationRequestDAO.getColaborationRequests(email));

        model.addAttribute("colaborationProposal",colaborationProposalDAO.getColaborationProposals(email));

        model.addAttribute("colaboration", colaborationDAO.getColaborationsStudent(email));

        return (String) session.getAttribute("nextUrl");
    }

    @RequestMapping("/profile")
    public String listProfile(HttpSession session, Model model){
        session.setAttribute("nextUrl","/user/profile");
        if(session.getAttribute("user")==null){
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("email", user.getUsername());
        Student student = studentDAO.getStudent(user.getUsername());
        model.addAttribute("student",student);

        return (String) session.getAttribute("nextUrl");
    }
}

