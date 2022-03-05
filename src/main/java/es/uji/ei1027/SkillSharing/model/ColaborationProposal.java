package es.uji.ei1027.SkillSharing.model;

import java.util.Date;

public class ColaborationProposal {
    private int proposalId;
    private Date dateStart;
    private Date dateEnd;
    private String description;
    private String emailStudent;
    private int idSkill;

    public int getProposalId() {
        return proposalId;
    }

    public void setProposalId(int proposalId) {
        this.proposalId = proposalId;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
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
        return "ColaborationProposal{" +
                "proposalId=" + proposalId +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", description='" + description + '\'' +
                ", emailStudent='" + emailStudent + '\'' +
                ", idSkill=" + idSkill +
                '}';
    }
}
