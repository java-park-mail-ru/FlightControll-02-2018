package ru.technopark.flightcontrol.wrappers;

public class RegisterWrapper {
    private String name;
    private String email;
    private String pass;
    private String repass;

    public RegisterWrapper(String name, String email, String pass, String repass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.repass = repass;
    }

    public RegisterWrapper() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRepass() {
        return repass;
    }

    public void setRepass(String repass) {
        this.repass = repass;
    }
}
