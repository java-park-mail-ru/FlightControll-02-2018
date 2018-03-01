package ru.technopark.flightcontrol.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.technopark.flightcontrol.dao.UsersManager;
import ru.technopark.flightcontrol.models.User;
import ru.technopark.flightcontrol.wrappers.AuthWrapper;
import ru.technopark.flightcontrol.wrappers.FieldsError;
import ru.technopark.flightcontrol.wrappers.RegisterWrapper;
import ru.technopark.flightcontrol.wrappers.RequestParamsException;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/user", consumes = "application/json")
public class UsersService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);
    private UsersManager manager;
    private User curUser;
    private boolean hasUser;

    private void prepareEnviron(HttpSession session) {
        final Number userId = (Number) session.getAttribute("userId");
        manager = UsersManager.getManager();
        curUser = manager.getUser(userId);
        hasUser = curUser != null && userId != null;
    }


    @PostMapping(value = "/register")
    public ResponseEntity registerUser(HttpSession session, @RequestBody RegisterWrapper request) {
        prepareEnviron(session);
        if (hasUser) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        try {
            request.validate();
            curUser = manager.createUser(request, LOGGER);
            if (curUser == null) {
                return ResponseEntity.badRequest().body(new FieldsError("user", " is used"));
            } else {
                session.setAttribute("userId", curUser.getId());
            }
        } catch (RequestParamsException exception) {
            return ResponseEntity.badRequest().body(exception.getFieldErrors());
        }
        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = "localhost:8080")
    @PostMapping(value = "/get")
    public ResponseEntity getUser(HttpSession session) {
        prepareEnviron(session);
        if (!hasUser) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(curUser);
    }


    @PostMapping(value = "/authenticate")
    public ResponseEntity authUser(HttpSession session, @RequestBody AuthWrapper request) {
        prepareEnviron(session);
        if (hasUser) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }

        try {
            request.validate();
            final User user = manager.getByName(request.getName());
            if (user == null) {
                return ResponseEntity.badRequest().body("Unsaved user");
            }
            final boolean isValid = manager.authenticate(user, request.getPass());
            if (isValid) {
                session.setAttribute("userId", user.getId());
            }
        } catch (RequestParamsException exception) {
            return ResponseEntity.badRequest().body(exception.getFieldErrors());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/change")
    public ResponseEntity changeUser(HttpSession session, @RequestBody RegisterWrapper request) {
        prepareEnviron(session);
        if (!hasUser) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        manager.changeUser(curUser, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/logout")
    public ResponseEntity logout(HttpSession session) {
        prepareEnviron(session);
        if (!hasUser) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        session.removeAttribute("userId");
        return  ResponseEntity.ok().build();
    }

}

