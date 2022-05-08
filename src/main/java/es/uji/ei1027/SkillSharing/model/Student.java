package es.uji.ei1027.SkillSharing.model;

public class Student {


    private String email;
    private String isSkp;
    private String name;
    private String pwd;
    private int recivedHours;
    private int providedHours;
    private int telNumber;
    private String degree;
    private String abilitationState;

    public String getIsSkp() {
        return isSkp;
    }

    public void setIsSkp(String isSkp) {
        this.isSkp = isSkp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getRecivedHours() {
        return recivedHours;
    }

    public void setRecivedHours(int recivedHours) {
        this.recivedHours = recivedHours;
    }

    public int getProvidedHours() {
        return providedHours;
    }

    public void setProvidedHours(int providedHours) {
        this.providedHours = providedHours;
    }

    public int getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(int telNumber) {
        this.telNumber = telNumber;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbilitationState() {return abilitationState;}

    public void setAbilitationState(String abilitationState) {
        this.abilitationState = abilitationState;
    }

    @Override
    public String toString() {
        return "Student{" +
                "email='" + email + '\'' +
                ", isSkp='" + isSkp + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", recivedHours=" + recivedHours +
                ", providedHours=" + providedHours +
                ", telNumber=" + telNumber +
                ", degree='" + degree + '\'' +
                ", abilitationState='" + abilitationState + '\'' +
                '}';
    }
}
