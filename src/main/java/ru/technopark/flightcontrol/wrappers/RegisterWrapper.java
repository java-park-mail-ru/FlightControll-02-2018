package ru.technopark.flightcontrol.wrappers;

public class RegisterWrapper extends Wrapper {
    private String name;
    private String email;
    private String pass;

    public RegisterWrapper(String name, String email, String pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    public RegisterWrapper() {

    }

    @Override
    public void validate() throws RequestParamsException {
        if (name == null && email == null && pass == null) {
            throw new RequestParamsException(null, "Request is empty");
        }
        validateField("name", name, 5);
        validateField("email", email, 10);
        validateField("pass", pass, 6);
        if (pass.equals(name) || pass.equals(email)) {
            throw new RequestParamsException("pass", "Password is equals to another fields");
        }
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
}
