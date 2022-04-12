package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.ColaborationProposal;

import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public final class ColaborationProposalRowMapper implements
        RowMapper<ColaborationProposal> {
    @Override
    public ColaborationProposal mapRow(ResultSet rs, int rowNum) throws SQLException {
        ColaborationProposal colaborationProposal = new ColaborationProposal();
        colaborationProposal.setProposalId(rs.getInt("proposalId"));
        //colaborationProposal.setDateStart(rs.getObject("dateStart", LocalDate.class));
        colaborationProposal.setDateStart(rs.getString("dateStart"));
        //colaborationProposal.setDateEnd(rs.getObject("dateEnd", LocalDate.class));
        colaborationProposal.setDateStart(rs.getString("dateEnd"));
        colaborationProposal.setDescription(rs.getString("description"));
        colaborationProposal.setEmailStudent(rs.getString("emailStudent"));
        colaborationProposal.setIdSkill(rs.getInt("idSKill"));
        return colaborationProposal;
    }
}
