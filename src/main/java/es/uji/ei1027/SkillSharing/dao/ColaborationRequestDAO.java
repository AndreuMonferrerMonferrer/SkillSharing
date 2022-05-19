package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.ColaborationProposal;
import es.uji.ei1027.SkillSharing.model.ColaborationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ColaborationRequestDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addColaborationRequest(ColaborationRequest colaborationRequest){
        jdbcTemplate.update("INSERT INTO ColaborationRequest (dateStart,dateEnd,description,emailStudent,idSkill) VALUES(?,?,?,?,?)",
                colaborationRequest.getDateStart(),
                colaborationRequest.getDateEnd(), colaborationRequest.getDescription(),
                colaborationRequest.getEmailStudent(), colaborationRequest.getIdSkill());
    }

    public void deleteColaborationRequest(int requestId){
        jdbcTemplate.update("DELETE from ColaborationRequest where requestId=?",
                requestId);
    }

    public void deleteColaborationRequest(ColaborationRequest request){
        jdbcTemplate.update("DELETE from ColaborationRequest where requestId=?",
                request.getRequestId());
    }

    public void updateColaborationRequest(ColaborationRequest colaborationRequest){
        jdbcTemplate.update("UPDATE ColaborationRequest SET dateStart=?, dateEnd=?,description=?, emailStudent=?, idSkill=? where requestId=?",
                colaborationRequest.getDateStart(), colaborationRequest.getDateEnd(),
                colaborationRequest.getDescription(), colaborationRequest.getEmailStudent(),
                colaborationRequest.getIdSkill(), colaborationRequest.getRequestId());
    }

    public ColaborationRequest getColaborationRequest(int requestId){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM ColaborationRequest WHERE requestId=?",
                    new ColaborationRequestRowMapper(), requestId);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Integer> getRequestId(){
        try{
            return jdbcTemplate.query("SELECT requestId FROM ColaborationRequest",
                    new SingleColumnRowMapper(Integer.class));
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<Integer>();
        }
    }

    public List<ColaborationRequest> getColaborationRequests() {
        try{
            return jdbcTemplate.query("SELECT * FROM ColaborationRequest",
                    new ColaborationRequestRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<ColaborationRequest> getRequestAbilitated(){
        try{
            return jdbcTemplate.query("SELECT * FROM ColaborationRequest WHERE emailStudent IN (SELECT email from Student where abilitationState='S')",
                    new ColaborationRequestRowMapper());
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<ColaborationRequest>();
        }
    }

    /*todas las ColaborationRequest de un alumno*/
    public List<ColaborationRequest> getColaborationRequests(String emailStudent){
        try{
            return this.jdbcTemplate.query("SELECT * FROM ColaborationRequest WHERE emailStudent=?",
                    new ColaborationRequestRowMapper(), emailStudent);
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<ColaborationRequest>();
        }
    }
}
