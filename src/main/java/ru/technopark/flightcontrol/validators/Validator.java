package ru.technopark.flightcontrol.validators;

import ru.technopark.flightcontrol.wrappers.AuthWrapper;
import ru.technopark.flightcontrol.wrappers.RegisterWrapper;
import ru.technopark.flightcontrol.wrappers.RequestParamsException;

public class Validator {
    public void validateField(String name, String field, int requriedLength) throws RequestParamsException {
        if (field == null) {
            throw new RequestParamsException(name, name + " is empty");
        }
        //todo: create email regex
        if (!field.matches("[a-zA-Z@.0-9]+")) {
            throw new RequestParamsException(name, name + " is incorrect");
        }
        if (field.length() < requriedLength) {
            throw new RequestParamsException(name, name + " is too short");
        }
    }

    public void validate(AuthWrapper wrapper) throws RequestParamsException {
        final String name = wrapper.getName();
        final String pass = wrapper.getPass();
        final String rePass = wrapper.getRepass();
        if (name == null && pass == null && rePass == null) {
            throw new RequestParamsException(null, "Request is empty");
        }

        validateField("name", name, 5);
        validateField("pass", pass, 6);
        validateField("repass", rePass, 6);
        if (pass != null && !pass.equals(rePass)) {
            throw new RequestParamsException("pass", "Password is not equals to password");
        }
    }

    public void validate(RegisterWrapper wrapper) throws RequestParamsException {
        final String name = wrapper.getName();
        final String pass = wrapper.getPass();
        final String email = wrapper.getEmail();
        if (name == null && email == null && pass == null) {
            throw new RequestParamsException(null, "Request is empty");
        }
        validateField("name", name, 5);
        validateField("email", email, 10);
        validateField("pass", pass, 6);
        if (pass != null && pass.equals(name) || pass.equals(email)) {
            throw new RequestParamsException("pass", "Password is equals to another fields");
        }
    }

}
