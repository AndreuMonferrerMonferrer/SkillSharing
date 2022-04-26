package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.SkillType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SkillTypeDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);}

    public void addSkillType(SkillType skillType){//como funciona un serial ¿bool?
        jdbcTemplate.update("INSERT INTO SkillType ( name, level, description,abilitationState) VALUES(?, ?, ?, ?)",
                skillType.getName(),skillType.getLevel(),skillType.getDescription(),skillType.getAbilitationState());
    }

    public void deleteSkillType(int id){//¿pongo name o id?
        jdbcTemplate.update("DELETE from SkillType WHERE id=?",
                id);
    }

    public void deleteSkillType(SkillType skillType){
        jdbcTemplate.update("DELETE from SkillType WHERE id=?",
                skillType.getId());
    }

    public void updateSkillType(SkillType skillType){//¿bool?
        jdbcTemplate.update("UPDATE skillType SET name=? , level=? , description=? ,  abilitationState=? WHERE id=?",
                skillType.getName(), skillType.getLevel(),skillType.getDescription(),skillType.getAbilitationState(),skillType.getId());
    }

    public SkillType getSkillType(int id){
        try{
            return jdbcTemplate.queryForObject("SELECT * from SkillType WHERE id=?",
                    new SkillTypeRowMapper(), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<SkillType> getSkillTypes(){
        try{
            return jdbcTemplate.query("SELECT * from SkillType",
                    new SkillTypeRowMapper());
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<SkillType>();
        }
    }

    public List<Integer> getSkillTypesIds(){
        try{
            return jdbcTemplate.query("SELECT id FROM SkillType",
                    new SingleColumnRowMapper(Integer.class));
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<Integer>();
        }
    }

    public List<SkillType> getSkillTypeId(String id){
        try{
            return this.jdbcTemplate.query("SELECT * FROM SkillType WHERE id=?",
                    new SkillTypeRowMapper(), id);
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<SkillType>();
        }
    }

}
