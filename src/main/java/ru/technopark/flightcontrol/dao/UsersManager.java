package ru.technopark.flightcontrol.dao;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.technopark.flightcontrol.comparators.RatingsComparator;
import ru.technopark.flightcontrol.models.User;
import ru.technopark.flightcontrol.wrappers.RegisterWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Scope(value = "singleton")
@Component
public final class UsersManager {

    private final HashMap<Number, User> usersMap = new HashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private final RatingsComparator<Object> ratingComparator = new RatingsComparator<>();

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
        User user = null;
        try {
            final Number id = ID_GENERATOR.getAndIncrement();
            if (userNotContains(id) && checkName(params.getUserName())) {
                user = new User(id, params.getEmail(), params.getUserName(), params.getPassword(), params.getImg());
            }
            usersMap.put(id, user);
        } catch (IOException exception) {
            logger.error("avatar is corrupted");
        }
        return user;
    }

    public User getByEmail(String email) {
        User matchedUser = null;
        for (User user : usersMap.values()) {
            if (user != null && user.getEmail().equals(email)) {
                matchedUser = user;
            }
        }
        return matchedUser;
    }


    public void changeUser(User user, RegisterWrapper params) {
        user.setName(params.getUserName());
        user.setEmail(params.getEmail());
        user.changePass(params.getPassword());
    }

    private boolean userNotContains(Number id) {
        return !usersMap.containsKey(id);
    }

    private boolean checkName(String name) {
        boolean isFree = true;
        for (User user : usersMap.values()) {
            if (user != null && user.getName().equals(name)) {
                isFree = false;
            }
        }
        return isFree;
    }

    public ArrayList<User> getLeaders(int page, int size) {
        if ((page - 1) * size > usersMap.size()) {
            return new ArrayList<>();
        }
        final ArrayList<User> ratingTable = new ArrayList<>(usersMap.values());
        ratingTable.sort(ratingComparator);
        final int offset = (page - 1) * size;
        return new ArrayList<>(ratingTable.subList(offset, offset + size));
    }

    public int getLeadersCount() {
        return usersMap.size();
    }

}
