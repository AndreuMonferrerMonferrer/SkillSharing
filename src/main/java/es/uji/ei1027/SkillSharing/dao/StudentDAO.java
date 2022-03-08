package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){jdbcTemplate = new JdbcTemplate(dataSource);}

    public void addStudent(Student student){
            jdbcTemplate.update("INSERT INTO Student VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                    student.getEmail(),student.getIsSkp(),student.getName(),student.getPwd(),student.getRecivedHours(),student.getProvidedHours(),student.getTelNumber(),student.getDegree());
    }
    public void deleteStudent(String email){
        jdbcTemplate.update("DELETE from Student WHERE email=?",
                email);
    }
    public void deleteStudent(Student student){
        jdbcTemplate.update("DELETE from Student WHERE email=?",
                student.getEmail());
    }
    public void updateStudent(Student student){
        jdbcTemplate.update("UPDATE student SET isSKP=?, name=?, pwd=?,recivedHours=?, providedHours=?, telNumber=?, degree=? WHERE email=?",
                student.getIsSkp(),student.getName(),student.getPwd(),student.getRecivedHours(), student.getProvidedHours(),student.getTelNumber(),student.getDegree(),student.getEmail());
    }

    public Student getStudent(String email){
        try{
            return jdbcTemplate.queryForObject("SELECT * from Student WHERE email=?",
                    new StudentRowMapper(), email);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    public List<Student> getStudents(){
        try{
            return jdbcTemplate.query("SELECT * from Student",
                    new StudentRowMapper());
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<Student>();
        }
    }
    public List<Student> getStudentEmail(String email){
        try{
            return this.jdbcTemplate.query("SELECT * FROM Student WHERE email=?",
                    new StudentRowMapper(), email);
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<Student>();
        }
    }
}
