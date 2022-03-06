package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Colaboration;
import es.uji.ei1027.SkillSharing.model.ColaborationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ColaborationRequestDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addColaborationRequest(ColaborationRequest colaborationRequest){
        jdbcTemplate.update("INSERT INTO ColaborationRequest VALUES(?, ?, ?, ?, ?, ?)",
                colaborationRequest.getRequestId(),colaborationRequest.getDateStart(),
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

    public List<ColaborationRequest> getColaborationRequests() {
        try{
            return jdbcTemplate.query("SELECT * FROM ColaborationRequest",
                    new ColaborationRequestRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
