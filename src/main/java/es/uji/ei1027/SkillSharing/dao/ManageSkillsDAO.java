package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.ManageSkills;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class ManageSkillsDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){jdbcTemplate = new JdbcTemplate(dataSource);}

    public void addManageSkills(ManageSkills manageSkills){
        jdbcTemplate.update("INSERT INTO ManageSkills VALUES(?, ?)",
                manageSkills.getIdSkillType(),manageSkills.getEmail());

    }

    public void deleteManageSkills(String idSkillType,String email){
        jdbcTemplate.update("DELETE from ManageSkills WHERE idSkillType=? and email=?",
                idSkillType,email);
    }

    public void deleteManageSkills(ManageSkills manageSkills){
        jdbcTemplate.update("DELETE from ManageSkills WHERE idSkillType=? and email=?",
                manageSkills.getIdSkillType(),manageSkills.getEmail());
    }

    //el update no se puede



}
