package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public final class StudentRowMapper implements
            RowMapper<Student>{
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException{
        Student student = new Student();
        student.setEmail(rs.getString("email"));
        student.setIsSkp(rs.getString("isSKP"));
        student.setName(rs.getString("name"));
        student.setPwd(rs.getString("pwd"));
        student.setRecivedHours(rs.getInt("recivedHours"));
        student.setProvidedHours(rs.getInt("providedHours"));
        student.setTelNumber(rs.getInt("telNumber"));
        student.setDegree(rs.getString("degree"));
        return student;
    }
}
