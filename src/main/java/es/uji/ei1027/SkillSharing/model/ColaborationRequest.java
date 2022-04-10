package es.uji.ei1027.SkillSharing.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ColaborationRequest {

    private int requestId;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String description;
    private String emailStudent;
    private int idSkill;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateStart(String  dateStart) {
        this.dateStart =this.dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setDateEnd(String  dateEnd) {
        this.dateEnd =this.dateEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmailStudent() {
        return emailStudent;
    }

    public void setEmailStudent(String emailStudent) {
        this.emailStudent = emailStudent;
    }

    public int getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(int idSkill) {
        this.idSkill = idSkill;
    }

    @Override
    public String toString() {
        return "ColaborationRequest{" +
                "requestId=" + requestId +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", description='" + description + '\'' +
                ", emailStudent='" + emailStudent + '\'' +
                ", idSkill=" + idSkill +
                '}';
    }
}
