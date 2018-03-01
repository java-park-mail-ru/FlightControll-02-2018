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

    @Override
    public void validate() throws RequestParamsException {
        //todo: change exception wrap
        if (name == null && pass == null && repass == null) {
            throw new RequestParamsException(null, "Request is empty");
        }
        validateField("name", name, 5);
        validateField("pass", pass, 6);
        validateField("repass", pass, 6);
        if (!pass.equals(repass)) {
            throw new RequestParamsException("pass", "Password is not equals to password");
        }
    }
}
