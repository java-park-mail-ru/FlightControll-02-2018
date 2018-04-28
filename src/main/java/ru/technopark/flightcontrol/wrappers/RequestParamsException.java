package ru.technopark.flightcontrol.wrappers;

public class RequestParamsException extends Exception {
    private FieldsError errors;

    public RequestParamsException(String field, String message) {
        errors = new FieldsError(field, message);
    }

    public FieldsError getFieldErrors() {
        return errors;
    }
}
