package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Colaboration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ColaborationDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addColaboration(Colaboration colaboration){
        jdbcTemplate.update("INSERT INTO Colaboration VALUES(?,?,?,?,?)",
                colaboration.getProposalId(),colaboration.getRequestId(),
                colaboration.getDateStart(),colaboration.getDateEnd(),
                colaboration.getDescription());
    }

    public void deleteColaboration(Colaboration colaboration){
        jdbcTemplate.update("DELETE from Colaboration where proposalId=? AND requestId=?",
                colaboration.getProposalId(), colaboration.getRequestId());
    }

    public void deleteColaboration(int proposalId, int requestId){
        jdbcTemplate.update("DELETE from Colaboration where proposalId=? AND requestId=?",
                proposalId, requestId);
    }

    public void updateColaboration(Colaboration colaboration){
        jdbcTemplate.update("UPADTE Colaboration SET dateStart=?, dateEnd=?,description=? WHERE proposalId=? and requestId=?",
                colaboration.getDateStart(), colaboration.getDateEnd(), colaboration.getDescription(),
                colaboration.getProposalId(), colaboration.getRequestId());
    }

    public Colaboration getColaboration(int proposalId, int requestId){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM Colaboration WHERE proposalId=? AND requestId=?",
                    new ColaborationRowMapper(),proposalId, requestId);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Colaboration> getColaborations() {
        try{
            return jdbcTemplate.query("SELECT * FROM Colaboration",
                    new ColaborationRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

}
