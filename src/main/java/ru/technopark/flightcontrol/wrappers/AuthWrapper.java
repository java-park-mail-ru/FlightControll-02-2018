package ru.technopark.flightcontrol.wrappers;

public class AuthWrapper extends Wrapper {
    private String name;
    private String pass;
    private String repass;

    public AuthWrapper() {
        super();
    }

    public AuthWrapper(String email, String pass, String repass) {
        this.name = email;
        this.pass = pass;
        this.repass = repass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
