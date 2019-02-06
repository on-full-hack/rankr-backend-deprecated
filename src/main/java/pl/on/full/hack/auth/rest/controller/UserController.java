package pl.on.full.hack.auth.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.on.full.hack.auth.config.SecurityConstants;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.auth.exception.UserAlreadyExistsException;
import pl.on.full.hack.auth.repository.UserRepository;
import pl.on.full.hack.auth.service.UserService;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = SecurityConstants.SIGN_UP_URL)
    public ResponseEntity<String> signUp(@RequestBody RankrUser user) {
        try {
            userService.signUp(user);
            return ResponseEntity.ok().body(user.getUsername() + "has successfully registered");
        }
        catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User " + user.getUsername() + " already exists");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause().getMessage());
        }
    }
}
