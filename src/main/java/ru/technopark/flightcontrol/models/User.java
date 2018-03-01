package ru.technopark.flightcontrol.models;

import org.slf4j.Logger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class User {
    private Number id;
    private String email;
    private String login;
    private String hash;
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public User(Number id, String email, String login, String pass) {
        this.id = id;
        this.email = email;
        this.login = login;
        changePass(pass);
    }

    public Number getId() {
        return id;
    }

    public String getEmail() {
        return email;

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void changePass(String pass) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            setHash(digest, pass);
        } catch (NoSuchAlgorithmException exception) {
            hash = "";
        }
    }

    public String getHash() {
        return hash;
    }

    private void setHash(MessageDigest digest, String pass) {
        final byte[] hashed = digest.digest(pass.getBytes(CHARSET));
        hash = new String(hashed);
    }

    public boolean checkHash(String pass) {
        String newHash;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(pass.getBytes(CHARSET));
            newHash = new String(hashed);
        } catch (NoSuchAlgorithmException exception) {
            newHash = "";
        }

        return this.hash.equals(newHash);
    }
}
