package ru.technopark.flightcontrol.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.technopark.flightcontrol.dao.UsersManager;
import ru.technopark.flightcontrol.models.User;
import ru.technopark.flightcontrol.validators.Validator;
import ru.technopark.flightcontrol.wrappers.AuthWrapper;
import ru.technopark.flightcontrol.wrappers.FieldsError;
import ru.technopark.flightcontrol.wrappers.RegisterWrapper;
import ru.technopark.flightcontrol.wrappers.RequestParamsException;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@RestController
@CrossOrigin(origins = "https://super-frontend.herokuapp.com/")
@RequestMapping(value = "/api/user", consumes = "application/json")
public class UsersService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);
    private static final Validator VALIDATOR = new Validator();
    @Autowired
    private UsersManager manager;

    private User prepareEnviron(HttpSession session) {
        final Number userId = (Number) session.getAttribute("userId");
        return manager.getUser(userId);
    }


    @PostMapping(value = "/register")
    public ResponseEntity registerUser(HttpSession session, @RequestBody RegisterWrapper request) {
        User curUser = prepareEnviron(session);
        if (curUser != null) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        try {
            VALIDATOR.validate(request);
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

    @PostMapping(value = "/get")
    public ResponseEntity getUser(HttpSession session) {
        final User curUser = prepareEnviron(session);
        if (curUser == null) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(curUser);
    }


    @PostMapping(value = "/authenticate")
    public ResponseEntity authUser(HttpSession session, @RequestBody AuthWrapper request) {
        final User curUser = prepareEnviron(session);
        if (curUser != null) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }

        try {
            VALIDATOR.validate(request);
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
        final User curUser = prepareEnviron(session);
        if (curUser == null) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        manager.changeUser(curUser, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/logout")
    public ResponseEntity logout(HttpSession session) {
        final User curUser = prepareEnviron(session);
        if (curUser != null) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        session.removeAttribute("userId");
        return  ResponseEntity.ok().build();
    }

}

