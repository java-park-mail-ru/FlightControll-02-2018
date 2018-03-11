package ru.technopark.flightcontrol.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        creatorVisibility = JsonAutoDetect.Visibility.NONE
)
public class User {
    private Number id;
    @JsonProperty
    private String email;
    @JsonProperty
    private String name;
    @JsonProperty
    private final int rate = 0;
    private String hash;
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public int getRate() {
        return rate;
    }

    public User(Number id, String email, String login, String pass) {
        this.id = id;
        this.email = email;
        this.name = login;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changePass(String pass) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            setHash(digest, pass);
        } catch (NoSuchAlgorithmException exception) {
            hash = "";
        }
    }

    private void setHash(MessageDigest digest, String pass) {
        final byte[] hashed = digest.digest(pass.getBytes(CHARSET));
        hash = new String(hashed);
    }

    public boolean checkHash(String pass) {
        String newHash;
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hashed = digest.digest(pass.getBytes(CHARSET));
            newHash = new String(hashed);
        } catch (NoSuchAlgorithmException exception) {
            newHash = "";
        }
        return this.hash.equals(newHash);
    }
}
