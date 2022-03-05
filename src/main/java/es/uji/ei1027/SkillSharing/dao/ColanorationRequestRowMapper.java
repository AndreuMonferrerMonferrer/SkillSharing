package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.ColaborationRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ColanorationRequestRowMapper implements RowMapper<ColaborationRequest> {
    @Override
    public ColaborationRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        ColaborationRequest colaborationRequest = new ColaborationRequest();
        colaborationRequest.setRequestId(rs.getInt("requestId"));
        colaborationRequest.setDateStart(rs.getObject("dateStart", LocalDate.class));
        colaborationRequest.setDateEnd(rs.getObject("dateEnd", LocalDate.class));
        colaborationRequest.setDescription(rs.getString("description"));
        colaborationRequest.setEmailStudent(rs.getString("emailStudent"));
        colaborationRequest.setIdSkill(rs.getInt("idSkill"));
        return colaborationRequest;
    }
}
