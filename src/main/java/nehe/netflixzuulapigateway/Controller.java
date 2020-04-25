package nehe.netflixzuulapigateway;


import nehe.demo.Modals.JwtResponse;
import nehe.netflixzuulapigateway.Models.JwtRequest;
import nehe.netflixzuulapigateway.Models.User;
import nehe.netflixzuulapigateway.Services.UserDetailsService;
import nehe.netflixzuulapigateway.Services.UserService;
import nehe.netflixzuulapigateway.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;



@RestController
@CrossOrigin
public class Controller {

    private AuthenticationManager authenticationManager;
    private UserService UserService;

    private JwtTokenUtil jwtTokenUtil;


    private UserDetailsService jwtInMemoryUserDetailsService;


    @Autowired
    public Controller(AuthenticationManager authenticationManager,
                                       JwtTokenUtil jwtTokenUtil,
                                       UserDetailsService jwtInMemoryUserDetailsServiee,
                                       UserService UserService
    )
    {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtInMemoryUserDetailsService = jwtInMemoryUserDetailsServiee;
        this.UserService = UserService;
    }


    //used to login in
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) throws Exception
    {
        Objects.requireNonNull(user);

        if(UserService.checkIfEmailExists(user.getEmail()))
        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("User with Email exists");
        }

        final String password = user.getPassword();
        final String email = user.getEmail();

        user.setRole("User");
        String result = UserService.registerUser(user);

        if (result.equals("User saved"))
        {
            authenticate(email, password);

            final UserDetails userDetails = jwtInMemoryUserDetailsService
                    .loadUserByUsername(user.getEmail());

            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));

        }

        //An error occurred
        return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(result);
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
