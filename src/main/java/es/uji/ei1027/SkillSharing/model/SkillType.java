package es.uji.ei1027.SkillSharing.model;


public class SkillType {
    private int id;
    private String name;
    private String description;
    private String abilitationState;
    private String level;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbilitationState() {
        return this.abilitationState;
    }

    public void setAbilitationState(String abilitationState) {
        this.abilitationState = abilitationState;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "SkillType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", abilitationState=" + abilitationState +
                ", level=" + level +
                '}';
    }
}
