package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.SkillType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillTypeRowMapper implements
            RowMapper<SkillType> {

    @Override
    public SkillType mapRow(ResultSet rs, int rowNum) throws SQLException {
        SkillType skillType=new SkillType();
        skillType.setId(rs.getInt("email"));
        skillType.setName(rs.getString("name"));
        //level
        skillType.setDescription(rs.getString("descripcion"));
        skillType.setAbilitationState(rs.getBoolean("abilitationState"));
        return skillType;
    }
}
