package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Colaboration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public final class ColaborationRowMapper implements RowMapper<Colaboration> {
    @Override
    public Colaboration mapRow(ResultSet rs, int rowNum) throws SQLException {
        Colaboration colaboration = new Colaboration();
        colaboration.setProposalId(rs.getInt("proposalId"));
        colaboration.setRequestId(rs.getInt("requestId"));
        colaboration.setDateStart(rs.getObject("dateStart", LocalDate.class));
        colaboration.setDateEnd(rs.getObject("dateEnd", LocalDate.class));
        colaboration.setDescription(rs.getString("description"));
        return colaboration;
    }
}
