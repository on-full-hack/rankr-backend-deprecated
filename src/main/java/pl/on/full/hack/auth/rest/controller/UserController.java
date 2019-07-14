package pl.on.full.hack.auth.rest.controller;

import com.auth0.jwt.JWT;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.on.full.hack.auth.config.SecurityConstants;
import pl.on.full.hack.auth.dto.UserDTO;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.auth.exception.UserAlreadyExistsException;
import pl.on.full.hack.auth.service.UserService;
import pl.on.full.hack.base.dto.BaseApiContract;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RestController
public class UserController {

    private UserService userService;

    @Value("${security.jwt.secret}")
    private String secret;
    @Value("#{new Long('${security.jwt.expiration.time}')}")
    private Long expirationTime;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = SecurityConstants.SIGN_UP_URL)
    public ResponseEntity<BaseApiContract<UserDTO>> signUp(@RequestBody UserDTO user) {
        final BaseApiContract<UserDTO> responseBody = new BaseApiContract<>();
        try {
            userService.signUp(user);
            //Na pewno chcemy zwracać usera z hashowanym hasłem?
            responseBody.setSpecificContract(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (UserAlreadyExistsException e) {
            responseBody.setError("User " + user.getUsername() + " already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        } catch (Exception e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping(path = "/user/current")
    public ResponseEntity<BaseApiContract<UserDTO>> getLeagueDetails(Authentication authentication) {
        final BaseApiContract<UserDTO> responseBody = new BaseApiContract<>();
        try {
            final String username = (String) authentication.getPrincipal();
            responseBody.setSpecificContract(userService.getDetails(username));
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (UsernameNotFoundException e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            return BaseApiContract.internalServerError(e);
        }
    }

    @DeleteMapping(path = "/user")
    public ResponseEntity<BaseApiContract<Void>> deleteUser(Authentication authentication) {
        final BaseApiContract<Void> responseBody = new BaseApiContract<>();
        try {
            final String username = (String) authentication.getPrincipal();
            userService.deleteUser(username);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (UsernameNotFoundException e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            return BaseApiContract.internalServerError(e);
        }
    }

    @ApiOperation(value = "Created for username or password update.", notes = "Every update generates new JWT token cause we base on username when we generate token")
    @PutMapping(path = "/user")
    public ResponseEntity<BaseApiContract<Void>> updateUser(@RequestBody UserDTO user, Authentication authentication) {
        final BaseApiContract<Void> responseBody = new BaseApiContract<>();
        try {
            final String username = (String) authentication.getPrincipal();
            RankrUser updatedRankrUser = userService.updateUser(user, username);
            String token = JWT.create()
                    .withSubject(updatedRankrUser.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                    .sign(HMAC512(secret.getBytes()));
            return ResponseEntity.status(HttpStatus.ACCEPTED).header(SecurityConstants.AUTH_HEADER, SecurityConstants.TOKEN_PREFIX + token).body(responseBody);
        } catch (UsernameNotFoundException e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (UserAlreadyExistsException e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        } catch (Exception e) {
            return BaseApiContract.internalServerError(e);
        }
    }
}
