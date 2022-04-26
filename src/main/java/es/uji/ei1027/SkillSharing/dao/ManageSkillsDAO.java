package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.ManageSkills;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ManageSkillsDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){jdbcTemplate = new JdbcTemplate(dataSource);}

    public void addManageSkills(ManageSkills manageSkills){
        jdbcTemplate.update("INSERT INTO ManageSkills VALUES(?, ?)",
                manageSkills.getIdSkillType(),manageSkills.getEmail());

    }

    public void deleteManageSkills(int idSkillType,String email){
        jdbcTemplate.update("DELETE from ManageSkills WHERE idSkillType=? and email=?",
                idSkillType,email);
    }

    public void deleteManageSkills(ManageSkills manageSkills){
        jdbcTemplate.update("DELETE from ManageSkills WHERE idSkillType=? and email=?",
                manageSkills.getIdSkillType(),manageSkills.getEmail());
    }

    //el update no se puede

    public ManageSkills getManageSkills(int idSkillType,String email){
        try{
            return jdbcTemplate.queryForObject("SELECT * from ManageSkills WHERE idSkillType= and email=?",
                    new ManageSkillsRowMapper(), idSkillType,email);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<ManageSkills> getManageSkillsList(){
        try{
            return jdbcTemplate.query("SELECT * from ManageSkills",
                    new ManageSkillsRowMapper());
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<ManageSkills>();
        }
    }


    //dos geters uno para sacar todas las filas de un email concreto y otro para sacar todas las filas de una skill concreta
    public List<ManageSkills> getManageSkillsEmail(String email){
        try{
            return this.jdbcTemplate.query("SELECT * FROM ManageSkills WHERE email=?",
                    new ManageSkillsRowMapper(), email);
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<ManageSkills>();
        }
    }

    public List<ManageSkills> getManageSkillsIdSkillType(int idSkillType){
        try{
            return this.jdbcTemplate.query("SELECT * FROM ManageSkills WHERE idSkillType=?",
                    new ManageSkillsRowMapper(), idSkillType);
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<ManageSkills>();
        }
    }

}
