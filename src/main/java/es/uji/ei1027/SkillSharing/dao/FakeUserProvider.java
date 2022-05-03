package es.uji.ei1027.SkillSharing.dao;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uji.ei1027.SkillSharing.model.Student;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import es.uji.ei1027.SkillSharing.model.UserDetails;

@Repository
public class FakeUserProvider implements UserDao {
    final Map<String, UserDetails> knownUsers = new HashMap<String, UserDetails>();
    private StudentDAO studentDAO;

    @Autowired
    public void setStudentDAO(StudentDAO studentDAO){this.studentDAO=studentDAO;}


    public FakeUserProvider() {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        UserDetails userPablo = new UserDetails();
        userPablo.setUsername("pablo@uji.es");
        userPablo.setPassword(passwordEncryptor.encryptPassword("pablo"));
        knownUsers.put("pablo@uji.es", userPablo);

        UserDetails userAaron = new UserDetails();
        userAaron.setUsername("aaron@uji.es");
        userAaron.setPassword(passwordEncryptor.encryptPassword("aaron"));
        knownUsers.put("aaron@uji.es", userAaron);

        UserDetails userAndreu = new UserDetails();
        userAndreu.setUsername("andreu@uji.es");
        userAndreu.setPassword(passwordEncryptor.encryptPassword("andreu"));
        knownUsers.put("andreu@uji.es", userAndreu);
    }

    @Override
    public UserDetails loadUserByUsername(String username, String password) {
        UserDetails user = knownUsers.get(username.trim());
        if (user == null)
            return null; // Usuari no trobat
        // Contrasenya
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if (passwordEncryptor.checkPassword(password, user.getPassword())) {
            // Es deuria esborrar de manera segura el camp password abans de tornar-lo
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
}

