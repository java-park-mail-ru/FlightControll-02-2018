package ru.technopark.flightcontrol.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public class RegisterWrapper {
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String repeatPassword;
    @JsonIgnore
    private MultipartFile img;

    public RegisterWrapper(String name, String email, String password, String repass, MultipartFile img) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.repeatPassword = repass;
        this.img = img;
    }

    public RegisterWrapper() {

    }

    public String getUserName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public MultipartFile getImg() { return img; }
}
