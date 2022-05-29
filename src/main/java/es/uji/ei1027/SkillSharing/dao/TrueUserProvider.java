package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Student;
import es.uji.ei1027.SkillSharing.model.UserDetails;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TrueUserProvider implements UserDao{
    final Map<String, UserDetails> knownUsers = new HashMap<String, UserDetails>();

    private StudentDAO studentDAO;
    @Autowired
    public TrueUserProvider(StudentDAO studentDAO){
        this.studentDAO=studentDAO;
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        List<Student> listStudents = studentDAO.getStudents();
        UserDetails newUser;
        for (int i = 0; i< listStudents.size(); i++){
            if (listStudents.get(i).getAbilitationState().equals("S")){
                newUser = new UserDetails();
                newUser.setUsername(listStudents.get(i).getEmail());
                newUser.setPassword(listStudents.get(i).getPwd());
                newUser.setSkp(listStudents.get(i).getIsSkp().equals("S"));
                knownUsers.put(listStudents.get(i).getEmail(),newUser);
            }
        }
    }

    public void addUser(Student student){

        UserDetails newUser = new UserDetails();
        newUser.setUsername(student.getEmail());
        newUser.setPassword(student.getPwd());
        newUser.setSkp(student.getIsSkp().equals("S"));
        knownUsers.put(student.getEmail(),newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username, String password) {
        UserDetails user = knownUsers.get(username.trim());
        if (user == null)
            return null; // Usuari no trobat
        // Contrasenya
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if (passwordEncryptor.checkPassword(password, user.getPassword())) {
            password=null;
            return user;
        }
        else {
            return null; // bad login!
        }
    }

    @Override
    public Collection<UserDetails> listAllUsers() {
        return knownUsers.values();
    }

    @Override
    public boolean deleteUser(String username) {
        UserDetails deleted = knownUsers.remove(username);
        return deleted!=null;
    }
}
