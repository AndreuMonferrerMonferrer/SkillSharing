package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Colaboration;
import es.uji.ei1027.SkillSharing.model.ColaborationProposal;
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
public class ColaborationProposalDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){jdbcTemplate = new JdbcTemplate(dataSource);}

    public void addColaborationProposal(ColaborationProposal colaborationProposal){
        jdbcTemplate.update("INSERT INTO ColaborationProposal (dateStart,dateEnd,description,emailStudent,idSkill) VALUES(?,?,?,?,?)",
          colaborationProposal.getDateStart(),colaborationProposal.getDateEnd(),
          colaborationProposal.getDescription(),colaborationProposal.getEmailStudent(),colaborationProposal.getIdSkill());
    }
    public void deleteColaborationProposal(int proposalId){
        jdbcTemplate.update("DELETE from ColaborationProposal WHERE proposalId=?",
                proposalId);
    }
    public void deleteColaborationProposal(ColaborationProposal colaborationProposal){
        jdbcTemplate.update("DELETE from ColaborationProposal WHERE proposalId=?",
                colaborationProposal.getProposalId());
    }
    public void updateColaborationProposal(ColaborationProposal colaborationProposal){
        jdbcTemplate.update("UPDATE ColaborationProposal SET dateStart=?, dateEnd=?,description=?," +
                        " emailStudent=?, idSkill=? WHERE proposalId=?",
                colaborationProposal.getDateStart(), colaborationProposal.getDateEnd(),
                colaborationProposal.getDescription(), colaborationProposal.getEmailStudent(),
                colaborationProposal.getIdSkill(), colaborationProposal.getProposalId());
    }

    public ColaborationProposal getColaborationProposal(int proposalId){
        try{
            return jdbcTemplate.queryForObject("SELECT * from ColaborationProposal WHERE proposalId=?",
                    new ColaborationProposalRowMapper(), proposalId);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    public List<ColaborationProposal> getColaborationProposals(){
        try{
            return jdbcTemplate.query("SELECT * from ColaborationProposal",
                    new ColaborationProposalRowMapper());
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<ColaborationProposal>();
        }
    }

    public List<Integer> getProposalId(){
        try{
            return jdbcTemplate.query("SELECT proposalId FROM ColaborationProposal",
                    new SingleColumnRowMapper(Integer.class));
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<Integer>();
        }
    }

    /*todas las ColaborationProposal de un alumno*/
    public List<ColaborationProposal> getColaborationProposalsByUser(String emailStudent){
        try{
            return this.jdbcTemplate.query("SELECT * FROM ColaborationProposal WHERE emailStudent=?",
                    new ColaborationProposalRowMapper(), emailStudent);
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<ColaborationProposal>();
        }
    }


    public List<ColaborationProposal> getProposalAbilitated(){
        try{
            return jdbcTemplate.query("SELECT * FROM ColaborationProposal WHERE emailStudent IN (SELECT email from Student where abilitationState='S')",
                    new ColaborationProposalRowMapper());
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<ColaborationProposal>();
        }
    }

    /*todas las ColaborationProposal otros alumnos*/
    public List<ColaborationProposal> getColaborationProposalsByOtherUsers(String emailStudent){
        try{
            return this.jdbcTemplate.query("SELECT * FROM ColaborationProposal WHERE emailStudent IN (SELECT email from Student where abilitationState='S') AND emailStudent!=?",
                    new ColaborationProposalRowMapper(), emailStudent);
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<ColaborationProposal>();
        }
    }
}
