package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.ColaborationProposal;

import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public final class ColaborationProposalRowMapper implements
        RowMapper<ColaborationProposal> {
    @Override
    public ColaborationProposal mapRow(ResultSet rs, int rowNum) throws SQLException {
        System.out.println("rowmapper ");
        ColaborationProposal colaborationProposal = new ColaborationProposal();
        colaborationProposal.setProposalId(rs.getInt("proposalId"));
        colaborationProposal.setDateStart(rs.getDate("dateStart"));
        colaborationProposal.setDateEnd(rs.getDate("dateEnd"));
        colaborationProposal.setDescription(rs.getString("description"));
        colaborationProposal.setEmailStudent(rs.getString("emailStudent"));
        colaborationProposal.setIdSkill(rs.getInt("idSKill"));
        return colaborationProposal;
    }
}
