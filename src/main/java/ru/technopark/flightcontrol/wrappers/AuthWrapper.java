package ru.technopark.flightcontrol.wrappers;

public class AuthWrapper {
    private String email;
    private String pass;

    public AuthWrapper() {

    }

    public AuthWrapper(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
