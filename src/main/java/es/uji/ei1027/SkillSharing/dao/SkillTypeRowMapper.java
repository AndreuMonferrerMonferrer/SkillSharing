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
        skillType.setId(rs.getInt("id"));
        skillType.setName(rs.getString("name"));
        skillType.setDescription(rs.getString("description"));
        skillType.setAbilitationState(rs.getString("abilitationState"));
        skillType.setLevel(rs.getString("level"));

        return skillType;
    }
}
