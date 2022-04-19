package es.uji.ei1027.SkillSharing.dao;

import java.util.Collection;
import es.uji.ei1027.SkillSharing.model.UserDetails;

public interface UserDao {
    UserDetails loadUserByUsername(String username, String password);
    Collection<UserDetails> listAllUsers();
}