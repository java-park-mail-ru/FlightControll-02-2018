package ru.technopark.flightcontrol.wrappers;

abstract class Wrapper {

    public abstract void validate() throws RequestParamsException;

    protected void validateField(String name, String field, int requriedLength) throws RequestParamsException {
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
}
