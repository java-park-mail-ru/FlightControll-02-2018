package ru.technopark.flightcontrol.dao;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.technopark.flightcontrol.models.User;
import ru.technopark.flightcontrol.wrappers.RegisterWrapper;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Scope(value = "singleton")
@Component
public final class UsersManager {

    private final HashMap<Number, User> usersMap = new HashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();

    public boolean authenticate(User user, String pass) {
        return user.checkHash(pass);
    }

    public User getUser(Number id) {
        if (userNotContains(id)) {
            return null;
        }
        return usersMap.get(id);
    }

    public User createUser(RegisterWrapper  params, Logger logger) {
        final Number id = ID_GENERATOR.getAndIncrement();
        User user = null;
        if (userNotContains(id) && checkName(params.getName())) {
             user = new User(id, params.getEmail(), params.getName(), params.getPass(), logger);
        }
        usersMap.put(id, user);
        return user;
    }

    public User getByName(String login) {
        User matchedUser = null;
        for (User user : usersMap.values()) {
            if (user != null && user.getLogin().equals(login)) {
                matchedUser = user;
            }
        }
        return matchedUser;
    }


    public void changeUser(User user, RegisterWrapper params) {
        user.setLogin(params.getName());
        user.setEmail(params.getEmail());
        user.changePass(params.getPass());
    }

    private boolean userNotContains(Number id) {
        return !usersMap.containsKey(id);
    }

    private boolean checkName(String name) {
        boolean isFree = true;
        for (User user : usersMap.values()) {
            if (user != null && user.getLogin().equals(name)) {
                isFree = false;
            }
        }
        return isFree;
    }

}
