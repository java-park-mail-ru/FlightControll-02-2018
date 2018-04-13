package ru.technopark.flightcontrol.validators;

import org.springframework.web.multipart.MultipartFile;
import ru.technopark.flightcontrol.wrappers.AuthWrapper;
import ru.technopark.flightcontrol.wrappers.PaginateWrapper;
import ru.technopark.flightcontrol.wrappers.RegisterWrapper;
import ru.technopark.flightcontrol.wrappers.RequestParamsException;

//todo: use springs validator
public class Validator {
    public static void validateField(String name, String field, int requriedLength) throws RequestParamsException {
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

    public static void validate(AuthWrapper wrapper) throws RequestParamsException {
        final String email = wrapper.getEmail();
        final String pass = wrapper.getPass();
        if (email == null && pass == null) {
            throw new RequestParamsException(null, "Request is empty");
        }

        validateField("name", email, 5);
        validateField("pass", pass, 6);
    }

    public static void validate(RegisterWrapper wrapper) throws RequestParamsException {
        final String name = wrapper.getUserName();
        final String pass = wrapper.getPassword();
        final String email = wrapper.getEmail();
        final String rePass = wrapper.getRepeatPassword();
        final MultipartFile file = wrapper.getImg();
        if (name == null && email == null && pass == null) {
            throw new RequestParamsException(null, "Request is empty");
        }
        validateField("name", name, 5);
        validateField("email", email, 10);
        validateField("pass", pass, 6);
        validateField("repass", rePass, 6);
        if (!pass.equals(rePass) || pass.equals(name) || pass.equals(email)) {
            throw new RequestParamsException("password", "Password is equals to another fields");
        }
        validateImage(file);
    }

    public static void validate(PaginateWrapper wrapper) throws RequestParamsException {
        final int page = wrapper.getPage();
        final int size = wrapper.getSize();
        if (page == 0 || size == 0) {
            throw new RequestParamsException(null, "Request is empty");
        }
        boolean checkBorders = page > 10 || size > 100;
        checkBorders = checkBorders || page < 1 || size < 1;
        if (checkBorders) {
            throw new RequestParamsException("page", "Page param is ambigious");
        }
    }

    public static void validateImage(final MultipartFile file) throws RequestParamsException {
        final String contentType = file.getContentType();
        if (file.isEmpty() && !(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
            throw new RequestParamsException("image", "Image is not valid");
        }
    }

}
