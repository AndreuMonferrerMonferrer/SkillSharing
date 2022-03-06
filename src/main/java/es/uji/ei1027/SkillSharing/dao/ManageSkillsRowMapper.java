package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.ManageSkills;


import org.springframework.jdbc.core.RowMapper;
import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ManageSkillsRowMapper implements RowMapper<ManageSkills> {

    public ManageSkills mapRow(ResultSet rs, int rowNum) throws SQLException {
        ManageSkills manageSkills = new ManageSkills();
        manageSkills.setIdSkillType(rs.getInt("idSkillType"));
        manageSkills.setEmail(rs.getString("email"));
        return manageSkills;
    }

}
