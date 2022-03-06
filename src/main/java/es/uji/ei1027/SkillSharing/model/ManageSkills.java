package es.uji.ei1027.SkillSharing.model;

public class ManageSkills {
    private int idSkillType;
    private String email;

    public int getIdSkillType() {
        return idSkillType;
    }

    public void setIdSkillType(int idSkillType) {
        this.idSkillType = idSkillType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ManageSkills{" +
                "nameskillType='" + idSkillType + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
